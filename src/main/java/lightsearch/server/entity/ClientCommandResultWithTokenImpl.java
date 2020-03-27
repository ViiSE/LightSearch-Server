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

import lightsearch.server.data.ClientCommandResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("clientCommandResultWithToken")
@Scope("prototype")
public class ClientCommandResultWithTokenImpl implements ClientCommandResult {

    private final ClientCommandResult clientCommandResult;
    private final String token;

    public ClientCommandResultWithTokenImpl(ClientCommandResult clientCommandResult, String token) {
        this.clientCommandResult = clientCommandResult;
        this.token = token;
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
        ClientCommandResultDTO resultDTO = (ClientCommandResultDTO) clientCommandResult.formForSend();
        resultDTO.setToken(token);

        return resultDTO;
    }
}