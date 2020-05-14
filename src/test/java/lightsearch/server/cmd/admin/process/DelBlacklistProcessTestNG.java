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
import lightsearch.server.checker.CommandCheckerAdminDelBlacklistImpl;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerDefaultImpl;
import lightsearch.server.data.AdminCommandSimpleResultDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDelBlacklistImpl;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.initialization.BlacklistDirectory;
import lightsearch.server.initialization.BlacklistFileDirectoryImpl;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.producer.entity.AdminCommandResultProducerTestImpl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class DelBlacklistProcessTestNG {

    private BlacklistService<String> blacklistService;

    private AdminProcess<AdminCommandResult> delBlProc;

    @BeforeClass
    @Parameters({"IMEI"})
    public void setUpClass(String IMEI) {
        blacklistService = new BlacklistServiceTestImpl();
        blacklistService.add(IMEI);

        LightSearchChecker checker = new LightSearchCheckerDefaultImpl();
        Checker<AdminCommand> commandChecker = new CommandCheckerAdminDelBlacklistImpl(blacklistService, checker);
        BlacklistDirectory blDir = new BlacklistFileDirectoryImpl(new CurrentServerDirectoryTestImpl());
        AdminCommandResultProducer admCmdResProducer = new AdminCommandResultProducerTestImpl();

        delBlProc = new DelBlacklistProcess(
                blDir,
                blacklistService,
                commandChecker,
                admCmdResProducer);

        testBegin(DelBlacklistProcess.class);
    }

    @Test(priority = 1)
    @Parameters({"IMEI"})
    public void apply(String IMEI) throws JsonProcessingException {
        testMethod("apply() [IMEI in blacklist]");

        AdminCommandSimpleResultDTO resDTO = (AdminCommandSimpleResultDTO) test(IMEI);
        assertFalse(blacklistService.contains(IMEI));
        printResult(resDTO);
    }

    @Test(priority = 2)
    @Parameters({"IMEI"})
    public void apply_IMEINotInTheBlacklist(String IMEI) throws JsonProcessingException {
        testMethod("apply() [IMEI not in the blacklist]");

        AdminCommandSimpleResultDTO resDTO = (AdminCommandSimpleResultDTO) test(IMEI);
        String message = resDTO.getMessage().toLowerCase();
        assertTrue(message.contains("not in the blacklist"));
        printResult(resDTO);
    }

    private Object test(String IMEI) {
        AdminCommand admCmd = new AdminCommandDelBlacklistImpl(IMEI);
        AdminCommandResult admCmdRes = delBlProc.apply(admCmd);
        return admCmdRes.formForSend();
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

        testEnd(DelBlacklistProcess.class);
    }
}
