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

package lightsearch.server.producer.time;

import lightsearch.server.LightSearchServer;
import lightsearch.server.time.DateTimeComparator;
import lightsearch.server.time.DateTimeComparatorImpl;
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
public class PropertiesTimeProducerIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private DateTimeComparatorProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(DateTimeComparatorProducerImpl.class);
    }

    @Test
    @Parameters({"pattern"})
    public void getDatabaseRecordIdentifierDefaultInstance(String pattern) {
        testMethod("getDateTimeComparatorDefaultInstance()");

        DateTimeComparator dtc = producer.getDateTimeComparatorDefaultInstance(pattern);
        System.out.println("instance: " + dtc);
        assertTrue(dtc instanceof DateTimeComparatorImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(DateTimeComparatorProducerImpl.class);
    }
}
