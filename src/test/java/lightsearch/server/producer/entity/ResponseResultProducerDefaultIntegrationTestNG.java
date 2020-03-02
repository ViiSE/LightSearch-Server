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
import lightsearch.server.entity.ResponseResult;
import lightsearch.server.entity.ResponseResultSimpleImpl;
import lightsearch.server.entity.ResponseResultWithCommandResultImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class ResponseResultProducerDefaultIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ResponseResultProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(ResponseResultProducerDefaultImpl.class);
    }

    @Test
    public void getResponseResultSimpleInstance() {
        testMethod("getResponseResultSimpleInstance()");

        ResponseResult res = producer.getResponseResultSimpleInstance(LocalDateTime.now());
        System.out.println("instance: " + res);
        assertTrue(res instanceof ResponseResultSimpleImpl);
    }

    @Test
    public void getResponseResultWithCommandResultInstance() {
        testMethod("getResponseResultWithCommandResultInstance()");

        ResponseResult res = producer.getResponseResultWithCommandResultInstance(
                producer.getResponseResultSimpleInstance(LocalDateTime.now()),
                null);
        System.out.println("instance: " + res);
        assertTrue(res instanceof ResponseResultWithCommandResultImpl);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(ResponseResultProducerDefaultImpl.class);
    }
}
