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

import lightsearch.server.data.ClientCommandResultKeyDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("clientCommandResultKey")
@Scope("prototype")
public class ClientCommandResultKeyImpl implements ClientCommandResult {

    private final String key;
    private final String type;
    private final String alg;

    public ClientCommandResultKeyImpl(String key, String type, String alg) {
        this.key = key;
        this.type = type;
        this.alg = alg;
    }

    @Override
    public String message() {
        return "OK";
    }

    @Override
    public String logMessage() {
        return "Client request key";
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public Object formForSend() {
        ClientCommandResultKeyDTO resultDTO = new ClientCommandResultKeyDTO();
        resultDTO.setKey(key);
        resultDTO.setType(type);
        resultDTO.setAlg(alg);

        return resultDTO;
    }
}
