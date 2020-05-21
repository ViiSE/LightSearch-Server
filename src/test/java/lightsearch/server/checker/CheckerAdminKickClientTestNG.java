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

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandKickImpl;
import lightsearch.server.entity.Client;
import lightsearch.server.entity.ClientSimpleImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.service.ClientsService;
import lightsearch.server.service.ClientsServiceTestImpl;
import org.testng.annotations.*;

import java.util.List;

import static test.message.TestMessage.*;

public class CheckerAdminKickClientTestNG {

    private Checker<AdminCommand> cmdChecker;

    @BeforeClass
    @Parameters({"foundIMEI"})
    public void setUpClass(String vlIMEI) {
        ClientsService<String, Client, List<Client>> clientsService = new ClientsServiceTestImpl();
        clientsService.addClient(vlIMEI, new ClientSimpleImpl(vlIMEI, "John"));
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerAdminKickClientImpl(clientsService, checker);

        testBegin(CommandCheckerAdminAddBlacklistImpl.class);
    }

    @Test
    @Parameters({"foundIMEI"})
    public void check_validIMEI(String IMEI) {
        testMethod("check() [valid IMEI]");

        try {
            AdminCommand cmd = new AdminCommandKickImpl(IMEI);
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(expectedExceptions = CheckerException.class)
    @Parameters({"invalidIMEI"})
    public void check_IMEINotInClients(String IMEI) throws CheckerException {
        testMethod("check() [IMEI not in clients]");

        AdminCommand cmd = new AdminCommandKickImpl(IMEI);
        cmdChecker.check(cmd);
    }

    @Test(dataProvider = "invalidsIMEI", expectedExceptions = CheckerException.class)
    public void check_invalid(String IMEI, String cause) throws CheckerException {
        testMethod("check() " + cause);

        AdminCommand cmd = new AdminCommandKickImpl(IMEI);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsIMEI")
    public Object[][] createInvalidsIMEI() {
        return new Object[][] {
                {"", "[invalid IMEI (empty)]"},
                {null, "[invalid IMEI (null)]"}
        };
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerAdminAddBlacklistImpl.class);
    }
}
