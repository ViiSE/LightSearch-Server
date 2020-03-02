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

import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("loginProcessWithLogger")
public class LoginProcessWithLogger implements ClientProcess<ClientCommandResult> {

    private final LoggerServer logger;
    private final ClientProcess<ClientCommandResult> clientProcess;

    public LoginProcessWithLogger(
            LoggerServer logger,
            @Qualifier("loginProcess") ClientProcess<ClientCommandResult> clientProcess) {
        this.logger = logger;
        this.clientProcess = clientProcess;
    }

    @Override
    public ClientCommandResult apply(ClientCommand command) {
        ClientCommandResult result = clientProcess.apply(command);

        if(result.isDone()) {
            ClientCommandDTO commandDTO = (ClientCommandDTO) command.formForSend();
            logger.info(LoginProcess.class,
                    "Client connected: " + "IMEI - " + commandDTO.getIMEI() +
                            ", ip - " + commandDTO.getIp() +
                            ", os - " + commandDTO.getOs() +
                            ", model - " + commandDTO.getModel() +
                            ", username - " + commandDTO.getUsername() +
                            ", user_ident - " + commandDTO.getUserIdentifier());
        } else
            logger.error(LoginProcess.class, result.message());

        return result;
    }
}
