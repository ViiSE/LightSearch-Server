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
import lightsearch.server.data.ResponseResultDTO;
import org.testng.annotations.Test;
import test.TestUtils;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class ResponseResultSimpleTestNG {

    @Test
    public void formForSend() throws JsonProcessingException {
        testBegin(ResponseResultSimpleImpl.class, "formForSend()");

        LocalDateTime dt = LocalDateTime.now();
        ResponseResult rr = new ResponseResultSimpleImpl(dt);
        ResponseResultDTO rrDTO = (ResponseResultDTO) rr.formForSend();

        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(rrDTO));

        assertEquals(rrDTO.getDateTime(), dt);

        testEnd(ResponseResultSimpleImpl.class, "formForSend()");
    }
}
