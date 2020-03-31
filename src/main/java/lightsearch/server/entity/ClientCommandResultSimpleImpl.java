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

@Component("clientCommandResultSimple")
@Scope("prototype")
public class ClientCommandResultSimpleImpl implements ClientCommandResult {

    private final boolean isDone;
    private final String message;
    private final String logMessage;

    public ClientCommandResultSimpleImpl(boolean isDone, String message, String logMessage) {
        this.isDone = isDone;
        this.message = message;
        this.logMessage = logMessage;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String logMessage() {
        return logMessage;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public Object formForSend() {
        ClientCommandResultDTO resultDTO = new ClientCommandResultDTO();
        resultDTO.setIsDone(isDone ? "true" : "false");
        resultDTO.setMessage(message);

        return resultDTO;
    }
}
