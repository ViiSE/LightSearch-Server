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

package lightsearch.server.cmd.admin.process;

import lightsearch.server.controller.admin.AdminCommandsController;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("restartProcessWithLogger")
public class RestartProcessWithLoggerImpl implements AdminProcess<AdminCommandResult> {

    private final LoggerServer logger;
    private final AdminProcess<AdminCommandResult> adminProcess;

    public RestartProcessWithLoggerImpl(
            LoggerServer logger,
            @Qualifier("restartProcess") AdminProcess<AdminCommandResult> adminProcess) {
        this.logger = logger;
        this.adminProcess = adminProcess;
    }

    @Override
    public AdminCommandResult apply(AdminCommand command) {
        AdminCommandResult result = adminProcess.apply(command);
        logger.info(AdminCommandsController.class, "Admin started server reboot process");
        return result;
    }
}
