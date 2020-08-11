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
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceImpl;
import org.testng.annotations.*;

import static test.message.TestMessage.*;

public class CommandCheckerClientAuthorizationDefaultTestNG {

    private Checker<ClientCommand> cmdChecker;

    @BeforeClass
    @Parameters({"blacklistIMEI", "validIMEI"})
    public void setUpClass(String blIMEI, String validIMEI) {
        HashAlgorithm ha = new HashAlgorithmSHA512Impl();
        BlacklistService<String> blacklistService = new BlacklistServiceImpl(ha);
        blacklistService.blacklist().add(blIMEI);
        Checker<String> cmdCheckerDef = new CommandCheckerClientDefaultImpl(blacklistService);
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();

        cmdChecker = new CommandCheckerClientAuthorizationImpl(cmdCheckerDef, checker);

        testBegin(CommandCheckerClientAuthorizationImpl.class);
    }

    @Test
    @Parameters({"validIMEI"})
    public void check_validIMEI(String IMEI) {
        testMethod("check() [valid IMEI]");

        try {
            ClientCommand cmd = createClientCommand(IMEI);
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(dataProvider = "invalidsAuthData", expectedExceptions = CheckerException.class)
    public void check_invalid(String IMEI, String userIdent, String model, String os, String ip, String uname, String pass, String cause) throws CheckerException {
        testMethod("check() " + cause);

        ClientCommand cmd = createClientCommand(IMEI, userIdent, model, os, ip, uname, pass);
        cmdChecker.check(cmd);
    }

    @DataProvider(name = "invalidsAuthData")
    public Object[][] createInvalidsIMEI() {
        return new Object[][] {
                {"", "101", "Laptop", "Linux", "127.0.0.1", "user", "pass", "[invalid IMEI (empty)]"},
                {null, "101", "Laptop", "Linux", "127.0.0.1", "user", "pass", "[invalid IMEI (null)]"},
                {"IMEI", null, "Laptop", "Linux", "127.0.0.1", "user", "pass", "[invalid userIdent (null)]"},
                {"IMEI", "101", null, "Linux", "127.0.0.1", "user", "pass", "[invalid model (null)]"},
                {"IMEI", "101", "Laptop", null, "127.0.0.1", "user", "pass", "[invalid os (null)]"},
                {"IMEI", "101", "Laptop", "Linux", null, "user", "pass", "[invalid ip (null)]"},
                {"IMEI", "101", "Laptop", "Linux", "127.0.0.1", "", "pass", "[invalid username (empty)]"},
                {"IMEI", "101", "Laptop", "Linux", "127.0.0.1", null, "pass", "[invalid username (null)]"},
                {"IMEI", "101", "Laptop", "Linux", "127.0.0.1", "user", "", "[invalid password (empty)]"},
                {"IMEI", "101", "Laptop", "Linux", "127.0.0.1", "user", null, "[invalid password (null)]"}
        };
    }

    private ClientCommand createClientCommand(String IMEI, String userIdent, String model, String os, String ip, String uname, String pass) {
        return new ClientCommandWithUsernameAndPasswordImpl(
                new ClientCommandWithIMEIImpl(
                        new ClientCommandWithIpImpl(
                                new ClientCommandWithOsImpl(
                                        new ClientCommandWithModelImpl(
                                                new ClientCommandWithUserIdentifierImpl(
                                                        new ClientCommandSimpleImpl(ClientCommands.LOGIN),
                                                        userIdent), model), os), ip), IMEI), uname, pass);
    }

    private ClientCommand createClientCommand(String IMEI) {
        return createClientCommand(IMEI, "111", "Model", "Linux", "127.0.0.1", "John", "sTRongPa55!");
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientAuthorizationImpl.class);
    }
}
