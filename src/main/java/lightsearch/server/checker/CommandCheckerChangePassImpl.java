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
import lightsearch.server.entity.AdminCommandChangePassImpl;
import lightsearch.server.exception.CheckerException;
import org.springframework.stereotype.Component;

@Component("commandCheckerChangePass")
public class CommandCheckerChangePassImpl implements Checker<AdminCommand> {

    private final LightSearchChecker checker;

    public CommandCheckerChangePassImpl(LightSearchChecker checker) {
        this.checker = checker;
    }

    @Override
    public void check(AdminCommand cmd) throws CheckerException {
        AdminCommandChangePassImpl command = (AdminCommandChangePassImpl) cmd;

        String password = command.password();

        if(checker.isNull(password))
            throw new CheckerException("Wrong command format. Password is null!", "ChangePass: unknown client: Password is null.");

        if(checker.isEmpty(password))
            throw new CheckerException("Wrong command format. Password is empty!", "ChangePass: unknown client: Password is empty.");
    }
}
