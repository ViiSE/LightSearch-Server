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

package lightsearch.server.cmd.client.process;

import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("bindProcessWithLogger")
public class BindProcessWithLoggerImpl implements ClientProcess<ClientCommandResult> {

    private final LoggerServer logger;
    private final ClientProcess<ClientCommandResult> clientProcess;

    public BindProcessWithLoggerImpl(
            LoggerServer logger,
            @Qualifier("bindProcess") ClientProcess<ClientCommandResult> clientProcess) {
        this.logger = logger;
        this.clientProcess = clientProcess;
    }

    @Override
    public ClientCommandResult apply(ClientCommand command) {
        ClientCommandResult result = clientProcess.apply(command);

        if (!result.isDone())
            logger.error(BindProcess.class, result.logMessage());

        return result;
    }
}
