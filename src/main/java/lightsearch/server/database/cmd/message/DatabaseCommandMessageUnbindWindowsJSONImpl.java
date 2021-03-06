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
package lightsearch.server.database.cmd.message;

import lightsearch.server.constants.DatabaseCommandMessages;
import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.entity.ClientCommand;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("databaseCommandMessageUnbindWindowsJSON")
@Scope("prototype")
public class DatabaseCommandMessageUnbindWindowsJSONImpl implements DatabaseCommandMessage {

    private final String command;
    private final String factoryBarcode;
    private final String userIdent;

    public DatabaseCommandMessageUnbindWindowsJSONImpl(ClientCommand clientCommand) {
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();

        this.command    = clientCommand.name();
        this.factoryBarcode = cmdDTO.getFactoryBarcode();
        this.userIdent = cmdDTO.getUserIdentifier();
    }

    @Override
    public String asString() {
        return "{\r\n"
                + "\"" + DatabaseCommandMessages.COMMAND + "\":\""  + command + "\",\r\n"
                + "\"" + DatabaseCommandMessages.FACTORY_BARCODE + "\":\"" + factoryBarcode + "\",\r\n"
                + "\"" + DatabaseCommandMessages.USER_IDENT + "\":\"" + userIdent + "\"\r\n"
                + "}";
    }
}
