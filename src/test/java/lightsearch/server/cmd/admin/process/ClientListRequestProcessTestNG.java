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
import lightsearch.server.data.AdminCommandResultWithClientsDTO;
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

import java.util.List;

import static test.message.TestMessage.*;

public class ClientListRequestProcessTestNG {

    private ClientsService<String, Client, List<Client>> clientsService;
    private AdminProcess<AdminCommandResult> clReqProc;

    @BeforeClass
    public void setUpClass() {
        fillClients();
        AdminCommandResultProducer admCmdResProducer = new AdminCommandResultProducerTestImpl();
        clReqProc = new ClientListRequestProcess(clientsService, admCmdResProducer);

        testBegin(ClientListRequestProcess.class);
    }

    @Test
    public void apply() throws JsonProcessingException {
        testMethod("apply()");

        AdminCommandResult cmdRes = clReqProc.apply(null);
        AdminCommandResultWithClientsDTO cmdResDTO = (AdminCommandResultWithClientsDTO) cmdRes.formForSend();

        System.out.println("Response: ");
        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(cmdResDTO));
        System.out.println("--------------------------");
    }

    private void fillClients() {
        clientsService = new ClientsServiceTestImpl();
        clientsService.addClient("111111111111111", "User1");
        clientsService.addClient("222222222222222", "User2");
        clientsService.addClient("333333333333333", "User3");
    }

    @AfterClass
    public void teardownClass() {
        System.out.println("Clear clients...");
        clientsService.remove("111111111111111");
        clientsService.remove("222222222222222");
        clientsService.remove("333333333333333");

        testEnd(ClientListRequestProcess.class);
    }
}
