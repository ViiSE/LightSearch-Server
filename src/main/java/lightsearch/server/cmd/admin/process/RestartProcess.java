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
package lightsearch.server.cmd.admin.process;

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("restartProcess")
public class RestartProcess implements AdminProcess<AdminCommandResult> {

    private final RestartEndpoint restartEndpoint;
    private final AdminCommandResultProducer admCmdResultProducer;

    public RestartProcess(RestartEndpoint restartEndpoint, AdminCommandResultProducer admCmdResultProducer) {
        this.restartEndpoint = restartEndpoint;
        this.admCmdResultProducer = admCmdResultProducer;
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand ignore) {
        restartEndpoint.restart();

        return admCmdResultProducer
                .getAdminCommandResultSimpleInstance(true, "Server reboot was successful!");
    }
}
