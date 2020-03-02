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
import lightsearch.server.entity.AdminCommandDatabaseImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.ValidatorException;
import lightsearch.server.validator.Validator;
import org.springframework.stereotype.Component;

@Component("commandCheckerAdminChangeDatabase")
public class CommandCheckerAdminChangeDatabaseImpl implements Checker<AdminCommand> {

    private final LightSearchChecker checker;
    private final Validator<String> ipValidator;
    private final Validator<Integer> portValidator;

    public CommandCheckerAdminChangeDatabaseImpl(
            LightSearchChecker checker,
            Validator<String> ipValidator,
            Validator<Integer> portValidator) {
        this.checker = checker;
        this.ipValidator = ipValidator;
        this.portValidator = portValidator;
    }

    @Override
    public void check(AdminCommand admCmd) throws CheckerException {
        if(admCmd instanceof AdminCommandDatabaseImpl) {
            AdminCommandDatabaseImpl admCmdDb = (AdminCommandDatabaseImpl) admCmd;

            if (checker.isNull(admCmdDb.dbName()))
                throw new CheckerException("Wrong command format. Database name is null!", "ChangeDatabase: dbName is null!");
            if (checker.isEmpty(admCmdDb.dbName()))
                throw new CheckerException("Wrong command format. Database name is empty!", "ChangeDatabase: dbName is empty!");
            if (checker.isNull(admCmdDb.password()))
                throw new CheckerException("Wrong command format. Database password is null!", "ChangeDatabase: password is null!");
            if (checker.isNull(admCmdDb.ip()))
                throw new CheckerException("Wrong command format. Database IP is null!", "ChangeDatabase: ip is null!");
            if (checker.isEmpty(admCmdDb.ip()))
                throw new CheckerException("Wrong command format. Database IP is empty!", "ChangeDatabase: ip is empty!");
            if (checker.isNull(admCmdDb.dbUsername()))
                throw new CheckerException("Wrong command format. Username is null!", "ChangeDatabase: username is null!");

            try {
                ipValidator.validate(admCmdDb.ip());
                portValidator.validate(admCmdDb.port());
            } catch (ValidatorException ex) {
                throw new CheckerException(ex.getMessage(), ex.getLogMessage());
            }
        } else throw new CheckerException(
                "Expected AdminCommand instanceof AdminCommandDatabaseImpl",
                "Expected AdminCommand instanceof AdminCommandDatabaseImpl");
    }
}
