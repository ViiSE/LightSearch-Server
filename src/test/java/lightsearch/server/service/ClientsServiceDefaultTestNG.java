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

package lightsearch.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lightsearch.server.entity.Client;
import lightsearch.server.entity.ClientSimpleImpl;
import lightsearch.server.producer.entity.ClientProducerTestImpl;
import lightsearch.server.producer.entity.JWTTokenHeaderProducerTestImpl;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.security.JWTFromIMEIImpl;
import lightsearch.server.time.JWTExpirationDateImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestUtils;

import static org.testng.Assert.*;
import static test.message.TestMessage.*;

public class ClientsServiceDefaultTestNG {

    private ClientsService<String, Client> clientsService;

    @BeforeClass
    @Parameters({"secret", "jwtValidDayCount"})
    public void setUpClass(String secret, long jwtValidDayCount) {
        HashAlgorithm hashAlgorithm = new HashAlgorithmSHA512Impl();
        clientsService = new ClientsServiceDefaultImpl(
                hashAlgorithm,
                TestUtils.objectMapperWithJavaTimeModule(),
                new ClientProducerTestImpl(),
                new JWTFromIMEIImpl(
                        secret,
                        hashAlgorithm,
                        new JWTExpirationDateImpl(jwtValidDayCount)),
                new JWTTokenHeaderProducerTestImpl());

        testBegin(BlacklistServiceDefaultImpl.class);
    }

    @Test(priority = 1)
    @Parameters({"IMEI", "username"})
    public void addClient_withIMEIandUsername(String IMEI, String username) {
        testMethod("addClient() [with IMEI and username]");

        String jwtToken = clientsService.addClient(IMEI, username).toString();

        System.out.println("Token: " + jwtToken);

        assertNotNull(jwtToken);
        assertFalse(jwtToken.isEmpty());

        assertTrue(clientsService.clients().size() > 0);
    }

    @Test(priority = 2)
    @Parameters({"hashIMEI", "username"})
    public void addClient_withHashAndUsername(String hashIMEI, String username) {
        testMethod("addClient() [with hashIMEI and Client]");

        Client client = new ClientSimpleImpl(hashIMEI, username);
        String jwtToken = clientsService.addClient(hashIMEI, client).toString();

        System.out.println("Token: " + jwtToken);

        assertNotNull(jwtToken);
        assertFalse(jwtToken.isEmpty());

        assertTrue(clientsService.clients().size() > 0);
    }

    @Test(priority = 3)
    @Parameters({"hashIMEI"})
    public void remove_hashKey(String hashIMEI) throws JsonProcessingException {
        testMethod("remove() [hashIMEI]");

        Client client = clientsService.remove(hashIMEI);
        assertFalse(clientsService.contains(hashIMEI));

        System.out.println("hashIMEI: " + hashIMEI);
        System.out.println("Client has been removed: " +
                TestUtils.objectMapperWithJavaTimeModule()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(client.formForSend()));
    }

    @Test(priority = 4)
    @Parameters({"IMEI"})
    public void remove_IMEI(String IMEI) throws JsonProcessingException {
        testMethod("remove() [IMEI]");

        Client client = clientsService.remove(IMEI);
        assertFalse(clientsService.contains(IMEI));

        System.out.println("Client has been removed: " +
                TestUtils.objectMapperWithJavaTimeModule()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(client.formForSend()));
    }

    @Test(priority = 5)
    @Parameters({"hashIMEI", "username"})
    public void contains_hashIMEI(String hashIMEI, String username) {
        testMethod("contains() [hashIMEI]");

        clientsService.addClient(hashIMEI, username);
        assertTrue(clientsService.contains(hashIMEI));
        clientsService.remove(hashIMEI);
    }

    @Test(priority = 6)
    @Parameters({"IMEI", "username"})
    public void contains(String IMEI, String username) {
        testMethod("contains() [IMEI]");

        clientsService.addClient(IMEI, username);
        assertTrue(clientsService.contains(IMEI));
        clientsService.remove(IMEI);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(BlacklistServiceDefaultImpl.class);
    }
}
