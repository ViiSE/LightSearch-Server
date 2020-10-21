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

import lightsearch.server.data.AdminChangeDatabaseCommandDTO;
import lightsearch.server.entity.AdminCommand;

import java.util.List;

public interface AdminCommandProducer {
    AdminCommand getAdminCommandAddBlacklistInstance(List<String> IMEI);
    AdminCommand getAdminCommandClientTimeoutInstance(int timeout);
    AdminCommand getAdminCommandDatabaseInstance(AdminChangeDatabaseCommandDTO cmdDTO);
    AdminCommand getAdminCommandDelBlacklistInstance(String IMEI);
    AdminCommand getAdminCommandDelBlacklistInstance(List<String> IMEIList);
    AdminCommand getAdminCommandClientKickInstance(String IMEI);
    AdminCommand getAdminCommandClientKickInstance(List<String> IMEIList);
}
