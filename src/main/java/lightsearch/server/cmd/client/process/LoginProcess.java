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

import lightsearch.server.checker.Checker;
import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.data.ClientDTO;
import lightsearch.server.database.CommandExecutor;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.entity.Client;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.ClientNotFoundException;
import lightsearch.server.exception.CommandExecutorException;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import lightsearch.server.producer.entity.ClientCommandResultProducer;
import lightsearch.server.service.ClientsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("loginProcess")
public class LoginProcess implements ClientProcess<ClientCommandResult> {

    private final ClientsService<String, Client, List<Client>> clientsService;
    private final Checker<ClientCommand> checker;
    private final DatabaseCommandMessageProducer dbCmdMsgProducer;
    private final CommandExecutor<ClientCommandResult, DatabaseCommandMessage> cmdExec;
    private final ClientCommandResultProducer resultProducer;

    public LoginProcess(
            @Qualifier("clientsServiceDatabase") ClientsService<String, Client, List<Client>> clientsService,
            @Qualifier("commandCheckerClientAuthorization") Checker<ClientCommand> checker,
            DatabaseCommandMessageProducer dbCmdMsgProducer,
            CommandExecutor<ClientCommandResult, DatabaseCommandMessage> cmdExec,
            ClientCommandResultProducer resultProducer) {
        this.clientsService = clientsService;
        this.checker = checker;
        this.dbCmdMsgProducer = dbCmdMsgProducer;
        this.cmdExec = cmdExec;
        this.resultProducer = resultProducer;
    }

    @Override
    public ClientCommandResult apply(ClientCommand command) {
        try {
            checker.check(command);

            ClientCommandDTO cmdDTO = (ClientCommandDTO) command.formForSend();
            clientsService.checkClientByUsernameAndPassword(cmdDTO.getUsername(), cmdDTO.getPassword());

            DatabaseCommandMessage dbCmdMessage =
                    dbCmdMsgProducer.getDatabaseCommandMessageConnectionWindowsJSONInstance(command);
            ClientCommandResult result = cmdExec.exec(dbCmdMessage);

            String jwtToken = clientsService.addClient(cmdDTO.getIMEI(), cmdDTO.getUsername()).toString();
            return resultProducer.getClientCommandResultLoginInstance(
                    resultProducer.getClientCommandResultWithTokenInstance(result, jwtToken),
                    ((ClientDTO)
                            clientsService
                                    .client(cmdDTO.getIMEI())
                                    .formForSend())
                            .getIMEI());
        } catch (CheckerException | CommandExecutorException | ClientNotFoundException ex) {
            return resultProducer.getClientCommandResultSimpleInstance(
                    false,
                    ex.getMessage(),
                    ex.getLogMessage());
        }
    }
}
