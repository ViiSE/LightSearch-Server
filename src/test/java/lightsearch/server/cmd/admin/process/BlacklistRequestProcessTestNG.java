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
import lightsearch.server.data.AdminCommandResultWithBlacklistDTO;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.producer.entity.AdminCommandResultProducerTestImpl;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.TestUtils;

import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.*;

public class BlacklistRequestProcessTestNG {

    private BlacklistService<String> blacklistService;
    private AdminProcess<AdminCommandResult> blReqProc;

    @BeforeClass
    public void setUpClass() {
        fillBlacklist();
        AdminCommandResultProducer admCmdResProducer = new AdminCommandResultProducerTestImpl();
        blReqProc = new BlacklistRequestProcess(blacklistService, admCmdResProducer);

        testBegin(BlacklistRequestProcess.class);
    }

    @Test
    public void apply() throws JsonProcessingException {
        testMethod("apply()");

        AdminCommandResult cmdRes = blReqProc.apply(null);
        AdminCommandResultWithBlacklistDTO cmdResDTO = (AdminCommandResultWithBlacklistDTO) cmdRes.formForSend();

        Collections.sort(blacklistService.blacklist());
        Collections.sort(cmdResDTO.getBlacklist());
        assertEquals(blacklistService.blacklist(), cmdResDTO.getBlacklist());

        System.out.println("Response: ");
        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(cmdResDTO));
        System.out.println("--------------------------");

        System.out.println("Blacklist: ");
        blacklistService.blacklist().forEach(System.out::println);
    }

    private void fillBlacklist() {
        blacklistService = new BlacklistServiceTestImpl();
        blacklistService.add("111111111111111");
        blacklistService.add("222222222222222");
        blacklistService.add("333333333333333");
    }

    @AfterClass
    public void teardownClass() {
        System.out.println("Clear blacklist...");
        blacklistService.blacklist().clear();

        testEnd(BlacklistRequestProcess.class);
    }
}
