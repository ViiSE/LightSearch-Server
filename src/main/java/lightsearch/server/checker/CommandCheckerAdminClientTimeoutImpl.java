/*
 *  Copyright 2019 ViiSE.
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
 */

package lightsearch.server.checker;

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandClientTimeoutImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.ValidatorException;
import lightsearch.server.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("commandCheckerAdminClientTimeout")
public class CommandCheckerAdminClientTimeoutImpl implements Checker<AdminCommand> {

    private final Validator<Integer> toutValidator;

    public CommandCheckerAdminClientTimeoutImpl(
            @Qualifier("clientTimeoutValidator") Validator<Integer> toutValidator) {
        this.toutValidator = toutValidator;
    }

    @Override
    public void check(AdminCommand admCmd) throws CheckerException {
        try {
            AdminCommandClientTimeoutImpl cmd = (AdminCommandClientTimeoutImpl) admCmd;
            toutValidator.validate(cmd.timeout());
        } catch (ValidatorException ex) {
            throw new CheckerException(ex.getMessage(), ex.getLogMessage());
        }
    }
}
