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

package lightsearch.server.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.LightSearchServer;
import lightsearch.server.data.ResponseResultDTO;
import lightsearch.server.database.repository.LSResponseRepository;
import lightsearch.server.entity.ResponseResult;
import lightsearch.server.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static test.message.TestMessage.catchMessage;

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/application.properties")
public class LSResponseRepositoryH2IntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    @Qualifier("lsResponseRepositoryH2")
    private LSResponseRepository<String> responseRepository;

    @Test(dataProvider = "commandsDP")
    public void exec(long lsCode) {
        try {
            ResponseResult responseResult = responseRepository.read(String.valueOf(lsCode));
            ResponseResultDTO responseResultDTO = (ResponseResultDTO) responseResult.formForSend();
            System.out.println(mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(responseResultDTO.getCmdRes().formForSend()));
        } catch (JsonProcessingException | RepositoryException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @DataProvider(name = "commandsDP")
    public Object[][] createCommandsDP() {
        return new Object[][] {
                {1},
                {2},
                {3},
                {4},
                {5},
                {6}
        };
    }
}
