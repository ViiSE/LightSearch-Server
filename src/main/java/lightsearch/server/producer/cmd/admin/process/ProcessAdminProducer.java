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

import lightsearch.server.cmd.admin.process.AdminProcess;

public interface ProcessAdminProducer<R> {
    AdminProcess<R> getHashIMEIProcessInstance();
    AdminProcess<R> getAddBlacklistProcessInstance();
    AdminProcess<R> getDelBlacklistProcessInstance();
    AdminProcess<R> getDelBlacklistListProcessInstance();
    AdminProcess<R> getBlacklistRequestProcessInstance();
    AdminProcess<R> getClientListRequestProcessInstance();
    AdminProcess<R> getClientKickProcessInstance();
    AdminProcess<R> getClientKickListProcessInstance();
    AdminProcess<R> getChangeDatabaseProcessInstance();
    AdminProcess<R> getDatasourceProcessInstance();
    AdminProcess<R> getLogProcessInstance();
    AdminProcess<R> getClientTimeoutProcessInstance();
    AdminProcess<R> getChangeClientTimeoutProcessInstance();
    AdminProcess<R> getRestartProcessInstance();
    AdminProcess<R> getChangePassProcessInstance();
}
