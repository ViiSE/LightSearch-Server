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

import java.util.HashMap;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class PropertyProducerIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private PropertyProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(PropertyProducerImpl.class);
    }

    @Test
    @Parameters({"tout"})
    public void getClientTimeoutPropertyInstance(int tout) {
        testMethod("getClientTimeoutPropertyInstance()");

        Property<String> prop = producer.getClientTimeoutPropertyInstance(tout);
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof ClientTimeoutPropertyImpl);
    }

    @Test
    @Parameters({"dbName"})
    public void getDatabaseNamePropertyInstance(String dbName) {
        testMethod("getDatabaseNamePropertyInstance()");

        Property<String> prop = producer.getDatabaseNamePropertyInstance(dbName);
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof DatabaseNamePropertyImpl);
    }

    @Test
    @Parameters({"ip"})
    public void getIpPropertyInstance(String ip) {
        testMethod("getIpPropertyInstance()");

        Property<String> prop = producer.getIpPropertyInstance(ip);
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof IpPropertyImpl);
    }

    @Test
    @Parameters({"port"})
    public void getIpPropertyInstance(int port) {
        testMethod("getPortPropertyInstance()");

        Property<String> prop = producer.getPortPropertyInstance(port);
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof PortPropertyImpl);
    }

    @Test
    @Parameters({"password"})
    public void getSpringDatasourcePasswordPropertyInstance(String pass) {
        testMethod("getSpringDatasourcePasswordPropertyInstance()");

        Property<String> prop = producer.getSpringDatasourcePasswordPropertyInstance(pass);
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof SpringDatasourcePasswordPropertyImpl);
    }

    @Test
    public void getSpringDatasourcePropertyInstance() {
        testMethod("getSpringDatasourcePropertyInstance()");

        Property<String> prop = producer.getSpringDatasourcePropertyInstance(new HashMap<>());
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof SpringDatasourcePropertyImpl);
    }

    @Test
    public void getSpringDatasourceURLFirebirdWindowsPropertyInstance() {
        testMethod("getSpringDatasourceURLFirebirdWindowsPropertyInstance()");

        Property<String> prop = producer.getSpringDatasourceURLFirebirdWindowsPropertyInstance(new HashMap<>());
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof SpringDatasourceURLFirebirdWindowsPropertyImpl);
    }

    @Test
    @Parameters({"username"})
    public void getSpringDatasourceUsernamePropertyInstance(String username) {
        testMethod("getSpringDatasourceUsernamePropertyInstance()");

        Property<String> prop = producer.getSpringDatasourceUsernamePropertyInstance(username);
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof SpringDatasourceUsernamePropertyImpl);
    }

    @Test
    @Parameters({"username", "dbName"})
    public void getSimplePropertyInstance(String name, String value) {
        testMethod("getSimplePropertyInstance()");

        Property<String> prop = producer.getSimplePropertyInstance(name, value);
        System.out.println("instance: " + prop);
        assertTrue(prop instanceof SimplePropertyImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(PropertyProducerImpl.class);
    }
}
