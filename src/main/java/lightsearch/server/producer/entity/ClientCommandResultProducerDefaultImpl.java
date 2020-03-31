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
    public ClientCommandResult getClientCommandResultSimpleInstance(boolean isDone, String message, String logMessage) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultSimple", isDone, message, logMessage);
    }

    @Override
    public ClientCommandResult getClientCommandResultWithTokenInstance(ClientCommandResult clientCommandResult, String token) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultWithToken", clientCommandResult, token);
    }

    @Override
    public ClientCommandResult getClientCommandResultCheckAuthInstance(boolean isOk) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultCheckAuth", isOk);
    }

    @Override
    public ClientCommandResult getClientCommandResultLoginInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultLogin", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultSearchInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultSearch", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultOpenSoftCheckInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultOpenSoftCheck", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultCloseSoftCheckInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultCloseSoftCheck", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultCancelSoftCheckInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultCancelSoftCheck", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultConfirmSoftCheckProductsInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultConfirmSoftCheckProducts", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultBindCheckInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultBindCheck", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultBindInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultBind", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultUnbindCheckInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultUnbindCheck", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultUnbindInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultUnbind", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultSkladListInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultSkladList", clientCommandResult);
    }

    @Override
    public ClientCommandResult getClientCommandResultTKListInstance(ClientCommandResult clientCommandResult) {
        return (ClientCommandResult) ctx.getBean("clientCommandResultTKList", clientCommandResult);
    }
}
