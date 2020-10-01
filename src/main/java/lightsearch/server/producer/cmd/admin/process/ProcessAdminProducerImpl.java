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

package lightsearch.server.producer.cmd.admin.process;

import lightsearch.server.cmd.admin.process.*;
import lightsearch.server.entity.AdminCommandResult;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("processorAdminProducer")
public class ProcessAdminProducerImpl implements ProcessAdminProducer<AdminCommandResult> {

    private final ApplicationContext ctx;

    public ProcessAdminProducerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public AdminProcess<AdminCommandResult> getAddBlacklistProcessInstance() {
        return ctx.getBean(AddBlacklistProcess.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getDelBlacklistProcessInstance() {
        return ctx.getBean(DelBlacklistProcess.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getDelBlacklistListProcessInstance() {
        return ctx.getBean(DelBlacklistListProcess.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getBlacklistRequestProcessInstance() {
        return ctx.getBean(BlacklistRequestProcessWithLoggerImpl.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getClientListRequestProcessInstance() {
        return ctx.getBean(ClientListRequestProcessWithLoggerImpl.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getClientKickProcessInstance() {
        return ctx.getBean(ClientKickProcess.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getChangeDatabaseProcessInstance() {
        return ctx.getBean(ChangeDatabaseProcess.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getClientTimeoutProcessInstance() {
        return ctx.getBean(ClientTimeoutProcess.class);
    }

    @Override
    public AdminProcess<AdminCommandResult> getRestartProcessInstance() {
        return ctx.getBean(RestartProcessWithLoggerImpl.class);
    }
}
