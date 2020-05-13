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

package lightsearch.server.producer.security;

import lightsearch.server.LightSearchServer;
import lightsearch.server.security.DecryptedInformationRSAImpl;
import lightsearch.server.security.EncryptedInformationImpl;
import lightsearch.server.security.Information;
import lightsearch.server.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class InformationProducerIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private InformationProducer producer;

    @Autowired
    private Keys<PublicKey, PrivateKey> keys;

    @BeforeClass
    public void setUpClass() {
        testBegin(InformationProducerImpl.class);
    }

    @Test
    @Parameters({"rawData"})
    public void getEncryptedInformationInstance(String rawData) {
        testMethod("getEncryptedInformationInstance()");

        Information<String> encData = producer.getEncryptedInformationInstance(rawData);
        System.out.println("instance: " + encData);
        assertTrue(encData instanceof EncryptedInformationImpl);
    }

    @Test
    @Parameters({"rawData"})
    public void getDecryptedInformationRSAInstance(String rawData) {
        testMethod("getDecryptedInformationRSAInstance()");

        Information<String> decData = producer.getDecryptedInformationRSAInstance(
                producer.getEncryptedInformationInstance(rawData),
                keys);
        System.out.println("instance: " + decData);
        assertTrue(decData instanceof DecryptedInformationRSAImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(InformationProducerImpl.class);
    }
}
