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
package lightsearch.server.cmd.admin.process;

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.entity.Client;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.service.ClientsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 *
 * @author ViiSE
 */
@Component("clientListRequestProcess")
public class ClientListRequestProcess implements AdminProcess<AdminCommandResult> {

    private final ClientsService<String, Client> clientsService;
    private final AdminCommandResultProducer adminCommandResultProducer;

    public ClientListRequestProcess(
            @Qualifier("clientsServiceDatabase") ClientsService<String, Client> clientsService,
            AdminCommandResultProducer adminCommandResultProducer) {
        this.clientsService = clientsService;
        this.adminCommandResultProducer = adminCommandResultProducer;
    }

    @Override
    public AdminCommandResult apply(AdminCommand ignore) {
        return adminCommandResultProducer.getAdminCommandResultWithClientInstance(
                adminCommandResultProducer.getAdminCommandResultSimpleInstance(
                        true,
                        "OK"),
                new ArrayList<>(clientsService.clients().values()));
    }
}
