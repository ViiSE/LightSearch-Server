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

package lightsearch.server.producer.entity;

import lightsearch.server.data.ClientCommandResultDTO;
import lightsearch.server.entity.ClientCommandResult;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("clientCommandResultProducerDefault")
public class ClientCommandResultProducerDefaultImpl implements ClientCommandResultProducer {

    private final ApplicationContext ctx;

    public ClientCommandResultProducerDefaultImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public ClientCommandResult getClientCommandResultFromDatabaseInstance(ClientCommandResultDTO clientCommandResultDTO) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultFromDatabase", clientCommandResultDTO);
    }

    @Override
    public ClientCommandResult getClientCommandResultSimpleInstance(boolean isDone, String message) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultSimple", isDone, message);
    }

    @Override
    public ClientCommandResult getClientCommandResultWithTokenInstance(ClientCommandResult clientCommandResult, String token) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultWithToken", clientCommandResult, token);
    }

    @Override
    public ClientCommandResult getClientCommandResultCheckAuthInstance(boolean isOk) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultCheckAuth", isOk);
    }
}
