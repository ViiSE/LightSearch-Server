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
import lightsearch.server.data.ProductDTO;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class ClientCommandWithProductsTestNG {

    @Test
    public void formForSend() throws JsonProcessingException {
        testBegin(ClientCommandWithProductsImpl.class, "formForSend()");

        List<Product> products = new ArrayList<>() {{
            add(new ProductSimpleImpl("id1"));
            add(new ProductSimpleImpl("id2"));
            add(new ProductSimpleImpl("id3"));
        }};

        ClientCommand clientCommand = new ClientCommandWithProductsImpl(
                new ClientCommandSimpleImpl(ClientCommands.LOGIN),
                products);
        ClientCommandDTO cmdDTO = (ClientCommandDTO) clientCommand.formForSend();
        String cmd = cmdDTO.getCommand();
        List<ProductDTO> productsDTO = cmdDTO.getData();

        assertNotNull(cmd, "Command name is null!");
        assertEquals(cmd, ClientCommands.LOGIN);
        assertNotNull(productsDTO, "List of products is null!");

        List<ProductDTO> prevPrDTO = products
                        .stream()
                        .map(product -> (ProductDTO) product.formForSend())
                        .collect(Collectors.toList());

        for(int i = 0; i < products.size(); i++) {
            String prevId = prevPrDTO.get(i).getId();
            String nextId = prevPrDTO.get(i).getId();
            assertEquals(prevId, nextId);
        }

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cmdDTO));

        testEnd(ClientCommandWithProductsImpl.class, "formForSend()");
    }
}
