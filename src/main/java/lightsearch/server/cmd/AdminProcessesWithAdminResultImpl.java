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

import lightsearch.server.cmd.admin.process.AdminProcess;
import lightsearch.server.constants.AdminCommands;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.producer.cmd.admin.process.ProcessAdminProducer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("adminProcessesHolderWithAdminResult")
public class AdminProcessesWithAdminResultImpl implements Processes<AdminCommand, AdminCommandResult> {

    private final Map<String, AdminProcess<AdminCommandResult>> processes = new HashMap<>();

    public AdminProcessesWithAdminResultImpl(ProcessAdminProducer<AdminCommandResult> prcAdmProducer) {
        processes.put(AdminCommands.BLACKLIST, prcAdmProducer.getBlacklistRequestProcessInstance());
        processes.put(AdminCommands.ADD_BLACKLIST, prcAdmProducer.getAddBlacklistProcessInstance());
        processes.put(AdminCommands.DEL_BLACKLIST, prcAdmProducer.getDelBlacklistProcessInstance());
        processes.put(AdminCommands.CLIENT_LIST, prcAdmProducer.getClientListRequestProcessInstance());
        processes.put(AdminCommands.CLIENT_TIMEOUT, prcAdmProducer.getClientTimeoutProcessInstance());
        processes.put(AdminCommands.KICK, prcAdmProducer.getClientKickProcessInstance());
        processes.put(AdminCommands.CHANGE_DATABASE, prcAdmProducer.getChangeDatabaseProcessInstance());
        processes.put(AdminCommands.RESTART, prcAdmProducer.getRestartProcessInstance());
    }

    @Override
    public Process<AdminCommand, AdminCommandResult> get(String command) {
        return processes.get(command);
    }
}
