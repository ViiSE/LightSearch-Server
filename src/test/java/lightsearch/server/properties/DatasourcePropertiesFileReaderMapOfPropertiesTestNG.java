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

import lightsearch.server.entity.Property;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.producer.entity.PropertyProducerTestImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class DatasourcePropertiesFileReaderMapOfPropertiesTestNG {

    private PropertiesReader<Map<String, Property<String>>> propertiesReader;

    @BeforeClass
    public void setUpClass() {
        propertiesReader = new DatasourcePropertiesFileReaderMapOfPropertiesImpl(
                new PropertyProducerTestImpl(),
                new ApplicationPropertiesDirectoryImpl(
                        new CurrentServerDirectoryTestImpl()));
    }

    @Test
    public void read() {
        testBegin(DatasourcePropertiesFileReaderMapOfPropertiesImpl.class, "read()");

        try {
            Map<String, Property<String>> propertyMap = propertiesReader.read();
            System.out.println("Properties:");
            System.out.println("----------------------------");
            propertyMap.forEach((name, prop) -> {
                System.out.println("name: " + name);
                System.out.println("prop: ");
                System.out.println("\tname: " + prop.name());
                System.out.println("\tvalue: " + prop.as());
                System.out.println("----------------------------");
            });

            assertTrue(propertyMap.containsKey("datasource.url"));
            assertTrue(propertyMap.containsKey("datasource.driver-class-name"));

            Property<String> urlProp = propertyMap.get("datasource.url");
            assertEquals(urlProp.name(), "datasource.url");

            Property<String> driverClassNameProp = propertyMap.get("datasource.driver-class-name");
            assertEquals(driverClassNameProp.name(), "datasource.driver-class-name");
        } catch (ReaderException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }

        testEnd(DatasourcePropertiesFileReaderMapOfPropertiesImpl.class, "read()");
    }
}
