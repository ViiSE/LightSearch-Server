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

package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandResultDTO;
import lightsearch.server.data.ClientDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("adminCommandResultWithClients")
@Scope("prototype")
public class AdminCommandResultWithClientsImpl implements AdminCommandResult {

    private final AdminCommandResult admCmdRes;
    private final List<Client> clients;

    public AdminCommandResultWithClientsImpl(AdminCommandResult admCmdRes, List<Client> clients) {
        this.admCmdRes = admCmdRes;
        this.clients = clients;
    }

    @Override
    public Object formForSend() {
        AdminCommandResultDTO admCmdResDTO = (AdminCommandResultDTO) admCmdRes.formForSend();
        List<ClientDTO> clientsDTOs = new ArrayList<>();
        clients.forEach(client -> clientsDTOs.add((ClientDTO) client.formForSend()));
        admCmdResDTO.setClients(clientsDTOs);

        return admCmdResDTO;
    }
}
