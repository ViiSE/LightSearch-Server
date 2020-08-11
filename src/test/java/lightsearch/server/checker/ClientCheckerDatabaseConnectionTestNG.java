/*
 *  Copyright 2020 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package lightsearch.server.checker;

import lightsearch.server.LightSearchServer;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandSimpleImpl;
import lightsearch.server.entity.ClientCommandWithUsernameAndPasswordImpl;
import lightsearch.server.entity.Property;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.producer.entity.PropertyProducerTestImpl;
import lightsearch.server.properties.ApplicationPropertiesDirectoryImpl;
import lightsearch.server.properties.DatasourcePropertiesFileReaderMapOfPropertiesImpl;
import lightsearch.server.properties.PropertiesReader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestDirectories;

import java.util.Map;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:connect-application.properties")
public class ClientCheckerDatabaseConnectionTestNG extends AbstractTestNGSpringContextTests {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        PropertiesReader<Map<String, Property<String>>> propRd = new DatasourcePropertiesFileReaderMapOfPropertiesImpl(
                new PropertyProducerTestImpl(),
                TestDirectories.applicationPropertiesDirectory()
        );
        cmdChecker = new ClientCheckerDatabaseConnectionImpl(propRd);

        testBegin(ClientCheckerDatabaseConnectionImpl.class);
    }

    @Test
    public void check_valid() {
        testMethod("check() [valid]");

        try {
            ClientCommand cmd = new ClientCommandWithUsernameAndPasswordImpl(
                    new ClientCommandSimpleImpl("checkDatabase"),
                    "ls_test",
                    "test"
            );
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(dataProvider = "invalidsDcValues", expectedExceptions = CheckerException.class)
    public void check_invalid(String username, String password, Checker<ClientCommand> cmdChecker, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = new ClientCommandWithUsernameAndPasswordImpl(
                new ClientCommandSimpleImpl("checkDatabase"),
                username,
                password
        );
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsDcValues")
    public Object[][] createInvalidsDcValues() {
        PropertiesReader<Map<String, Property<String>>> propRd = new DatasourcePropertiesFileReaderMapOfPropertiesImpl(
                new PropertyProducerTestImpl(),
                new ApplicationPropertiesDirectoryImpl(new CurrentServerDirectoryTestImpl())
        );

        PropertiesReader<Map<String, Property<String>>> propRdFake = new DatasourcePropertiesFileReaderMapOfPropertiesImpl(
                new PropertyProducerTestImpl(),
                TestDirectories.fakePropertiesDirectory()
        );

        return new Object[][] {
                {"ls_fake", "test", cmdChecker, "[wrong username]"},
                {"ls_test", "fake", cmdChecker, "[wrong password]"},
                {"ls_fake", "fake", cmdChecker, "[wrong username and password]"},
                {"ls_test", "test", new ClientCheckerDatabaseConnectionImpl(propRd), "[Connection is not available.]"},
                {"ls_test", "test", new ClientCheckerDatabaseConnectionImpl(propRdFake), "[JDBC driver is not available.]"}
        };
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(ClientCheckerDatabaseConnectionImpl.class);
    }
}
