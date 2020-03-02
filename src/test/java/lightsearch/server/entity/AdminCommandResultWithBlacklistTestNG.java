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

import lightsearch.server.data.AdminCommandResultDTO;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static test.message.TestMessage.*;

public class AdminCommandResultWithBlacklistTestNG {

    @BeforeClass
    public void setUpClass() {
        testBegin(AdminCommandResultWithBlacklistImpl.class);
    }

    @Test(dataProvider = "createDP")
    public void formForSend(boolean isDone, String message, List<String> blacklist, String cause) throws IOException {
        testMethod("formForSend() " + cause);

        AdminCommandResult admCmdRes = new AdminCommandResultWithBlacklistImpl(
                new AdminCommandResultSimpleImpl(isDone, message),
                blacklist);

        AdminCommandResultDTO admCmdResDTO = (AdminCommandResultDTO) admCmdRes.formForSend();

        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(admCmdResDTO));
    }

    @DataProvider
    public Object[][] createDP() {
        return new Object[][] {
                {true, "Is true!", new ArrayList<String>() {{ add("IMEI1"); add("IMEI2"); }}, "[blacklist is not empty]"},
                {true, "Is true!", new ArrayList<String>(), "[blacklist is empty]"},
        };
    }

    @AfterClass
    public void teardownClass() {
        testEnd(AdminCommandResultWithBlacklistImpl.class);
    }
}
