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

public class ClientCommandWithIpTestNG {

    @Test
    @Parameters({"ip"})
    public void formForSend(String ip) throws JsonProcessingException {
        testBegin(ClientCommandWithIpImpl.class, "formForSend()");

        ClientCommand clientCommand = new ClientCommandWithIpImpl(
                new ClientCommandSimpleImpl(ClientCommands.LOGIN),
                ip);
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();
        String cmd = cmdDTO.getCommand();
        String i = cmdDTO.getIp();

        assertNotNull(cmd, "Command name is null!");
        assertEquals(cmd, ClientCommands.LOGIN);
        assertNotNull(i, "IP is null!");
        assertEquals(i, ip);

        System.out.println(new ObjectMapper().writeValueAsString(cmdDTO));

        testEnd(ClientCommandWithIpImpl.class, "formForSend()");
    }
}
