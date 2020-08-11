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

import lightsearch.server.entity.Client;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceImpl;
import lightsearch.server.service.ClientsService;
import lightsearch.server.service.ClientsServiceTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static test.message.TestMessage.*;

public class CommandCheckerClientContainsTestNG {

    private Checker<String> cmdChecker;

    @BeforeClass
    @Parameters({"blacklistIMEI", "validIMEI"})
    public void setUpClass(String blIMEI, String vIMEI) {
        HashAlgorithm ha = new HashAlgorithmSHA512Impl();
        BlacklistService<String> blacklistService = new BlacklistServiceImpl(ha);
        blacklistService.blacklist().add(blIMEI);
        ClientsService<String, Client, List<Client>> clientsService = new ClientsServiceTestImpl();
        clientsService.addClient(vIMEI, "user1");
        cmdChecker = new CommandCheckerClientContainsImpl(
                new CommandCheckerClientDefaultImpl(blacklistService),
                clientsService);

        testBegin(CommandCheckerClientDefaultImpl.class);
    }

    @Test
    public void check_validIMEI() {
        testMethod("check() [valid IMEI]");

        try {
            cmdChecker.check("id1");
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

    @Test(expectedExceptions = CheckerException.class)
    @Parameters({"invalidIMEI"})
    public void check_IMEINotFound(String IMEI) throws CheckerException {
        testMethod("check() [IMEI not found]");

        cmdChecker.check(IMEI);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerClientDefaultImpl.class);
    }
}
