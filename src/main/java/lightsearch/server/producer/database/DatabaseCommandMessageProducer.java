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

package lightsearch.server.producer.database;

import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.entity.ClientCommand;

public interface DatabaseCommandMessageProducer {
    DatabaseCommandMessage getDatabaseCommandMessageConnectionWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageSearchWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageOpenSoftCheckWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageCancelSoftCheckWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageCloseSoftCheckWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageConfirmSoftCheckProductsWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageBindCheckWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageBindWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageUnbindCheckWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageUnbindWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageSearchSoftCheckWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageTKListWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageSkladListWindowsJSONInstance(ClientCommand command);
    DatabaseCommandMessage getDatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONInstance(ClientCommand command);
}