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

import lightsearch.server.constants.DatasourceConstants;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class DatasourcePropertiesFileReaderMapOfPropertiesValuesTestNG {

    private PropertiesReader<Map<String, Object>> propertiesReader;

    @BeforeClass
    public void setUpClass() {
        propertiesReader = new DatasourcePropertiesFileReaderMapOfPropertiesValuesImpl(
                new PropertiesFileReaderListOfStringImpl(
                        new ApplicationPropertiesDirectoryImpl(
                                new CurrentServerDirectoryTestImpl()))
        );
    }

    @Test
    public void read() {
        testBegin(DatasourcePropertiesFileReaderMapOfPropertiesImpl.class, "read()");

        try {
            Map<String, Object> propertyMap = propertiesReader.read();
            System.out.println("Properties:");
            System.out.println("----------------------------");
            propertyMap.forEach((name, prop) -> {
                System.out.println("name: " + name);
                System.out.println("prop: ");
                System.out.println("\tvalue: " + prop);
                System.out.println("----------------------------");
            });

            assertTrue(propertyMap.containsKey(DatasourceConstants.DRIVER_CLASS_NAME));
            assertEquals(propertyMap.get(DatasourceConstants.DRIVER_CLASS_NAME), "org.firebirdsql.jdbc.FBDriver");

            assertTrue(propertyMap.containsKey(DatasourceConstants.SCRIPT_ENCODING));
            assertEquals(propertyMap.get(DatasourceConstants.SCRIPT_ENCODING), "UTF-8");

            assertTrue(propertyMap.containsKey(DatasourceConstants.USERNAME));
            assertEquals(propertyMap.get(DatasourceConstants.USERNAME), "defaultUser");

            assertTrue(propertyMap.containsKey(DatasourceConstants.PASSWORD));
            assertEquals(propertyMap.get(DatasourceConstants.PASSWORD), "defaultPass");

            assertTrue(propertyMap.containsKey(DatasourceConstants.DB_TYPE));
            assertEquals(propertyMap.get(DatasourceConstants.DB_TYPE), "firebirdsql");

            assertTrue(propertyMap.containsKey(DatasourceConstants.ADDITIONAL));
            assertEquals(propertyMap.get(DatasourceConstants.ADDITIONAL), "encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251");

            assertTrue(propertyMap.containsKey(DatasourceConstants.DB_NAME));
            assertEquals(propertyMap.get(DatasourceConstants.DB_NAME), "/new/super/base");

            assertTrue(propertyMap.containsKey(DatasourceConstants.IP));
            assertEquals(propertyMap.get(DatasourceConstants.IP), "127.0.0.1");

            assertTrue(propertyMap.containsKey(DatasourceConstants.PORT));
            assertEquals(propertyMap.get(DatasourceConstants.PORT), 5050);
        } catch (ReaderException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }

        testEnd(DatasourcePropertiesFileReaderMapOfPropertiesImpl.class, "read()");
    }
}
