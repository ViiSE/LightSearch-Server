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

package lightsearch.server.properties;

import lightsearch.server.data.AdminChangeDatabaseCommandDTO;
import lightsearch.server.data.DatasourcePoolDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDatabaseImpl;
import lightsearch.server.entity.ClientTimeoutPropertyImpl;
import lightsearch.server.entity.Property;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.producer.entity.PropertyProducer;
import lightsearch.server.producer.entity.PropertyProducerTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class PropertiesChangerLocalTestNG {

    private PropertiesChanger<List<String>, Property<String>> propChanger;

    private AdminCommand admCmd;
    private PropertyProducer propProducer;

    @BeforeClass
    @Parameters({"dbName", "dbUsername", "dbPass", "dbIp", "dbPort"})
    public void setUpClass(String dbName, String dbUsername, String dbPass, String ip, int port) {
        AdminChangeDatabaseCommandDTO adminCommandDTO = new AdminChangeDatabaseCommandDTO();
        adminCommandDTO.setDbName(dbName);
        adminCommandDTO.setUsername(dbUsername);
        adminCommandDTO.setPassword(dbPass);
        adminCommandDTO.setHost(ip);
        adminCommandDTO.setPort(port);
        adminCommandDTO.setAdditional("encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251&amp;defaultResultSetHoldable=true");
        adminCommandDTO.setDbType("firebirdsql");
        adminCommandDTO.setPool(new DatasourcePoolDTO() {{
            setMaxLifeTime(100000L);
            setAutoCommit(true);
            setConnectionTimeout(30000L);
            setIdleTimeout(3000L);
            setMaximumPoolSize(10L);
        }});

        admCmd = new AdminCommandDatabaseImpl(adminCommandDTO);
        propProducer = new PropertyProducerTestImpl();
        CurrentServerDirectory curDir = new CurrentServerDirectoryTestImpl();
        PropertiesDirectory propDir = new ApplicationPropertiesDirectoryImpl(curDir);
        PropertiesReader<List<String>> propReader = new PropertiesFileReaderListOfStringImpl(propDir);

        propChanger = new PropertiesChangerLocalImpl(propReader);

        testBegin(PropertiesChangerLocalImpl.class);
    }

    @Test
    @Parameters({"tout"})
    public void change_oneProp(int tout) {
        testMethod("change() [one property]");

        Property<String> prop = new ClientTimeoutPropertyImpl(tout);

        try {
            List<String> props = propChanger.change(prop);
            props.forEach(System.out::println);
            boolean isCheck = false;
            for(String pr : props) {
                if (pr.contains("lightsearch.server.jwt-valid-day-count=" + tout)) {
                    isCheck = true;
                    break;
                }
            }

            assertTrue(isCheck, "Property not changed!");
        } catch (PropertiesException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test
    public void change_listProp() {
        testMethod("change() [list of properties]");

        Property<String> prop = new SpringDatasourcePropertyCreatorImpl(propProducer).create(admCmd);

        try {
            List<String> props = propChanger.change(prop);
            props.forEach(System.out::println);
        } catch (PropertiesException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void teardownClass() {
        testEnd(PropertiesChangerLocalImpl.class);
    }
}
