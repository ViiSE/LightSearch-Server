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
import lightsearch.server.data.AdminChangeDatabaseCommandDTO;
import lightsearch.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class AdminCommandProducerIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private AdminCommandProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(AdminCommandProducerImpl.class);
    }

    @Test
    @Parameters({"IMEI"})
    public void getAdminCommandAddBlacklistInstance(String IMEI) {
        testMethod("getAdminCommandAddBlacklistInstance()");

        AdminCommand command = producer.getAdminCommandAddBlacklistInstance(IMEI);
        System.out.println("instance: " + command);
        assertTrue(command instanceof AdminCommandAddBlacklistImpl);
    }

    @Test
    @Parameters({"IMEI"})
    public void getAdminCommandDelBlacklistInstance(String IMEI) {
        testMethod("getAdminCommandDelBlacklistInstance()");

        AdminCommand command = producer.getAdminCommandDelBlacklistInstance(IMEI);
        System.out.println("instance: " + command);
        assertTrue(command instanceof AdminCommandDelBlacklistImpl);
    }

    @Test
    @Parameters({"IMEI"})
    public void getAdminCommandClientKickInstance(String IMEI) {
        testMethod("getAdminCommandClientKickInstance()");

        AdminCommand command = producer.getAdminCommandClientKickInstance(IMEI);
        System.out.println("instance: " + command);
        assertTrue(command instanceof AdminCommandKickImpl);
    }

    @Test
    @Parameters({"tout"})
    public void getAdminCommandClientKickInstance(int tout) {
        testMethod("getAdminCommandClientTimeoutInstance()");

        AdminCommand command = producer.getAdminCommandClientTimeoutInstance(tout);
        System.out.println("instance: " + command);
        assertTrue(command instanceof AdminCommandClientTimeoutImpl);
    }

    @Test
    @Parameters({"ip", "port", "dbName", "username", "password"})
    public void getAdminCommandDatabaseInstance(String ip, int port, String dbName, String username, String pass) {
        testMethod("getAdminCommandDatabaseInstance()");

        AdminChangeDatabaseCommandDTO adminCommandDTO = new AdminChangeDatabaseCommandDTO();
        adminCommandDTO.setHost(ip);
        adminCommandDTO.setPort(port);
        adminCommandDTO.setDbName(dbName);
        adminCommandDTO.setUsername(username);
        adminCommandDTO.setPassword(pass);

        AdminCommand command = producer.getAdminCommandDatabaseInstance(adminCommandDTO);
        System.out.println("instance: " + command);
        assertTrue(command instanceof AdminCommandDatabaseImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(AdminCommandProducerImpl.class);
    }
}
