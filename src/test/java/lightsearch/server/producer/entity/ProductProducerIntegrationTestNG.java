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

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class ProductProducerIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProductProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(ProductProducerImpl.class);
    }

    @Test
    @Parameters({"id"})
    public void getProductSimpleInstance(String id) {
        testMethod("getProductSimpleInstance()");

        Product product = producer.getProductSimpleInstance(id);
        System.out.println("instance: " + product);
        assertTrue(product instanceof ProductSimpleImpl);
    }

    @Test
    @Parameters({"id", "amount"})
    public void getProductWithAmountInstance(String id, float amount) {
        testMethod("getProductWithAmountInstance()");

        Product product = producer.getProductWithAmountInstance(
                producer.getProductSimpleInstance(id),
                amount);
        System.out.println("instance: " + product);
        assertTrue(product instanceof ProductWithAmountImpl);
    }

    @Test
    @Parameters({"id", "name"})
    public void getProductWithNameInstance(String id, String name) {
        testMethod("getProductWithNameInstance()");

        Product product = producer.getProductWithNameInstance(
                producer.getProductSimpleInstance(id),
                name);
        System.out.println("instance: " + product);
        assertTrue(product instanceof ProductWithNameImpl);
    }

    @Test
    @Parameters({"id", "subdiv"})
    public void getProductWithSubdivisionInstance(String id, String subdiv) {
        testMethod("getProductWithSubdivisionInstance()");

        Product product = producer.getProductWithSubdivisionInstance(
                producer.getProductSimpleInstance(id),
                subdiv);
        System.out.println("instance: " + product);
        assertTrue(product instanceof ProductWithSubdivisionImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(ProductProducerImpl.class);
    }
}
