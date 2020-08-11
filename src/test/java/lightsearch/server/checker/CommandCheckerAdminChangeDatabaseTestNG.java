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

import lightsearch.server.data.AdminCommandDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDatabaseImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.validator.IpValidator;
import lightsearch.server.validator.PortValidator;
import lightsearch.server.validator.Validator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

public class CommandCheckerAdminChangeDatabaseTestNG {

    private Checker<AdminCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        Validator<String> ipValidator = new IpValidator();
        Validator<Integer> portValidator = new PortValidator();
        cmdChecker = new CommandCheckerAdminChangeDatabaseImpl(checker, ipValidator, portValidator);

        testBegin(CommandCheckerAdminChangeDatabaseImpl.class);
    }

    @Test
    public void check_valid() {
        testMethod("check() [valid]");

        try {
            AdminCommand cmd = new AdminCommandDatabaseImpl(createAdminCommandDTO());
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(dataProvider = "invalidsDbValues", expectedExceptions = CheckerException.class)
    public void check_invalid(String ip, int port, String dbName, String dbUsername, String password, String cause) throws CheckerException {
        testMethod("check() " + cause);

        AdminCommand cmd = new AdminCommandDatabaseImpl(createAdminCommandDTO(ip, port, dbName, dbUsername, password));
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsDbValues")
    public Object[][] createInvalidsDbValues() {
        return new Object[][] {
                {"192.168..0.1", 8080, "dbName", "dbUsername", "pass", "[invalid ip(not matches)]"},
                {"", 8080, "dbName", "dbUsername", "pass", "[invalid ip(empty)]"},
                {null, 8080, "dbName", "dbUsername", "pass", "[invalid ip(null)]"},
                {"192.168..0.1", 8080, "dbName", "dbUsername", "pass", "[invalid ip(not matches)]"},
                {"192.168.0.1", 8080, "", "dbUsername", "pass", "[invalid dbName(empty)]"},
                {"192.168.0.1", 8080, null, "dbUsername", "pass", "[invalid dbName(null)]"},
                {"192.168.0.1", 8080, "dbName", null, "pass", "[invalid dbUsername(null)]"},
                {"192.168.0.1", 8080, "dbName", "dbUsername", null, "[invalid password(null)]"},
                {"192.168.0.1", -123, "dbName", "dbUsername", "pass", "[invalid port(less)]"},
                {"192.168.0.1", 67000, "dbName", "dbUsername", "pass", "[invalid port(more)]"},

        };
    }

    private AdminCommandDTO createAdminCommandDTO() {
        return createAdminCommandDTO("127.0.0.1", 8080, "firebird", "fire", "bird");
    }

    private AdminCommandDTO createAdminCommandDTO(String ip, int port, String dbName, String dbUsername, String password) {
        AdminCommandDTO cmdDTO = new AdminCommandDTO();
        cmdDTO.setIp(ip);
        cmdDTO.setPort(port);
        cmdDTO.setDbName(dbName);
        cmdDTO.setUsername(dbUsername);
        cmdDTO.setPassword(password);

        return cmdDTO;
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerAdminChangeDatabaseImpl.class);
    }
}
