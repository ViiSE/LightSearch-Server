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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static test.message.TestMessage.*;

public class CheckerClientConfirmSoftCheckProductsTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerClientConfirmSoftCheckProductsImpl(checker);

        testBegin(CommandCheckerClientConfirmSoftCheckProductsImpl.class);
    }

    @Test(dataProvider = "validsData")
    public void check_valid(String userIdent, String cardCode, List<Product> products) {
        testMethod("check()");

        ClientCommand cmd = createClientCommand(userIdent, cardCode, products);
        try {
            cmdChecker.check(cmd);
        } catch (CheckerException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "invalidsData", expectedExceptions = CheckerException.class)
    public void check_invalid(String userIdent, String cardCode, List<Product> products, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(userIdent, cardCode, products);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsData")
    public Object[][] createInvalidsData() {
        return new Object[][] {
                {"", "222", new ArrayList<>() {{ add(new ProductSimpleImpl("id1")); }}, "[invalid userIdentifier (empty)]"},
                {null, "222", new ArrayList<>() {{ add(new ProductSimpleImpl("id1")); }}, "[invalid userIdentifier (null)]"},
                {"111", "", new ArrayList<>() {{ add(new ProductSimpleImpl("id1")); }}, "[invalid cardCode (empty)]"},
                {"111", null, new ArrayList<>() {{ add(new ProductSimpleImpl("id1")); }}, "[invalid cardCode (null)]"},
                {"111", "222", new ArrayList<>(), "[invalid products (empty)]"},
                {"111", "222", new ArrayList<>() {{ add(new ProductWithAmountImpl(new ProductSimpleImpl("id1"),-10.0f)); }}, "[invalid products (amount less than zero)]"},
                {"111", "222", new ArrayList<>() {{ add(new ProductWithAmountImpl(new ProductSimpleImpl(""),1.0f)); }}, "[invalid products (empty id)]"},
                {"111", "222", new ArrayList<>() {{ add(new ProductWithAmountImpl(new ProductSimpleImpl(null),1.0f)); }}, "[invalid products (null id)]"},
        };
    }

    @DataProvider(name = "validsData")
    public Object[][] createValidsData() {
        return new Object[][] {
                {"101", "222", new ArrayList<>() {{ add(new ProductWithAmountImpl(new ProductSimpleImpl("id1"), 40.5f)); }}},
        };
    }

    private ClientCommand createClientCommand(String userIdentifier, String cardCode, List<Product> products) {
        return new ClientCommandWithUserIdentifierImpl(
                new ClientCommandWithCardCodeImpl(
                        new ClientCommandWithProductsImpl(
                                new ClientCommandSimpleImpl(ClientCommands.CONFIRM_SOFT_CHECK_PRODUCTS),
                                products),
                        cardCode),
                userIdentifier);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientConfirmSoftCheckProductsImpl.class);
    }
}
