/*
 *    Copyright 2020 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.server.checker;

import lightsearch.server.entity.*;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.service.ClientsService;
import lightsearch.server.service.ClientsServiceTestImpl;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static test.message.TestMessage.*;

public class CommandCheckerAdminKickListClientTestNG {

    private Checker<AdminCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        ClientsService<String, Client, List<Client>> clientsService = new ClientsServiceTestImpl();

        int i = 0;
        List<String> IMEIList = getIMEIList();
        for(String IMEI: IMEIList)
            clientsService.addClient(IMEI, "John_" + i);

        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerAdminKickListClientImpl(clientsService, checker);

        testBegin(CommandCheckerAdminAddBlacklistImpl.class);
    }

    @Test
    public void check_validIMEI() {
        testMethod("check() [valid IMEI]");

        List<String> IMEIList = getValidIMEIList();
        try {
            AdminCommand cmd = new AdminCommandKickListImpl(IMEIList);
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(expectedExceptions = CheckerException.class)
    public void check_IMEINotInClients() throws CheckerException {
        testMethod("check() [IMEI not in clients]");

        List<String> IMEIList = getInvalidsIMEIList();

        AdminCommand cmd = new AdminCommandKickListImpl(IMEIList);
        cmdChecker.check(cmd);
    }

    @Test(dataProvider = "invalidsIMEI", expectedExceptions = CheckerException.class)
    public void check_invalid(String IMEI, String cause) throws CheckerException {
        testMethod("check() " + cause);

        List<String> IMEIList = new ArrayList<>();
        IMEIList.add(IMEI);
        AdminCommand cmd = new AdminCommandKickListImpl(IMEIList);
        cmdChecker.check(cmd);
    }

    private List<String> getIMEIList() {
        List<String> IMEIList = new ArrayList<>();
        IMEIList.add("111111111111111");
        IMEIList.add("222222222222222");
        IMEIList.add("333333333333333");
        IMEIList.add("444444444444444");
        IMEIList.add("555555555555555");

        return IMEIList;
    }

    private List<String> getValidIMEIList() {
        List<String> IMEIList = new ArrayList<>();
        IMEIList.add("id1");
        IMEIList.add("id2");

        return IMEIList;
    }

    private List<String> getInvalidsIMEIList() {
        List<String> IMEIList = new ArrayList<>();
        IMEIList.add("id8");
        IMEIList.add("id9");

        return IMEIList;
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
