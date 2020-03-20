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

package lightsearch.server.cmd;

import lightsearch.server.cmd.client.process.ClientProcess;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.producer.cmd.client.process.ProcessClientProducer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("clientProcessesHolderWithClientResult")
public class ClientProcessesWithClientResultImpl implements Processes<ClientCommand, ClientCommandResult> {

    private final Map<String, ClientProcess<ClientCommandResult>> processes = new HashMap<>();

    public ClientProcessesWithClientResultImpl(ProcessClientProducer<ClientCommandResult> prcProducer) {
        processes.put(ClientCommands.LOGIN, prcProducer.getLoginProcessInstance());
        processes.put(ClientCommands.SEARCH, prcProducer.getSearchProcessInstance());
        processes.put(ClientCommands.OPEN_SOFT_CHECK, prcProducer.getOpenSoftCheckProcessInstance());
        processes.put(ClientCommands.CANCEL_SOFT_CHECK, prcProducer.getCancelSoftCheckProcessInstance());
        processes.put(ClientCommands.CONFIRM_SOFT_CHECK_PRODUCTS, prcProducer.getConfirmSoftCheckProductsProcessInstance());
        processes.put(ClientCommands.CLOSE_SOFT_CHECK, prcProducer.getCloseSoftCheckProcessInstance());
        processes.put(ClientCommands.BIND_CHECK, prcProducer.getBindCheckProcessInstance());
        processes.put(ClientCommands.BIND, prcProducer.getBindProcessInstance());
        processes.put(ClientCommands.UNBIND, prcProducer.getUnbindProcessInstance());
        processes.put(ClientCommands.UNBIND_CHECK, prcProducer.getUnbindCheckProcessInstance());
        processes.put(ClientCommands.SEARCH_SOFT_CHECK, prcProducer.getSearchSoftCheckProcessInstance());
        processes.put(ClientCommands.SKLAD_LIST, prcProducer.getSkladListProcessInstance());
        processes.put(ClientCommands.TK_LIST, prcProducer.getTKListProcessInstance());
    }

    @Override
    public Process<ClientCommand, ClientCommandResult> get(String command) {
        return processes.get(command);
    }
}
