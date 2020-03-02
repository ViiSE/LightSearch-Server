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

package lightsearch.server.properties;

import lightsearch.server.data.AdminCommandDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDatabaseImpl;
import lightsearch.server.entity.Property;
import lightsearch.server.producer.entity.PropertyProducer;
import lightsearch.server.producer.entity.PropertyProducerTestImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class SpringDatasourcePropertyCreatorTestNG {

    private AdminCommand admCmd;
    private PropertyProducer propProducer;

    @BeforeClass
    @Parameters({"dbName", "dbUsername", "dbPass", "dbIp", "dbPort"})
    public void setUpClass(String dbName, String dbUsername, String dbPass, String ip, int port) {
        AdminCommandDTO adminCommandDTO = new AdminCommandDTO();
        adminCommandDTO.setDbName(dbName);
        adminCommandDTO.setUsername(dbUsername);
        adminCommandDTO.setPassword(dbPass);
        adminCommandDTO.setIp(ip);
        adminCommandDTO.setPort(port);

        admCmd = new AdminCommandDatabaseImpl(adminCommandDTO);
        propProducer = new PropertyProducerTestImpl();
    }

    @Test
    public void create() {
        testBegin(SpringDatasourcePropertyCreatorImpl.class, "create()");

        PropertyCreator<String, AdminCommand> propertyCreator = new SpringDatasourcePropertyCreatorImpl(propProducer);
        Property<String> property = propertyCreator.create(admCmd);

        System.out.println(property.as());

        assertTrue(property.as().contains("spring.datasource.url"));
        assertTrue(property.as().contains("spring.datasource.username"));
        assertTrue(property.as().contains("spring.datasource.password"));

        testEnd(SpringDatasourcePropertyCreatorImpl.class, "create()");
    }
}
