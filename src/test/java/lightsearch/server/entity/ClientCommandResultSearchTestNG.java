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
import lightsearch.server.data.ClientSearchCommandResultDTO;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.IOException;
import java.util.ArrayList;

import static test.message.TestMessage.*;

public class ClientCommandResultSearchTestNG {

    private ClientCommandResult commandResult;

    @BeforeClass
    public void setUpClass() {
        ClientCommandResultDTO resDTO = new ClientCommandResultDTO();
        resDTO.setIsDone(String.valueOf(true));
        resDTO.setMessage("Search");
        resDTO.setData(new ArrayList<>());

        commandResult = new ClientCommandResultSearchImpl(
                new ClientCommandResultFromDatabaseImpl(resDTO));

        testBegin(ClientCommandResultSearchImpl.class);
    }

    @Test
    public void formForSend() throws IOException {
        testMethod("formForSend()");

        ClientSearchCommandResultDTO cmdResDTO = (ClientSearchCommandResultDTO) commandResult.formForSend();
        System.out.println(TestUtils.objectMapperWithJavaTimeModule().writeValueAsString(cmdResDTO));
    }

    @AfterClass
    public void teardownClass() {
        testEnd(ClientCommandResultSearchImpl.class);
    }
}
