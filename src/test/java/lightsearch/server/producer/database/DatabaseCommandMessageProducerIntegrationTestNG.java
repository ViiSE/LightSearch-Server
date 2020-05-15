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
public class DatabaseCommandMessageProducerIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private DatabaseCommandMessageProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(DatabaseCommandMessageProducerImpl.class);
    }

    @Test
    public void getDatabaseCommandMessageBindCheckWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageBindCheckWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithCheckEAN13Impl(
                        new ClientCommandSimpleImpl(
                                ClientCommands.BIND_CHECK),
                        true),
                "22505303");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageBindCheckWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageBindCheckWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageBindWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageBindWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithFactoryBarcodeImpl(
                        new ClientCommandWithUserIdentifierImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.BIND),
                        "101"),
                "22505303"),
                "22505");

        DatabaseCommandMessage commandMessage = producer.getDatabaseCommandMessageBindWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageBindWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageCancelSoftCheckWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageCancelSoftCheckWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithCardCodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandSimpleImpl(
                                ClientCommands.CANCEL_SOFT_CHECK),
                        "101"),
                "222");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageCancelSoftCheckWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageCancelSoftCheckWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageCloseSoftCheckWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageCloseSoftCheckWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithCardCodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandWithDeliveryImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.CLOSE_SOFT_CHECK),
                        "101"),
                "22505303"),
                "222");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageCloseSoftCheckWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageCloseSoftCheckWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageConfirmSoftCheckProductsWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageConfirmSoftCheckProductsWindowsJSONInstance()");

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
                .getDatabaseCommandMessageConfirmSoftCheckProductsWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageConfirmSoftCheckProductsWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageConnectionWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageConnectionWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandWithUsernameAndPasswordImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.LOGIN),
                                "user1",
                                "pass"),
                        "111");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageConnectionWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageConnectionWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageOpenSoftCheckWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageOpenSoftCheckWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithCardCodeImpl(
                new ClientCommandWithUserIdentifierImpl(
                        new ClientCommandSimpleImpl(
                                ClientCommands.OPEN_SOFT_CHECK),
                        "101"),
                "222");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageOpenSoftCheckWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageOpenSoftCheckWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageSearchWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageSearchWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithSkladImpl(
                        new ClientCommandWithTKImpl(
                                new ClientCommandSimpleImpl(
                                        ClientCommands.SEARCH),
                        "tk1"),
                "skl1"),
                "22505");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageSearchWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageSearchWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageSearchSoftCheckWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageSearchSoftCheckWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandWithBarcodeImpl(
                new ClientCommandWithUsernameImpl(
                        new ClientCommandSimpleImpl(
                                ClientCommands.SEARCH),
                        "user"),
                "22505");

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageSearchSoftCheckWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageSearchSoftCheckWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageSkladListWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageSkladListWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandSimpleImpl(ClientCommands.SKLAD_LIST);

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageSkladListWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageSkladListWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageTKListWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageTKListWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandSimpleImpl(ClientCommands.TK_LIST);

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageTKListWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageTKListWindowsJSONImpl);
    }

    @Test
    public void getDatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONInstance() {
        testMethod("getDatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONInstance()");

        ClientCommand cmd = new ClientCommandSimpleImpl(ClientCommands.UPDATE_SOFT_CHECK_PRODUCTS);

        DatabaseCommandMessage commandMessage = producer
                .getDatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONInstance(cmd);
        System.out.println("instance: " + commandMessage);
        assertTrue(commandMessage instanceof DatabaseCommandMessageUpdateSoftCheckProductsWindowsJSONImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(DatabaseCommandMessageProducerImpl.class);
    }
}
