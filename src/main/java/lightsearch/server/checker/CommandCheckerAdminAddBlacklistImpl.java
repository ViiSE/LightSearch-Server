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
import lightsearch.server.entity.AdminCommandAddBlacklistImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.service.BlacklistService;
import org.springframework.stereotype.Component;

@Component("commandCheckerAdminAddBlacklist")
public class CommandCheckerAdminAddBlacklistImpl implements Checker<AdminCommand> {

    private final BlacklistService<String> blacklistService;
    private final LightSearchChecker checker;

    public CommandCheckerAdminAddBlacklistImpl(BlacklistService<String> blacklistService, LightSearchChecker checker) {
        this.blacklistService = blacklistService;
        this.checker = checker;
    }

    @Override
    public void check(AdminCommand cmd) throws CheckerException {
        AdminCommandAddBlacklistImpl command = (AdminCommandAddBlacklistImpl) cmd;

        if(checker.isNull(command.IMEI()))
            throw new CheckerException("Wrong command format. IMEI is null!", "AddBlacklist: unknown client: IMEI is null!");

        if(checker.isEmpty(command.IMEI()))
            throw new CheckerException("Wrong command format. IMEI is empty!", "AddBlacklist: unknown client: IMEI is empty!");

        if(blacklistService.contains(command.IMEI()))
            throw new CheckerException("This client is already in the blacklist!", "Client " + command.IMEI() + " already in the blacklist.");
    }
}
