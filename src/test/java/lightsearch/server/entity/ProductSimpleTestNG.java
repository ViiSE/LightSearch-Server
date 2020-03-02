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
import lightsearch.server.data.ProductDTO;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class ProductSimpleTestNG {

    @Test
    @Parameters({"id"})
    public void formForSend(String id) throws JsonProcessingException {
        testBegin(ProductSimpleImpl.class, "formForSend()");

        Product product = new ProductSimpleImpl(id);

        ProductDTO prDTO = (ProductDTO) product.formForSend();
        String i = prDTO.getId();

        assertNotNull(i, "ID is null!");
        assertEquals(i, id);

        System.out.println(new ObjectMapper().writeValueAsString(prDTO));

        testEnd(ProductSimpleImpl.class, "formForSend()");
    }
}
