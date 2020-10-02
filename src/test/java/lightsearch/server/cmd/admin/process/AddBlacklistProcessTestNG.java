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
import lightsearch.server.checker.CommandCheckerAdminAddBlacklistImpl;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerDefaultImpl;
import lightsearch.server.data.AdminCommandAddBlacklistResultDTO;
import lightsearch.server.data.AdminCommandSimpleResultDTO;
import lightsearch.server.entity.*;
import lightsearch.server.initialization.BlacklistDirectory;
import lightsearch.server.initialization.BlacklistFileDirectoryImpl;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.producer.entity.AdminCommandResultProducerTestImpl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceTestImpl;
import lightsearch.server.service.ClientsService;
import lightsearch.server.service.ClientsServiceTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class AddBlacklistProcessTestNG {

    private BlacklistService<String> blacklistService;
    private ClientsService<String, Client, List<Client>> clientsService;

    private AdminProcess<AdminCommandResult> addBlProc;

    @BeforeClass
    @Parameters({"IMEI", "IMEIInClients"})
    public void setUpClass(String IMEI, String clientsIMEI) {
        blacklistService = new BlacklistServiceTestImpl();
        clientsService = new ClientsServiceTestImpl();
        clientsService.addClient(clientsIMEI, new ClientSimpleImpl(clientsIMEI, "user1"));

        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        Checker<AdminCommand> commandChecker = new CommandCheckerAdminAddBlacklistImpl(blacklistService, checker);
        BlacklistDirectory blDir = new BlacklistFileDirectoryImpl(new CurrentServerDirectoryTestImpl());
        AdminCommandResultProducer admCmdResProducer = new AdminCommandResultProducerTestImpl();

        addBlProc = new AddBlacklistProcess(
                clientsService,
                blacklistService,
                commandChecker,
                blDir,
                admCmdResProducer);

        testBegin(AddBlacklistProcess.class);
    }

    @Test(priority = 1)
    @Parameters({"IMEI"})
    public void apply(String IMEI) throws JsonProcessingException {
        testMethod("apply() [IMEI is not client]");

        AdminCommandAddBlacklistResultDTO resDTO = (AdminCommandAddBlacklistResultDTO) test(IMEI);
        assertTrue(blacklistService.contains(IMEI));
        printResult(resDTO);
    }

    @Test(priority = 2)
    @Parameters({"IMEIInClients"})
    public void apply_IMEIInClients(String IMEIInClient) throws JsonProcessingException {
        testMethod("apply() [IMEI is client]");

        AdminCommandAddBlacklistResultDTO resDTO = (AdminCommandAddBlacklistResultDTO) test(IMEIInClient);
        assertTrue(blacklistService.contains(IMEIInClient));
        assertFalse(clientsService.contains(IMEIInClient));
        printResult(resDTO);
    }

    @Test(priority = 3)
    @Parameters({"IMEI"})
    public void apply_IMEIAlreadyInTheBlacklist(String IMEI) throws JsonProcessingException {
        testMethod("apply() [IMEI already in the blacklist]");

        AdminCommandAddBlacklistResultDTO resDTO = (AdminCommandAddBlacklistResultDTO) test(IMEI);
        assertFalse(resDTO.getIsDone());
        String message = resDTO.getMessage().toLowerCase();
        assertTrue(message.contains("already in the blacklist"));
        printResult(resDTO);
    }

    private Object test(String IMEI) {
        AdminCommand admCmd = new AdminCommandAddBlacklistImpl(IMEI);
        return addBlProc.apply(admCmd).formForSend();
    }

    private void printResult(Object resDTO) throws JsonProcessingException {
        System.out.println("Result: ");
        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(resDTO));
    }

    @AfterClass
    public void teardownClass() {
        System.out.println("Clear blacklist file...");
        try (FileOutputStream fout = new FileOutputStream(new CurrentServerDirectoryTestImpl().name() + "blacklist", false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
            bw.write("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        blacklistService.blacklist().clear();

        testEnd(AddBlacklistProcess.class);
    }
}
