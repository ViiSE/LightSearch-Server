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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.*;

public class ClientCommandResultSimpleTestNG {

    @BeforeClass
    public void setUpClass() {
        testBegin(ClientCommandResultSimpleImpl.class);
    }

    @Test(dataProvider = "createDP")
    public void formForSend(boolean isDone, String message, String cause) throws IOException {
        testMethod("formForSend() " + cause);

        ClientCommandResult clientCommandResult = new ClientCommandResultSimpleImpl(isDone, message, message);
        ClientCommandResultDTO cmdResDTO = (ClientCommandResultDTO) clientCommandResult.formForSend();
        boolean isD = cmdResDTO.getIsDone();
        String msg = cmdResDTO.getMessage();

        assertEquals(isD, isDone);
        assertEquals(msg, message);

        System.out.println(TestUtils.objectMapperWithJavaTimeModule().writeValueAsString(cmdResDTO));
    }

    @DataProvider
    public Object[][] createDP() {
        return new Object[][] {
                {true, "Is true!", "[true]"},
                {false, "Is false!", "[false]"}
        };
    }

    @AfterClass
    public void teardownClass() {
        testEnd(ClientCommandResultSimpleImpl.class);
    }
}
