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

/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.database.cmd.message;

import lightsearch.server.constants.DatabaseCommandMessages;
import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.data.ProductDTO;
import lightsearch.server.entity.ClientCommand;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("databaseCommandMessageConfirmSoftCheckProductsWindowsJSON")
@Scope("prototype")
public class DatabaseCommandMessageConfirmSoftCheckProductsWindowsJSONImpl implements DatabaseCommandMessage {

    private final String ID_FIELD         = DatabaseCommandMessages.ID;
    private final String AMOUNT_FIELD     = DatabaseCommandMessages.AMOUNT;
    
    private final String command;
    private final String userIdent;
    private final String cardCode;
    private final List<ProductDTO> data;

    public DatabaseCommandMessageConfirmSoftCheckProductsWindowsJSONImpl(ClientCommand clientCommand) {
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();

        this.command   = clientCommand.name();
        this.userIdent = cmdDTO.getUserIdentifier();
        this.cardCode  = cmdDTO.getCardCode();
        this.data      = cmdDTO.getData();
    }

    @Override
    public String asString() {
        final StringBuilder rawData = new StringBuilder("[");
        data.forEach(product -> {
            rawData.append("\r\n{\r\n");
            String id = product.getId();
            String amount = product.getAmount();
            rawData.append("\"").append(ID_FIELD).append("\":\"").append(id).append("\",\r\n")
                    .append("\"").append(AMOUNT_FIELD).append("\":\"").append(amount).append("\"\r\n},");
            });

        String rawDataStr = rawData.substring(0, rawData.lastIndexOf("},")) + "}";
        rawDataStr += "\r\n]";

        return "{\r\n"
                    + "\"" + DatabaseCommandMessages.COMMAND + "\":\""  + command + "\",\r\n"
                    + "\"" + DatabaseCommandMessages.USER_IDENT + "\":\"" + userIdent + "\",\r\n"
                    + "\"" + DatabaseCommandMessages.CARD_CODE + "\":\"" + cardCode + "\",\r\n"
                    + "\"" + DatabaseCommandMessages.DATA + "\":" + rawDataStr + "\r\n"
                + "}";
    }
}
