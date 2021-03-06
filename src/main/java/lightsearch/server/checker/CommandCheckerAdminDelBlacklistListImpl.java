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
import lightsearch.server.entity.AdminCommandDelBlacklistImpl;
import lightsearch.server.entity.AdminCommandDelBlacklistListImpl;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.service.BlacklistService;
import org.springframework.stereotype.Component;

@Component("commandCheckerAdminDelBlacklistList")
public class CommandCheckerAdminDelBlacklistListImpl implements Checker<AdminCommand> {

    private final BlacklistService<String> blacklistService;
    private final LightSearchChecker checker;

    public CommandCheckerAdminDelBlacklistListImpl(BlacklistService<String> blacklistService, LightSearchChecker checker) {
        this.blacklistService = blacklistService;
        this.checker = checker;
    }

    @Override
    public void check(AdminCommand cmd) throws CheckerException {
        AdminCommandDelBlacklistListImpl command = (AdminCommandDelBlacklistListImpl) cmd;

        if(checker.isNull(command.IMEI()))
            throw new CheckerException("Wrong command format. List of IMEI is null!", "AddBlacklist: unknown clients: List of IMEI is null.");

        for(String IMEIStr: command.IMEI()) {
            if(checker.isNull(IMEIStr))
                throw new CheckerException("Wrong command format. One or more IMEI in list of IMEI is null!", "AddBlacklist: unknown client: one or more IMEI in list of IMEI is null.");
            if (checker.isEmpty(IMEIStr))
                throw new CheckerException("Wrong command format. One or more IMEI in list of IMEI is empty!", "AddBlacklist: unknown client: one or more IMEI in list of IMEI is empty.");
            if(!blacklistService.contains(IMEIStr))
                throw new CheckerException("One or more IMEI in list of IMEI is not in the blacklist!", "Client " + IMEIStr + " already in the blacklist.");
        }
    }
}
