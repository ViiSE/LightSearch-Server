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

package lightsearch.server.producer.cmd.admin.process;

import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.admin.process.*;
import lightsearch.server.entity.AdminCommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class ProcessAdminProducerDefaultIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProcessAdminProducer<AdminCommandResult> producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(ProcessAdminProducerDefaultImpl.class);
    }

    @Test
    public void getAddBlacklistProcessInstance() {
        testMethod("getAddBlacklistProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getAddBlacklistProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof AddBlacklistProcess);
    }

    @Test
    public void getDelBlacklistProcessInstance() {
        testMethod("getDelBlacklistProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getDelBlacklistProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof DelBlacklistProcess);
    }

    @Test
    public void getBlacklistRequestProcessInstance() {
        testMethod("getBlacklistRequestProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getBlacklistRequestProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof BlacklistRequestProcessWithLoggerImpl);
    }

    @Test
    public void getChangeDatabaseProcessInstance() {
        testMethod("getChangeDatabaseProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getChangeDatabaseProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof ChangeDatabaseProcess);
    }

    @Test
    public void getClientKickProcessInstance() {
        testMethod("getClientKickProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getClientKickProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof ClientKickProcess);
    }

    @Test
    public void getClientListRequestProcessInstance() {
        testMethod("getClientListRequestProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getClientListRequestProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof ClientListRequestProcessWithLoggerImpl);
    }

    @Test
    public void getClientTimeoutProcessInstance() {
        testMethod("getClientTimeoutProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getClientTimeoutProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof ClientTimeoutProcess);
    }

    @Test
    public void getRestartProcessInstance() {
        testMethod("getRestartProcessInstance()");

        AdminProcess<AdminCommandResult> process = producer.getRestartProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof RestartProcessWithLoggerImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(ProcessAdminProducerDefaultImpl.class);
    }
}
