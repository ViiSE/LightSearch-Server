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
import lightsearch.server.data.AdminCommandResultWithLogsDTO;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.entity.Client;

import java.util.List;
import java.util.Map;

public interface AdminCommandResultProducer {
    AdminCommandResult getAdminCommandResultSimpleInstance(boolean isDone, String message);
    AdminCommandResult getAdminCommandResultHashIMEIInstance(AdminCommandResult admCmdRes, String hashIMEI);
    AdminCommandResult getAdminCommandResultAddBlacklistInstance(AdminCommandResult admCmdRes, List<String> hashIMEIList);
    AdminCommandResult getAdminCommandResultWithBlacklistInstance(AdminCommandResult admCmdRes, List<String> blacklist);
    AdminCommandResult getAdminCommandResultWithClientTimeoutInstance(AdminCommandResult admCmdRes, long clientTimeout);
    AdminCommandResult getAdminCommandResultWithClientInstance(AdminCommandResult admCmdRes, List<Client> clients);
    AdminCommandResult getAdminCommandResultWithDatasourceInstance(AdminCommandResultWithDatasourceDTO dto);
    AdminCommandResult getAdminCommandResultWithLogsInstance(AdminCommandResult admCmdRes, Map<String, List<String>> logMap);
}
