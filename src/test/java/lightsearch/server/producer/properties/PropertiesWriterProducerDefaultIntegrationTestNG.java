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

package lightsearch.server.producer.properties;

import lightsearch.server.LightSearchServer;
import lightsearch.server.properties.PropertiesFileWriterListOfStringImpl;
import lightsearch.server.properties.PropertiesWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class PropertiesWriterProducerDefaultIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private PropertiesWriterProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(PropertiesWriterProducerDefaultImpl.class);
    }

    @Test
    @Parameters({"dir", "append"})
    public void getDatabaseRecordIdentifierDefaultInstance(String dir, boolean append) {
        testMethod("getPropertiesFileWriterListOfStringInstance()");

        PropertiesWriter<List<String>> pw = producer.getPropertiesFileWriterListOfStringInstance(dir, append);
        System.out.println("instance: " + pw);
        assertTrue(pw instanceof PropertiesFileWriterListOfStringImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(PropertiesWriterProducerDefaultImpl.class);
    }
}
