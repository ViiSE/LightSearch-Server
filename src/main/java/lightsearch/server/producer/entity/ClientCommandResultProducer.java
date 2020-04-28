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

public interface ClientCommandResultProducer {
    ClientCommandResult getClientCommandResultFromDatabaseInstance(ClientCommandResultDTO clientCommandResultDTO);
    ClientCommandResult getClientCommandResultSimpleInstance(boolean isDone, String message, String logMessage);
    ClientCommandResult getClientCommandResultWithTokenInstance(ClientCommandResult clientCommandResult, String token);
    ClientCommandResult getClientCommandResultCheckAuthInstance(boolean isOk);
    ClientCommandResult getClientCommandResultLoginInstance(ClientCommandResult clientCommandResult, String hashIMEI);
    ClientCommandResult getClientCommandResultSearchInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultOpenSoftCheckInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultCloseSoftCheckInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultCancelSoftCheckInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultConfirmSoftCheckProductsInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultBindCheckInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultBindInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultUnbindCheckInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultUnbindInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultSkladListInstance(ClientCommandResult clientCommandResult);
    ClientCommandResult getClientCommandResultTKListInstance(ClientCommandResult clientCommandResult);
}
