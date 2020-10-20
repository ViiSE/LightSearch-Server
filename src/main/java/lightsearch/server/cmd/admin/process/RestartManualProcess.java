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

import lightsearch.server.LightSearchServer;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("restartManualProcess")
public class RestartManualProcess implements AdminProcess<AdminCommandResult> {

    private ConfigurableApplicationContext ctx;
    private final AdminCommandResultProducer admCmdResultProducer;

    public RestartManualProcess(ConfigurableApplicationContext ctx, AdminCommandResultProducer admCmdResultProducer) {
        this.ctx = ctx;
        this.admCmdResultProducer = admCmdResultProducer;
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand ignore) {
        ApplicationArguments args = ctx.getBean(ApplicationArguments.class);

        Thread reloadTh = new Thread(() -> {
            ctx.close();
            ctx = SpringApplication.run(LightSearchServer.class, args.getSourceArgs());
        });

        reloadTh.setDaemon(false);
        reloadTh.start();
        try {
            reloadTh.join();
            return admCmdResultProducer
                    .getAdminCommandResultSimpleInstance(true, "Server reboot was successful!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
