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

import lightsearch.server.data.ClientCheckAuthCommandResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("clientCommandResultCheckAuth")
@Scope("prototype")
public class ClientCommandResultCheckAuthImpl implements ClientCommandResult {

    private final boolean isOk;

    public ClientCommandResultCheckAuthImpl(boolean isOk) {
        this.isOk = isOk;
    }

    @Override
    public String message() {
        return String.valueOf(isOk);
    }

    @Override
    public String logMessage() {
        return String.valueOf(isOk);
    }

    @Override
    public boolean isDone() {
        return isOk;
    }

    @Override
    public Object formForSend() {
        ClientCheckAuthCommandResultDTO resultDTO = new ClientCheckAuthCommandResultDTO();
        resultDTO.setOk(isOk);

        return resultDTO;
    }
}
