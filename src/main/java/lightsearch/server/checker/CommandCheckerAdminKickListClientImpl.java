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
import lightsearch.server.entity.AdminCommandKickImpl;
import lightsearch.server.entity.AdminCommandKickListImpl;
import lightsearch.server.entity.Client;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.service.ClientsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("commandCheckerAdminKickListClient")
public class CommandCheckerAdminKickListClientImpl implements Checker<AdminCommand> {

    private final ClientsService<String, Client, List<Client>> clientsService;
    private final LightSearchChecker checker;

    public CommandCheckerAdminKickListClientImpl(
            @Qualifier("clientsServiceDatabase") ClientsService<String, Client, List<Client>> clientsService,
            LightSearchChecker checker) {
        this.clientsService = clientsService;
        this.checker = checker;
    }

    @Override
    public void check(AdminCommand admCmd) throws CheckerException {
        AdminCommandKickListImpl command = (AdminCommandKickListImpl) admCmd;

        if (checker.isNull(command.IMEI()))
            throw new CheckerException("Wrong command format. IMEI is null!", "AddBlacklist: unknown client: IMEI is null.");

        for(String IMEI: command.IMEI()) {
            if (checker.isEmpty(IMEI))
                throw new CheckerException("Wrong command format. One or more IMEI is empty!", "AddBlacklist: unknown client: one or more IMEI is empty.");

            if (!clientsService.contains(IMEI))
                throw new CheckerException("One or more clients with current IMEI not found. (Not connected to LightSearch Server)", "One or more clients does not exist.");
        }
    }
}
