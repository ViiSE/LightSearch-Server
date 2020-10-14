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

package lightsearch.server.entity;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class SpringDatasourceURLFirebirdWindowsPropertyTestNG {

    @Test
    @Parameters({"ipValue", "portValue", "dbNameValue"})
    public void as(String ip, int port, String dbName) {
        testBegin(SimplePropertyImpl.class, "as()");

        Property<String> ipProp = new IpPropertyImpl(ip);
        Property<String> portProp = new PortPropertyImpl(port);
        Property<String> dbNameProp = new DatabaseNamePropertyImpl(dbName);
        // TODO: 12.10.2020 CLEANUP THIS
        Property<String> dbTypeProp = new DbTypePropertyImpl("firebirdsql");
        Property<String> additionalProp = new DbAdditionalPropertyImpl("encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251");

        Map<String, Property<String>> propMap = new HashMap<>();
        propMap.put(ipProp.name(), ipProp);
        propMap.put(portProp.name(), portProp);
        propMap.put(dbNameProp.name(), dbNameProp);
        propMap.put(dbTypeProp.name(), dbTypeProp);
        propMap.put(additionalProp.name(), additionalProp);

        Property<String> property = new SpringDatasourceURLPropertyImpl(propMap);
        assertEquals(
                property.as(),
                String.format(
                        "%s=%s%s:%s%s%s",
                        property.name(),
                        "jdbc:firebirdsql://", ip, port, dbName,
                        "?encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251"));
        System.out.println(property.as());

        testEnd(SimplePropertyImpl.class, "as()");
    }

    @Test
    public void name() {
        testBegin(SimplePropertyImpl.class, "name()");

        Property<String> property = new SpringDatasourceURLPropertyImpl(null);
        assertEquals(property.name(), "spring.datasource.url");
        System.out.println("name: " + property.name());

        testEnd(SimplePropertyImpl.class, "name()");
    }
}
