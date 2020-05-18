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

package lightsearch.server.database.cmd.message;

import lightsearch.server.constants.ClientCommands;
import lightsearch.server.entity.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class DatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONTestNG {

    DatabaseCommandMessage dbCmdMessage;

    @BeforeClass
    @Parameters({"username"})
    public void setUpClass(String username) {
        String command = ClientCommands.UPDATE_SOFT_CHECK_PRODUCTS;

        ClientCommand clientCommand = new ClientCommandWithUsernameImpl(
                new ClientCommandWithProductsImpl(
                        new ClientCommandSimpleImpl(command),
                        new ArrayList<>() {{
                            add(new ProductSimpleImpl("id1"));
                            add(new ProductSimpleImpl("id2"));
                            add(new ProductSimpleImpl("id3"));
                        }}),
                username);

        dbCmdMessage = new DatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONImpl(clientCommand);

        testBegin(DatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONImpl.class);
    }

    @Test
    public void asString() {
        testMethod("asString()");

        String message = dbCmdMessage.asString();
        assertTrue(message.contains("\r\n"));

        System.out.println(message);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(DatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONImpl.class);
    }
}
