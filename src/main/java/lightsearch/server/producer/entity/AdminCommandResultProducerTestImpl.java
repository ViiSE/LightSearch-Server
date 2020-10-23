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
import lightsearch.server.entity.*;

import java.util.List;
import java.util.Map;

public class AdminCommandResultProducerTestImpl implements AdminCommandResultProducer {

    @Override
    public AdminCommandResult getAdminCommandResultSimpleInstance(boolean isDone, String message) {
        return new AdminCommandResultSimpleImpl(isDone, message);
    }

    @Override
    public AdminCommandResult getAdminCommandResultHashIMEIInstance(AdminCommandResult admCmdRes, String hashIMEI) {
        return new AdminCommandResultHashIMEIImpl(admCmdRes, hashIMEI);
    }

    @Override
    public AdminCommandResult getAdminCommandResultAddBlacklistInstance(AdminCommandResult admCmdRes, List<String> hashIMEI) {
        return new AdminCommandResultAddBlacklistImpl(admCmdRes, hashIMEI);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithBlacklistInstance(AdminCommandResult admCmdRes, List<String> blacklist) {
        return new AdminCommandResultWithBlacklistImpl(admCmdRes, blacklist);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithClientTimeoutInstance(AdminCommandResult admCmdRes, long clientTimeout) {
        return new AdminCommandResultWithClientTimeoutImpl(admCmdRes, clientTimeout);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithClientInstance(AdminCommandResult admCmdRes, List<Client> clients) {
        return new AdminCommandResultWithClientsImpl(admCmdRes, clients);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithDatasourceInstance(AdminCommandResultWithDatasourceDTO dto) {
        return new AdminCommandResultWithDatasourceImpl(dto);
    }

    @Override
    public AdminCommandResult getAdminCommandResultWithLogsInstance(AdminCommandResult admCmdRes, Map<String, List<String>> logMap) {
        return new AdminCommandResultWithLogsImpl(admCmdRes, logMap);
    }
}
