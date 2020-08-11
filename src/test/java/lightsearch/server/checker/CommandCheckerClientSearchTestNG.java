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

public class CommandCheckerClientSearchTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerClientSearchImpl(checker);

        testBegin(CommandCheckerClientSearchImpl.class);
    }

    @Test
    @Parameters({"barcode", "sklad", "TK"})
    public void check_valid(String barcode, String sklad, String TK) {
        testMethod("check()");

        ClientCommand cmd = createClientCommand(sklad, TK, barcode);
        try {
            cmdChecker.check(cmd);
        } catch (CheckerException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "invalidsData", expectedExceptions = CheckerException.class)
    public void check_invalid(String TK, String sklad, String barcode, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(TK, sklad, barcode);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsData")
    public Object[][] createInvalidsData() {
        return new Object[][] {
                {"", "sklad1", "barcode", "[invalid TK (empty)]"},
                {null, "sklad1", "barcode", "[invalid TK (null)]"},
                {"TK1", "", "barcode", "[invalid sklad (empty)]"},
                {"TK1", null, "barcode", "[invalid TK (null)]"},
                {"", "sklad1", "", "[invalid barcode (null)]"},
                {"", "sklad1", null, "[invalid barcode (null)]"}
        };
    }

    private ClientCommand createClientCommand(String TK, String sklad, String barcode) {
        return new ClientCommandWithBarcodeImpl(
                new ClientCommandWithSkladImpl(
                        new ClientCommandWithTKImpl(
                                new ClientCommandSimpleImpl(ClientCommands.SEARCH), TK), sklad), barcode);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientSearchImpl.class);
    }
}
