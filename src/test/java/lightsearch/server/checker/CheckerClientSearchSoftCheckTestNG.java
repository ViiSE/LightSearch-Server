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

import lightsearch.server.constants.ClientCommands;
import lightsearch.server.entity.*;
import lightsearch.server.exception.CheckerException;
import org.testng.annotations.*;

import static test.message.TestMessage.*;

public class CheckerClientSearchSoftCheckTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerClientSearchSoftCheckImpl(checker);

        testBegin(CommandCheckerClientSearchSoftCheckImpl.class);
    }

    @Test
    @Parameters({"barcode", "username"})
    public void check_valid(String barcode, String username) {
        testMethod("check()");

        ClientCommand cmd = createClientCommand(barcode, username);
        try {
            cmdChecker.check(cmd);
        } catch (CheckerException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "invalidsData", expectedExceptions = CheckerException.class)
    public void check_invalid(String barcode, String userIdent, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(barcode, userIdent);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsData")
    public Object[][] createInvalidsData() {
        return new Object[][] {
                {"", "111", "[invalid barcode (empty)]"},
                {null, "111", "[invalid barcode (null)]"},
                {"test", "", "[invalid username (empty)]"},
                {"tesst", null, "[invalid username (null)]"}
        };
    }

    private ClientCommand createClientCommand(String barcode, String username) {
        return new ClientCommandWithBarcodeImpl(
                new ClientCommandWithUsernameImpl(
                        new ClientCommandSimpleImpl(ClientCommands.SEARCH_SOFT_CHECK), username), barcode);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientSearchSoftCheckImpl.class);
    }
}
