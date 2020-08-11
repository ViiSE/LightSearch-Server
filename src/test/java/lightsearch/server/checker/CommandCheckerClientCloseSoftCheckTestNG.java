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

public class CommandCheckerClientCloseSoftCheckTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerClientCloseSoftCheckImpl(checker);

        testBegin(CommandCheckerClientCloseSoftCheckImpl.class);
    }

    @Test
    @Parameters({"userIdent", "cardCode", "delivery"})
    public void check_valid(String userIdent, String cardCode, String delivery) {
        testMethod("check()");

        ClientCommand cmd = createClientCommand(cardCode, userIdent, delivery);
        try {
            cmdChecker.check(cmd);
        } catch (CheckerException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "invalidsData", expectedExceptions = CheckerException.class)
    public void check_invalid(String cardCode, String userIdent, String delivery, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(cardCode, userIdent, delivery);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsData")
    public Object[][] createInvalidsData() {
        return new Object[][] {
                {"", "222", "0", "[invalid userIdentifier (empty)]"},
                {null, "222", "0", "[invalid userIdentifier (null)]"},
                {"111", "", "0", "[invalid cardCode (empty)]"},
                {"111", null, "0", "[invalid cardCode (null)]"},
                {"111", "222", "", "[invalid delivery (empty)]"},
                {"111", "222", null, "[invalid delivery (null)]"}
        };
    }

    private ClientCommand createClientCommand(String cardCode, String userIdentifier, String delivery) {
        return new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandWithCardCodeImpl(
                                new ClientCommandWithDeliveryImpl(
                                        new ClientCommandSimpleImpl(ClientCommands.CLOSE_SOFT_CHECK),
                                        delivery),
                                cardCode),
                        userIdentifier);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientCloseSoftCheckImpl.class);
    }
}
