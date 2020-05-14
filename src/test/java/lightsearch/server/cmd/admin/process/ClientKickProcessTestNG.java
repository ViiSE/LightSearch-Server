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

package lightsearch.server.cmd.admin.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import lightsearch.server.checker.Checker;
import lightsearch.server.checker.CommandCheckerAdminKickClientImpl;
import lightsearch.server.checker.LightSearchCheckerDefaultImpl;
import lightsearch.server.data.AdminCommandSimpleResultDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandKickImpl;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.entity.Client;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.producer.entity.AdminCommandResultProducerTestImpl;
import lightsearch.server.service.ClientsService;
import lightsearch.server.service.ClientsServiceTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.TestUtils;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class ClientKickProcessTestNG {

    private ClientsService<String, Client> clientsService;
    private AdminProcess<AdminCommandResult> clKickProc;

    @BeforeClass
    public void setUpClass() {
        fillClientService();
        Checker<AdminCommand> checker = new CommandCheckerAdminKickClientImpl(
                clientsService,
                new LightSearchCheckerDefaultImpl());

        AdminCommandResultProducer admCmdResProducer = new AdminCommandResultProducerTestImpl();
        clKickProc = new ClientKickProcess(
                clientsService,
                checker,
                admCmdResProducer);

        testBegin(ClientKickProcess.class);
    }

    @Test
    public void apply() throws JsonProcessingException {
        testMethod("apply()");

        AdminCommand admCmd = new AdminCommandKickImpl("id1");
        AdminCommandResult cmdRes = clKickProc.apply(admCmd);
        AdminCommandSimpleResultDTO cmdResDTO = (AdminCommandSimpleResultDTO) cmdRes.formForSend();

        System.out.println("Response: ");
        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(cmdResDTO));
        System.out.println("--------------------------");

        assertTrue(cmdResDTO.getIsDone());
        assertFalse(clientsService.contains("111111111111111"));
    }

    @Test
    public void apply_clientNotFound() throws JsonProcessingException {
        testMethod("apply() [Client not found]");

        AdminCommand admCmd = new AdminCommandKickImpl("id2");
        AdminCommandResult cmdRes = clKickProc.apply(admCmd);
        AdminCommandSimpleResultDTO cmdResDTO = (AdminCommandSimpleResultDTO) cmdRes.formForSend();

        System.out.println("Response: ");
        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(cmdResDTO));
        System.out.println("--------------------------");

        assertFalse(cmdResDTO.getIsDone());
        String message = cmdResDTO.getMessage().toLowerCase();
        assertTrue(message.contains("not found"));
    }

    private void fillClientService() {
        clientsService = new ClientsServiceTestImpl();
        clientsService.addClient("111111111111111", "User1");
    }

    @AfterClass
    public void teardownClass() {
        System.out.println("Clear clients...");
        clientsService.clients().clear();

        testEnd(ClientKickProcess.class);
    }
}
