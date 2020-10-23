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

package lightsearch.server.producer.entity;

import lightsearch.server.data.AdminCommandResultWithDatasourceDTO;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.entity.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("adminCommandResultProducer")
public class AdminCommandResultProducerImpl implements AdminCommandResultProducer {

    private final ApplicationContext ctx;


    public AdminCommandResultProducerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public AdminCommandResult getAdminCommandResultSimpleInstance(boolean isDone, String message) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultSimple", isDone, message);
    }

    @Override
    public AdminCommandResult getAdminCommandResultHashIMEIInstance(AdminCommandResult admCmdRes, String hashIMEI) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultHashIMEI", admCmdRes, hashIMEI);
    }

    @Override
    public AdminCommandResult getAdminCommandResultAddBlacklistInstance(AdminCommandResult admCmdRes, List<String> hashIMEIList) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultAddBlacklist", admCmdRes, hashIMEIList);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithBlacklistInstance(AdminCommandResult admCmdRes, List<String> blacklist) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultWithBlacklist", admCmdRes, blacklist);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithClientTimeoutInstance(AdminCommandResult admCmdRes, long clientTimeout) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultWithClientTimeout", admCmdRes, clientTimeout);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithClientInstance(AdminCommandResult admCmdRes, List<Client> clients) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultWithClients", admCmdRes, clients);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithDatasourceInstance(AdminCommandResultWithDatasourceDTO dto) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultWithDatasource", dto);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithLogsInstance(AdminCommandResult admCmdRes, Map<String, List<String>> logMap) {
        return (AdminCommandResult) ctx.getBean("adminCommandResultWithLogs", admCmdRes, logMap);
    }
}
