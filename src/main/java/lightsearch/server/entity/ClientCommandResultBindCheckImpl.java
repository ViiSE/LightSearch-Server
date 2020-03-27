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

import lightsearch.server.data.ClientBindCheckCommandResultDTO;
import lightsearch.server.data.ClientCommandResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("clientCommandResultBindCheck")
@Scope("prototype")
public class ClientCommandResultBindCheckImpl implements ClientCommandResult {

    private final ClientCommandResult clientCommandResult;

    public ClientCommandResultBindCheckImpl(ClientCommandResult clientCommandResult) {
        this.clientCommandResult = clientCommandResult;
    }

    @Override
    public String message() {
        return clientCommandResult.message();
    }

    @Override
    public boolean isDone() {
        return clientCommandResult.isDone();
    }

    @Override
    public Object formForSend() {
        ClientCommandResultDTO resDTO = (ClientCommandResultDTO) clientCommandResult.formForSend();
        ClientBindCheckCommandResultDTO result = new ClientBindCheckCommandResultDTO();
        result.setIsDone(resDTO.getIsDone() ? "true" : "false");
        result.setData(resDTO.getData());

        return result;
    }
}
