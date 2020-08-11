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

public class CommandCheckerClientBindTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerClientBindImpl(checker);

        testBegin(CommandCheckerClientBindImpl.class);
    }

    @Test
    @Parameters({"barcode", "factoryBarcode", "userIdent"})
    public void check_valid(String barcode, String factoryBarcode, String userIdent) {
        testMethod("check()");

        ClientCommand cmd = createClientCommand(barcode, factoryBarcode, userIdent);
        try {
            cmdChecker.check(cmd);
        } catch (CheckerException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "invalidsData", expectedExceptions = CheckerException.class)
    public void check_invalid(String barcode, String factoryBarcode, String userIdent, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(barcode, factoryBarcode, userIdent);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsData")
    public Object[][] createInvalidsData() {
        return new Object[][] {
                {"", "22505322", "101", "[invalid barcode (empty)]"},
                {null, "22505322", "101", "[invalid barcode (null)]"},
                {"22505", "", "101", "[invalid factory barcode (empty)]"},
                {"22505", null, "101", "[invalid factory barcode (null)]"},
                {"22505", "22505322", "", "[invalid user identifier (empty)]"},
                {"22505", "22505322", null, "[invalid user identifier (null)]"}
        };
    }

    private ClientCommand createClientCommand(String barcode, String factoryBarcode, String userIdent) {
        return new ClientCommandWithBarcodeImpl(
                new ClientCommandWithFactoryBarcodeImpl(
                        new ClientCommandWithUserIdentifierImpl(
                                new ClientCommandSimpleImpl(ClientCommands.BIND),
                                userIdent),
                        factoryBarcode),
                barcode);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientBindImpl.class);
    }
}
