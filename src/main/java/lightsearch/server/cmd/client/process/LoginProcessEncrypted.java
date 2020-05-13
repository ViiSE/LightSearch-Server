/*
 *  Copyright 2019 ViiSE.
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
 */

package lightsearch.server.cmd.client.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.data.ClientLoginCommandDTO;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.exception.InformationException;
import lightsearch.server.producer.entity.ClientCommandProducer;
import lightsearch.server.producer.entity.ClientCommandResultProducer;
import lightsearch.server.producer.security.InformationProducer;
import lightsearch.server.security.Information;
import lightsearch.server.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component("loginProcessEncrypted")
public class LoginProcessEncrypted implements ClientProcess<ClientCommandResult> {

    private final ClientProcess<ClientCommandResult> loginProcess;
    private final Keys<PublicKey, PrivateKey> keys;
    private final InformationProducer infoProducer;
    private final ClientCommandProducer cmdProducer;
    private final ClientCommandResultProducer resultProducer;
    private final ObjectMapper mapper;

    public LoginProcessEncrypted(
            @Qualifier("loginProcess") ClientProcess<ClientCommandResult> loginProcess,
            Keys<PublicKey, PrivateKey> keys,
            InformationProducer infoProducer,
            ClientCommandProducer cmdProducer,
            ClientCommandResultProducer resultProducer,
            ObjectMapper mapper) {
        this.loginProcess = loginProcess;
        this.keys = keys;
        this.infoProducer = infoProducer;
        this.cmdProducer = cmdProducer;
        this.resultProducer = resultProducer;
        this.mapper = mapper;
    }

    @Override
    public ClientCommandResult apply(ClientCommand command) {
        try {
            ClientCommandDTO encDTO = (ClientCommandDTO) command.formForSend();
            Information<String> decInfo = infoProducer.getDecryptedInformationRSAInstance(
                    infoProducer.getEncryptedInformationInstance(encDTO.getEncryptedData()),
                    keys);

            String decDTO = decInfo.data();
            ClientLoginCommandDTO cmdDTO = mapper.readValue(decDTO, ClientLoginCommandDTO.class);
            ClientCommand cmd = cmdProducer
                    .getClientCommandWithUsernameAndPasswordInstance(
                            cmdProducer.getClientCommandWithIpInstance(
                                    cmdProducer.getClientCommandWithModelInstance(
                                            cmdProducer.getClientCommandWithOsInstance(
                                                    cmdProducer.getClientCommandWithUserIdentifierInstance(
                                                            cmdProducer.getClientCommandWithIMEIInstance(
                                                                    cmdProducer.getClientCommandSimpleInstance(
                                                                            ClientCommands.LOGIN),
                                                                    cmdDTO.getIMEI()),
                                                            cmdDTO.getUserIdentifier()),
                                                    cmdDTO.getOs()),
                                            cmdDTO.getModel()),
                                    cmdDTO.getIp()),
                            cmdDTO.getUsername(),
                            cmdDTO.getPassword());
            return loginProcess.apply(cmd);
        } catch (InformationException ex) {
            return error(ex.getMessage(), ex.getLogMessage());
        } catch (IOException ex) {
            return error("Произошла внутренняя ошибка LightSearch.", ex.getMessage());
        }
    }

    private ClientCommandResult error(String message, String logMessage) {
        return resultProducer.getClientCommandResultSimpleInstance(
                false,
                message,
                logMessage);
    }
}
