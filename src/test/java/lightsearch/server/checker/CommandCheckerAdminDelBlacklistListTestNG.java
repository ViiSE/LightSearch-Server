/*
 *    Copyright 2020 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.server.checker;

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDelBlacklistListImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static test.message.TestMessage.*;

public class CommandCheckerAdminDelBlacklistListTestNG {

    private Checker<AdminCommand> cmdChecker;
    private BlacklistService<String> blacklistService;

    @BeforeClass
    public void setUpClass() {
        HashAlgorithm ha = new HashAlgorithmSHA512Impl();
        blacklistService = new BlacklistServiceImpl(ha);
        List<String> IMEIList = getIMEIList();
        for(String IMEI: IMEIList) {
            blacklistService.add(IMEI);
        }
        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        cmdChecker = new CommandCheckerAdminDelBlacklistListImpl(blacklistService, checker);

        testBegin(CommandCheckerAdminDelBlacklistListImpl.class);
    }

    @Test
    public void check_IMEIInBlacklist() {
        testMethod("check() [IMEI in the blacklist]");

        List<String> IMEIList = getValidIMEIList();

        try {
            AdminCommand cmd = new AdminCommandDelBlacklistListImpl(IMEIList);
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(expectedExceptions = CheckerException.class)
    public void check_IMEINotInBlacklist() throws CheckerException {
        testMethod("check() [IMEI not in the blacklist]");

        AdminCommand cmd = new AdminCommandDelBlacklistListImpl(getInvalidIMEIList());
        cmdChecker.check(cmd);
    }

    @Test(dataProvider = "invalidsIMEI", expectedExceptions = CheckerException.class)
    public void check_invalid(String IMEI, String cause) throws CheckerException {
        testMethod("check() " + cause);

        List<String> IMEIList = new ArrayList<>();
        IMEIList.add(IMEI);

        AdminCommand cmd = new AdminCommandDelBlacklistListImpl(IMEIList);
        cmdChecker.check(cmd);
    }

    private List<String> getIMEIList() {
        List<String> IMEIList = new ArrayList<>();
        IMEIList.add("111111111111111");
        IMEIList.add("222222222222222");
        IMEIList.add("333333333333333");
        IMEIList.add("444444444444444");
        IMEIList.add("555555555555555");

        return IMEIList;
    }

    private List<String> getValidIMEIList() {
        List<String> IMEIList = new ArrayList<>();
        IMEIList.add("111111111111111");
        IMEIList.add("222222222222222");

        return IMEIList;
    }

    private List<String> getInvalidIMEIList() {
        List<String> IMEIList = new ArrayList<>();
        IMEIList.add("666666666666666");
        IMEIList.add("777777777777777");

        return IMEIList;
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
        testEnd(CommandCheckerAdminDelBlacklistListImpl.class);
    }
}
