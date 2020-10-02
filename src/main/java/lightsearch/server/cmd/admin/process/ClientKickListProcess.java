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
package lightsearch.server.cmd.admin.process;

import lightsearch.server.checker.Checker;
import lightsearch.server.data.ClientDTO;
import lightsearch.server.entity.*;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.service.ClientsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author ViiSE
 */
@Component("clientKickListProcess")
public class ClientKickListProcess implements AdminProcess<AdminCommandResult> {

    private final ClientsService<String, Client, List<Client>> clientsService;
    private final Checker<AdminCommand> checker;
    private final AdminCommandResultProducer adminCommandResultProducer;

    public ClientKickListProcess(
            @Qualifier("clientsServiceDatabase") ClientsService<String, Client, List<Client>> clientsService,
            @Qualifier("commandCheckerAdminKickListClient") Checker<AdminCommand> checker,
            AdminCommandResultProducer adminCommandResultProducer) {
        this.clientsService = clientsService;
        this.checker = checker;
        this.adminCommandResultProducer = adminCommandResultProducer;
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            checker.check(command);

            AdminCommandKickListImpl cmdKick = (AdminCommandKickListImpl) command;

            for(String IMEI: cmdKick.IMEI()) {
                clientsService.remove(IMEI);
            }

            return adminCommandResultProducer
                    .getAdminCommandResultSimpleInstance(
                            true,
                            "Clients has been kicked from the current session.");
        } catch (CheckerException ex) {
            return adminCommandResultProducer
                    .getAdminCommandResultSimpleInstance(
                            false,
                            ex.getMessage());
        }
    }
}
