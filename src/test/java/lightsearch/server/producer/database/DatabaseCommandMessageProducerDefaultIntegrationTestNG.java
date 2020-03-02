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

package lightsearch.server.producer.database;

import lightsearch.server.LightSearchServer;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.database.cmd.message.*;
import lightsearch.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class DatabaseCommandMessageProducerDefaultIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private DatabaseCommandMessageProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(DatabaseCommandMessageProducerDefaultImpl.class);
    }

    @Test
    public void getDatabaseCommandMessageBindCheckDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageBindCheckDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithCheckEAN13Impl(
                        new ClientCommandSimpleImpl(
                                ClientCommands.BIND_CHECK),
                        true),
                "22505303");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageBindCheckDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageBindCheckDefaultWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageBindDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageBindDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithFactoryBarcodeImpl(
                        new ClientCommandWithUserIdentifierImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.BIND),
                        "101"),
                "22505303"),
                "22505");

        DatabaseCommandMessage commandMessage = producer.getDatabaseCommandMessageBindDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageBindDefaultWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithCardCodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandSimpleImpl(
                                ClientCommands.CANCEL_SOFT_CHECK),
                        "101"),
                "222");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithCardCodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandWithDeliveryImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.CLOSE_SOFT_CHECK),
                        "101"),
                "22505303"),
                "222");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithCardCodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandWithProductsImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.CONFIRM_SOFT_CHECK_PRODUCTS),
                                new ArrayList<>() {{
                                    add(new ProductWithAmountImpl(new ProductSimpleImpl("id1"), 10.5f));
                                }}),
                        "22505303"),
                "222");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandWithUsernameAndPasswordImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.LOGIN),
                                "user1",
                                "pass"),
                        "111");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageConnectionDefaultWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithCardCodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandSimpleImpl(
                                ClientCommands.OPEN_SOFT_CHECK),
                        "101"),
                "222");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageSearchDefaultWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageSearchDefaultWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithSkladImpl(
                        new ClientCommandWithTKImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.SEARCH),
                        "tk1"),
                "skl1"),
                "22505");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageSearchDefaultWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageSearchDefaultWindowsJSONImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(DatabaseCommandMessageProducerDefaultImpl.class);
    }
}
