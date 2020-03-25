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

import lightsearch.server.data.ClientCheckAuthCommandResultDTO;
import lightsearch.server.data.ClientCommandResultDTO;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.*;

public class ClientCommandResultCheckAuthTestNG {

    @BeforeClass
    public void setUpClass() {
        testBegin(ClientCommandResultCheckAuthImpl.class);
    }

    @Test(dataProvider = "createDP")
    public void formForSend(boolean isOk, String message, String cause) throws IOException {
        testMethod("formForSend() " + cause);

        ClientCommandResult clientCommandResult = new ClientCommandResultCheckAuthImpl(isOk);
        ClientCheckAuthCommandResultDTO cmdResDTO = (ClientCheckAuthCommandResultDTO) clientCommandResult.formForSend();
        boolean isD = cmdResDTO.isOk();

        assertEquals(isD, isOk);

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
        testEnd(ClientCommandResultCheckAuthImpl.class);
    }
}
