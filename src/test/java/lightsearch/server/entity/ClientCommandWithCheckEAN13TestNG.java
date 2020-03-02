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

public class ClientCommandWithCheckEAN13TestNG {

    @Test
    @Parameters({"checkEan13"})
    public void formForSend(boolean checkEan13) throws JsonProcessingException {
        testBegin(ClientCommandWithCheckEAN13Impl.class, "formForSend()");

        ClientCommand clientCommand = new ClientCommandWithCheckEAN13Impl(
                new ClientCommandSimpleImpl(ClientCommands.BIND_CHECK),
                checkEan13);
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();
        String cmd = cmdDTO.getCommand();
        boolean bc = cmdDTO.getCheckEan13();

        assertNotNull(cmd, "Command name is null!");
        assertEquals(cmd, ClientCommands.BIND_CHECK);
        assertEquals(bc, checkEan13);

        System.out.println(new ObjectMapper().writeValueAsString(cmdDTO));

        testEnd(ClientCommandWithCheckEAN13Impl.class, "formForSend()");
    }
}
