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

package lightsearch.server.cmd.client.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.Processes;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/test-firebird.properties")
public class SearchProcessIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Processes<ClientCommand, ClientCommandResult> holder;

    @Test
    @Parameters({"barcode", "sklad", "TK"})
    public void apply(String barcode, String sklad, String TK) throws JsonProcessingException {
        ClientCommand clientCommand = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithTKImpl(
                        new ClientCommandWithSkladImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.SEARCH),
                                sklad),
                        TK),
                barcode);

        ClientCommandResult result = holder.get(ClientCommands.SEARCH).apply(clientCommand);

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.formForSend()));
    }
}
