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
import lightsearch.server.entity.AdminCommandClientTimeoutImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.validator.ClientTimeoutValidator;
import lightsearch.server.validator.Validator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

public class CheckerAdminClientTimeoutTestNG {

    private Checker<AdminCommand> cmdChecker;

    @BeforeClass
    public void setUpClass() {
        Validator<Integer> toutValidator = new ClientTimeoutValidator();
        cmdChecker = new CommandCheckerAdminClientTimeoutImpl(toutValidator);

        testBegin(CommandCheckerAdminClientTimeoutImpl.class);
    }

    @Test
    @Parameters({"validTimeout"})
    public void check_validTimeout(int timeout) {
        testMethod("check() [valid timeout]");

        try {
            AdminCommand cmd = new AdminCommandClientTimeoutImpl(timeout);
            cmdChecker.check(cmd);
        } catch (CheckerException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }

    @Test(expectedExceptions = CheckerException.class)
    @Parameters({"invalidTimeout"})
    public void check_invalidTimeout(int timeout) throws CheckerException {
        testMethod("check() [invalid timeout]");

        AdminCommand cmd = new AdminCommandClientTimeoutImpl(timeout);
        cmdChecker.check(cmd);
    }

    @AfterClass
    public void shutDownClass() {
        testEnd(CommandCheckerAdminClientTimeoutImpl.class);
    }
}
