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

import lightsearch.server.data.ClientCommandResultDTO;
import lightsearch.server.data.ProductDTO;
import lightsearch.server.data.ResponseResultDTO;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class ResponseResultWithCommandResultTestNG {

    private ResponseResult responseResult;

    @BeforeClass
    public void setUpClass() {
        LocalDateTime dt = LocalDateTime.now();
        ResponseResult rr = new ResponseResultSimpleImpl(dt);

        ClientCommandResultDTO cmdResDTO = new ClientCommandResultDTO();
        cmdResDTO.setIsDone(String.valueOf(true));
        cmdResDTO.setMessage("Done");
        cmdResDTO.setSkladList(new ArrayList<>() {{
            add("Sklad1");
            add("Sklad2");
            add("Sklad3"); }});
        cmdResDTO.setTKList(new ArrayList<>() {{
            add("TK1");
            add("TK2"); }});
        cmdResDTO.setUserIdentifier("101");
        cmdResDTO.setToken("veryBigToken.afrjohiGB;ORJNFO24NF.FSOghp43obgojrenpgorjnj");
        cmdResDTO.setData(new ArrayList<>() {{
            add(new ProductDTO() {{
                setId("id1");
                setEi("pcs.");
                setPrice(46.54f);
                setAmount(34.0f);
                setName("Item1");
                setSubdiv("Sklad1");
            }});
            add(new ProductDTO() {{
                setId("id2");
                setEi("pcs.");
                setPrice(50.f);
                setAmount(1.0f);
                setName("Item2");
                setSubdiv("Sklad2");
            }});
            add(new ProductDTO() {{
                setId("id1");
                setEi("pcs.");
                setPrice(60.99f);
                setAmount(10.0f);
                setName("Item3");
                setSubdiv("TK1");
            }});
        }});

        ClientCommandResult cmdRes = new ClientCommandResultFromDatabaseImpl(cmdResDTO);

        responseResult = new ResponseResultWithCommandResultImpl(rr, cmdRes);
    }

    @Test
    public void formForSend() throws IOException {
        testBegin(ResponseResultWithCommandResultImpl.class, "formForSend()");

        ResponseResultDTO rrDTO = (ResponseResultDTO) responseResult.formForSend();

        ClientCommandResult cmdRes = rrDTO.getCmdRes();
        ClientCommandResultDTO cmdResDTO = (ClientCommandResultDTO) cmdRes.formForSend();

        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(cmdResDTO));

        testEnd(ResponseResultWithCommandResultImpl.class, "formForSend()");
    }
}
