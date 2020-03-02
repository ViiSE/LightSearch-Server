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
import lightsearch.server.service.BlacklistService;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("blacklistRequestProcess")
public class BlacklistRequestProcess implements AdminProcess<AdminCommandResult> {

    private final BlacklistService<String> blacklistService;
    private final AdminCommandResultProducer adminCommandResultProducer;

    public BlacklistRequestProcess(
            BlacklistService<String> blacklistService,
            AdminCommandResultProducer adminCommandResultProducer) {
        this.blacklistService = blacklistService;
        this.adminCommandResultProducer = adminCommandResultProducer;
    }

    @Override
    public AdminCommandResult apply(AdminCommand ignore) {
        return adminCommandResultProducer
                .getAdminCommandResultWithBlacklistInstance(
                        adminCommandResultProducer.getAdminCommandResultSimpleInstance(true, "OK"),
                        blacklistService.blacklist());
    }
}
