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
import lightsearch.server.database.repository.ResponseResultRowMapperH2;
import lightsearch.server.entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class RowMapperProducerH2IntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("rowMapperProducerH2")
    private RowMapperProducer producer;

    @BeforeClass
    public void setUpClass() {
        testBegin(RowMapperProducerFirebirdImpl.class);
    }

    @Test
    public void getResponseResultRowMapperInstance() {
        testMethod("getResponseResultRowMapperInstance()");

        RowMapper<ResponseResult> mapper = producer.getResponseResultRowMapperInstance();
        System.out.println("instance: " + mapper);
        assertTrue(mapper instanceof ResponseResultRowMapperH2);
    }

    @AfterClass
    public void teardownClass() {
        testEnd(RowMapperProducerFirebirdImpl.class);
    }
}
