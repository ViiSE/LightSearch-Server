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
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandSimpleImpl;
import lightsearch.server.entity.ClientCommandWithFactoryBarcodeImpl;
import lightsearch.server.entity.ClientCommandWithUserIdentifierImpl;
import lightsearch.server.exception.CheckerException;
import org.testng.annotations.*;

import static test.message.TestMessage.*;

public class CommandCheckerClientUnbindTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerClientUnbindImpl(checker);

        testBegin(CommandCheckerClientUnbindImpl.class);
    }

    @Test
    @Parameters({"factoryBarcode", "userIdent"})
    public void check_valid(String factoryBarcode, String userIdent) {
        testMethod("check()");

        ClientCommand cmd = createClientCommand(factoryBarcode, userIdent);
        try {
            cmdChecker.check(cmd);
        } catch (CheckerException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "invalidsData", expectedExceptions = CheckerException.class)
    public void check_invalid(String factoryBarcode, String userIdent, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(factoryBarcode, userIdent);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsData")
    public Object[][] createInvalidsData() {
        return new Object[][] {
                {"", "101", "[invalid factory barcode (empty)]"},
                {null, "101", "[invalid factory barcode (null)]"},
                {"22505322", "", "[invalid user identifier (empty)]"},
                {"22505322", null, "[invalid user identifier (null)]"}
        };
    }

    private ClientCommand createClientCommand(String factoryBarcode, String userIdent) {
        return new ClientCommandWithFactoryBarcodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandSimpleImpl(ClientCommands.UNBIND),
                        userIdent),
                factoryBarcode);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientUnbindImpl.class);
    }
}
