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
import lightsearch.server.database.CommandExecutor;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.CommandExecutorException;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import lightsearch.server.producer.entity.ClientCommandResultProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("bindProcess")
public class BindProcess implements ClientProcess<ClientCommandResult> {

    private final Checker<ClientCommand> checker;
    private final DatabaseCommandMessageProducer dbCmdMsgProducer;
    private final CommandExecutor<ClientCommandResult, DatabaseCommandMessage> cmdExec;
    private final ClientCommandResultProducer resultProducer;

    public BindProcess(
            @Qualifier("commandCheckerClientBind") Checker<ClientCommand> checker,
            DatabaseCommandMessageProducer dbCmdMsgProducer,
            CommandExecutor<ClientCommandResult, DatabaseCommandMessage> cmdExec,
            ClientCommandResultProducer resultProducer) {
        this.checker = checker;
        this.dbCmdMsgProducer = dbCmdMsgProducer;
        this.cmdExec = cmdExec;
        this.resultProducer = resultProducer;
    }

    @Override
    public ClientCommandResult apply(ClientCommand command) {
        try {
            checker.check(command);

            DatabaseCommandMessage dbCmdMessage =
                    dbCmdMsgProducer.getDatabaseCommandMessageBindDefaultWindowsJSONInstance(command);

            ClientCommandResult cmdRes = cmdExec.exec(dbCmdMessage);

            return resultProducer.getClientCommandResultBindInstance(cmdRes);
        } catch (CheckerException | CommandExecutorException ex) {
            return resultProducer.getClientCommandResultSimpleInstance(
                    false,
                    ex.getMessage(),
                    ex.getLogMessage());
        }
    }
}
