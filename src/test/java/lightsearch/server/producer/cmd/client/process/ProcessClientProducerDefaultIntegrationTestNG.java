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

package lightsearch.server.producer.cmd.client.process;

import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.client.process.*;
import lightsearch.server.entity.ClientCommandResult;
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
public class ProcessClientProducerDefaultIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProcessClientProducer<ClientCommandResult> producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(ProcessClientProducerDefaultImpl.class);
    }

    @Test
    public void getBindCheckProcessInstance() {
        testMethod("getBindCheckProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getBindCheckProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof BindCheckProcess);
    }

    @Test
    public void getBindProcessInstance() {
        testMethod("getBindProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getBindProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof BindProcess);
    }

    @Test
    public void getCancelSoftCheckProcessInstance() {
        testMethod("getCancelSoftCheckProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getCancelSoftCheckProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof CancelSoftCheckProcess);
    }

    @Test
    public void getCloseSoftCheckProcessInstance() {
        testMethod("getCloseSoftCheckProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getCloseSoftCheckProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof CloseSoftCheckProcessWithLoggerImpl);
    }

    @Test
    public void getConfirmSoftCheckProductsProcessInstance() {
        testMethod("getConfirmSoftCheckProductsProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getConfirmSoftCheckProductsProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof ConfirmSoftCheckProductsProcess);
    }

    @Test
    public void getLoginProcessInstance() {
        testMethod("getLoginProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getLoginProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof LoginProcessWithLogger);
    }

    @Test
    public void getOpenSoftCheckProcessInstance() {
        testMethod("getOpenSoftCheckProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getOpenSoftCheckProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof OpenSoftCheckProcessWithLoggerImpl);
    }

    @Test
    public void getSearchProcessInstance() {
        testMethod("getSearchProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getSearchProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof SearchProcess);
    }

    @Test
    public void getUnbindProcessInstance() {
        testMethod("getUnbindProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getUnbindProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof UnbindProcess);
    }

    @Test
    public void getUnbindCheckProcessInstance() {
        testMethod("getUnbindCheckProcessInstance()");

        ClientProcess<ClientCommandResult> process = producer.getUnbindCheckProcessInstance();
        System.out.println("instance: " + process);
        assertTrue(process instanceof UnbindCheckProcess);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(ProcessClientProducerDefaultImpl.class);
    }
}
