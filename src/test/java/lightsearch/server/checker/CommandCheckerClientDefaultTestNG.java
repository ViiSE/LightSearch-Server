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

import lightsearch.server.exception.CheckerException;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

public class CommandCheckerClientDefaultTestNG {

    private Checker<String> cmdChecker;

    @BeforeClass
    @Parameters({"blacklistIMEI"})
    public void setUpClass(String blIMEI) {
        HashAlgorithm ha = new HashAlgorithmSHA512Impl();
        BlacklistService<String> blacklistService = new BlacklistServiceImpl(ha);
        blacklistService.add(blIMEI);
        cmdChecker = new CommandCheckerClientDefaultImpl(blacklistService);

        testBegin(CommandCheckerClientDefaultImpl.class);
    }

    @Test()
    @Parameters({"validIMEI"})
    public void check_validIMEI(String IMEI) {
        testMethod("check() [valid IMEI]");

        try {
            cmdChecker.check(IMEI);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(expectedExceptions = CheckerException.class)
    @Parameters({"blacklistIMEI"})
    public void check_IMEIinBlacklist(String IMEI) throws CheckerException {
        testMethod("check() [IMEI in blacklist]");

        cmdChecker.check(IMEI);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientDefaultImpl.class);
    }
}
