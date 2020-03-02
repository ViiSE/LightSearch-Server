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

package lightsearch.server.producer.entity;

import lightsearch.server.LightSearchServer;
import lightsearch.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class ClientCommandProducerDefaultIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ClientCommandProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(ClientCommandProducerDefaultImpl.class);
    }

    @Test
    @Parameters({"cmdName"})
    public void getClientCommandSimpleInstance(String cmdName) {
        testMethod("getClientCommandSimpleInstance()");

        ClientCommand cmdSimple = producer.getClientCommandSimpleInstance(cmdName);
        System.out.println("instance: " + cmdSimple);
        assertTrue(cmdSimple instanceof ClientCommandSimpleImpl);
    }

    @Test
    @Parameters({"cmdName", "userIdent"})
    public void getClientCommandWithUserIdentifierInstance(String cmdName, String userIdent) {
        testMethod("getClientCommandWithUserIdentifierInstance()");

        ClientCommand command = producer.getClientCommandWithUserIdentifierInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                userIdent);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithUserIdentifierImpl);
    }

    @Test
    @Parameters({"cmdName", "checkEan13"})
    public void getClientCommandWithCheckEAN13Instance(String cmdName, boolean checkEAN13) {
        testMethod("getClientCommandWithCheckEAN13Instance()");

        ClientCommand command = producer.getClientCommandWithCheckEAN13Instance(
                producer.getClientCommandSimpleInstance(cmdName),
                checkEAN13);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithCheckEAN13Impl);
    }

    @Test
    @Parameters({"cmdName", "barcode"})
    public void getClientCommandWithBarcodeInstance(String cmdName, String barcode) {
        testMethod("getClientCommandWithBarcodeInstance()");

        ClientCommand command = producer.getClientCommandWithBarcodeInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                barcode);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithBarcodeImpl);
    }

    @Test
    @Parameters({"cmdName", "IMEI"})
    public void getClientCommandWithIMEIInstance(String cmdName, String IMEI) {
        testMethod("getClientCommandWithIMEIInstance()");

        ClientCommand command = producer.getClientCommandWithIMEIInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                IMEI);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithIMEIImpl);
    }

    @Test
    @Parameters({"cmdName", "delivery"})
    public void getClientCommandWithDeliveryInstance(String cmdName, String delivery) {
        testMethod("getClientCommandWithDeliveryInstance()");

        ClientCommand command = producer.getClientCommandWithDeliveryInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                delivery);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithDeliveryImpl);
    }

    @Test
    @Parameters({"cmdName", "cardCode"})
    public void getClientCommandWithCardCodeInstance(String cmdName, String cardCode) {
        testMethod("getClientCommandWithCardCodeInstance()");

        ClientCommand command = producer.getClientCommandWithCardCodeInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                cardCode);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithCardCodeImpl);
    }

    @Test
    @Parameters({"cmdName"})
    public void getClientCommandWithCardCodeInstance(String cmdName) {
        testMethod("getClientCommandWithProductsInstance()");

        ClientCommand command = producer.getClientCommandWithProductsInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                new ArrayList<>());
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithProductsImpl);
    }

    @Test
    @Parameters({"cmdName", "TK"})
    public void getClientCommandWithTKInstance(String cmdName, String TK) {
        testMethod("getClientCommandWithProductsInstance()");

        ClientCommand command = producer.getClientCommandWithTKInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                TK);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithTKImpl);
    }

    @Test
    @Parameters({"cmdName", "sklad"})
    public void getClientCommandWithSkladInstance(String cmdName, String sklad) {
        testMethod("getClientCommandWithSkladInstance()");

        ClientCommand command = producer.getClientCommandWithSkladInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                sklad);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithSkladImpl);
    }

    @Test
    @Parameters({"cmdName", "os"})
    public void getClientCommandWithOsInstance(String cmdName, String os) {
        testMethod("getClientCommandWithOsInstance()");

        ClientCommand command = producer.getClientCommandWithOsInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                os);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithOsImpl);
    }

    @Test
    @Parameters({"cmdName", "model"})
    public void getClientCommandWithModelInstance(String cmdName, String model) {
        testMethod("getClientCommandWithModelInstance()");

        ClientCommand command = producer.getClientCommandWithModelInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                model);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithModelImpl);
    }

    @Test
    @Parameters({"cmdName", "ip"})
    public void getClientCommandWithIpInstance(String cmdName, String ip) {
        testMethod("getClientCommandWithIpInstance()");

        ClientCommand command = producer.getClientCommandWithIpInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                ip);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithIpImpl);
    }

    @Test
    @Parameters({"cmdName", "factoryBarcode"})
    public void getClientCommandWithFactoryBarcodeInstance(String cmdName, String factoryBarcode) {
        testMethod("getClientCommandWithFactoryBarcodeInstance()");

        ClientCommand command = producer.getClientCommandWithFactoryBarcodeInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                factoryBarcode);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithFactoryBarcodeImpl);
    }

    @Test
    @Parameters({"cmdName", "username", "password"})
    public void getClientCommandWithUsernameAndPasswordInstance(String cmdName, String username, String password) {
        testMethod("getClientCommandWithUsernameAndPasswordInstance()");

        ClientCommand command = producer.getClientCommandWithUsernameAndPasswordInstance(
                producer.getClientCommandSimpleInstance(cmdName),
                username,
                password);
        System.out.println("instance: " + command);
        assertTrue(command instanceof ClientCommandWithUsernameAndPasswordImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(ClientCommandProducerDefaultImpl.class);
    }
}
