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
 */

package lightsearch.server.database.cmd.message;

import lightsearch.server.constants.DatabaseCommandMessages;
import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.data.ProductDTO;
import lightsearch.server.entity.ClientCommand;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("databaseCommandMessageUpdateSoftCheckProductsWindowsJSON")
@Scope("prototype")
public class DatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONImpl implements DatabaseCommandMessage {

    private final String ID_FIELD = DatabaseCommandMessages.ID;

    private final String command;
    private final String username;
    private final List<ProductDTO> data;

    public DatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONImpl(ClientCommand clientCommand) {
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();

        this.command  = clientCommand.name();
        this.username = cmdDTO.getUserIdentifier();
        this.data     = cmdDTO.getData();
    }

    @Override
    public String asString() {
        final StringBuilder rawData = new StringBuilder("[");
        data.forEach(product -> {
            rawData.append("\r\n{\r\n");
            String id = product.getId();
            rawData.append("\"").append(ID_FIELD).append("\":\"").append(id).append("\"\r\n},");
        });

        String rawDataStr = rawData.substring(0, rawData.lastIndexOf("},")) + "}";
        rawDataStr += "\r\n]";

        return "{\r\n"
                    + "\"" + DatabaseCommandMessages.COMMAND + "\":\""  + command + "\",\r\n"
                    + "\"" + DatabaseCommandMessages.USERNAME + "\":\"" + username + "\",\r\n"
                    + "\"" + DatabaseCommandMessages.DATA + "\":" + rawDataStr + "\r\n"
                + "}";
    }
}
