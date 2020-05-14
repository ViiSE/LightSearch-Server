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
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandSimpleImpl;
import lightsearch.server.entity.ClientCommandWithBarcodeImpl;
import lightsearch.server.entity.ClientCommandWithUsernameImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class DatabaseCommandMessageSearchSoftCheckWindowsJSONTestNG {

    DatabaseCommandMessage dbCmdMessage;

    @BeforeClass
    @Parameters({"barcode", "username"})
    public void setUpClass(String barcode, String username) {
        String command = ClientCommands.SEARCH_SOFT_CHECK;

        ClientCommand clientCommand = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithUsernameImpl(
                        new ClientCommandSimpleImpl(command),
                        username),
                barcode);

        dbCmdMessage = new DatabaseCommandMessageSearchSoftCheckWindowsJSONImpl(clientCommand);

        testBegin(DatabaseCommandMessageSearchSoftCheckWindowsJSONImpl.class);
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
        testEnd(DatabaseCommandMessageSearchSoftCheckWindowsJSONImpl.class);
    }
}
