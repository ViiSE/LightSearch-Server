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

public class CommandCheckerClientUpdateSoftCheckProductsTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerClientUpdateSoftCheckProductsImpl(checker);

        testBegin(CommandCheckerClientUpdateSoftCheckProductsImpl.class);
    }

    @Test(dataProvider = "validsData")
    public void check_valid(String username, List<Product> products) {
        testMethod("check()");

        ClientCommand cmd = createClientCommand(username, products);
        try {
            cmdChecker.check(cmd);
        } catch (CheckerException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "invalidsData", expectedExceptions = CheckerException.class)
    public void check_invalid(String username, List<Product> products, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(username, products);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsData")
    public Object[][] createInvalidsData() {
        return new Object[][] {
                {"", new ArrayList<>() {{ add(new ProductSimpleImpl("id1")); }}, "[invalid username (empty)]"},
                {null, new ArrayList<>() {{ add(new ProductSimpleImpl("id1")); }}, "[invalid username (null)]"},
                {"user", new ArrayList<>(), "[invalid products (empty)]"},
                {"user", new ArrayList<>() {{ add(new ProductSimpleImpl("")); }}, "[invalid products (empty id)]"},
                {"user", new ArrayList<>() {{ add(new ProductSimpleImpl(null)); }}, "[invalid products (null id)]"},
        };
    }

    @DataProvider(name = "validsData")
    public Object[][] createValidsData() {
        return new Object[][] {
                {"user", new ArrayList<>() {{ add(new ProductSimpleImpl("id1")); }}},
        };
    }

    private ClientCommand createClientCommand(String username, List<Product> products) {
        return new ClientCommandWithUsernameImpl(
                new ClientCommandWithProductsImpl(
                        new ClientCommandSimpleImpl(ClientCommands.CONFIRM_SOFT_CHECK_PRODUCTS),
                        products),
                username);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientUpdateSoftCheckProductsImpl.class);
    }
}
