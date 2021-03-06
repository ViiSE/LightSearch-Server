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

package lightsearch.server.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.data.ClientCommandDTO;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class ClientCommandWithFactoryBarcodeTestNG {

    @Test
    @Parameters({"factoryBarcode"})
    public void formForSend(String factoryBarcode) throws JsonProcessingException {
        testBegin(ClientCommandWithFactoryBarcodeImpl.class, "formForSend()");

        ClientCommand clientCommand = new ClientCommandWithFactoryBarcodeImpl(
                new ClientCommandSimpleImpl(ClientCommands.BIND),
                factoryBarcode);
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();
        String cmd = cmdDTO.getCommand();
        String bc = cmdDTO.getFactoryBarcode();

        assertNotNull(cmd, "Command name is null!");
        assertEquals(cmd, ClientCommands.BIND);
        assertNotNull(bc, "Factory barcode is null!");
        assertEquals(bc, factoryBarcode);

        System.out.println(new ObjectMapper().writeValueAsString(cmdDTO));

        testEnd(ClientCommandWithFactoryBarcodeImpl.class, "formForSend()");
    }
}
