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
import lightsearch.server.entity.AdminCommandDelBlacklistImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceImpl;
import org.testng.annotations.*;

import static test.message.TestMessage.*;

public class CheckerAdminDelBlacklistTestNG {

    private Checker<AdminCommand> cmdChecker;
    private BlacklistService<String> blacklistService;

    @BeforeClass
    @Parameters({"blacklistIMEI"})
    public void setUpClass(String blIMEI) {
        HashAlgorithm ha = new HashAlgorithmSHA512Impl();
        blacklistService = new BlacklistServiceImpl(ha);
        blacklistService.add(blIMEI);
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerAdminDelBlacklistImpl(blacklistService, checker);

        testBegin(CommandCheckerAdminDelBlacklistImpl.class);
    }

    @Test
    @Parameters({"blacklistIMEI"})
    public void check_IMEIInBlacklist(String IMEI) {
        testMethod("check() [IMEI in the blacklist]");

        try {
            AdminCommand cmd = new AdminCommandDelBlacklistImpl(IMEI);
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(expectedExceptions = CheckerException.class)
    @Parameters({"validIMEI"})
    public void check_IMEINotInBlacklist(String IMEI) throws CheckerException {
        testMethod("check() [IMEI not in the blacklist]");

        AdminCommand cmd = new AdminCommandDelBlacklistImpl(IMEI);
        cmdChecker.check(cmd);
    }

    @Test(dataProvider = "invalidsIMEI", expectedExceptions = CheckerException.class)
    public void check_invalid(String IMEI, String cause) throws CheckerException {
        testMethod("check() " + cause);

        AdminCommand cmd = new AdminCommandDelBlacklistImpl(IMEI);
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
        blacklistService.blacklist().clear();
        testEnd(CommandCheckerAdminDelBlacklistImpl.class);
    }
}
