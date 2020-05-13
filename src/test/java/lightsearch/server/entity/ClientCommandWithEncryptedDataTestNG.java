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

public class ClientCommandWithEncryptedDataTestNG {

    @Test
    @Parameters({"encryptedData"})
    public void formForSend(String encryptedData) throws JsonProcessingException {
        testBegin(ClientCommandWithEncryptedDataImpl.class, "formForSend()");

        ClientCommand clientCommand = new ClientCommandWithEncryptedDataImpl(
                new ClientCommandSimpleImpl(ClientCommands.LOGIN_ENCRYPTED),
                encryptedData);
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();
        String cmd = cmdDTO.getCommand();
        String encData = cmdDTO.getEncryptedData();

        assertNotNull(cmd, "Command name is null!");
        assertEquals(cmd, ClientCommands.LOGIN_ENCRYPTED);
        assertNotNull(encData, "Encrypted data is null!");
        assertEquals(encData, encryptedData);

        System.out.println(new ObjectMapper().writeValueAsString(cmdDTO));

        testEnd(ClientCommandWithEncryptedDataImpl.class, "formForSend()");
    }
}
