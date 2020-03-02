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

public class SpringDatasourcePropertyTestNG {

    @Test
    @Parameters({"ipValue", "portValue", "dbNameValue", "dbUsernameValue", "dbPassValue"})
    public void as(String ip, int port, String dbName, String dbUsernameValue, String dbPassValue) {
        testBegin(SimplePropertyImpl.class, "as()");

        Property<String> ipProp = new IpPropertyImpl(ip);
        Property<String> portProp = new PortPropertyImpl(port);
        Property<String> dbNameProp = new DatabaseNamePropertyImpl(dbName);
        Map<String, Property<String>> urlPropMap = new HashMap<>();
        urlPropMap.put(ipProp.name(), ipProp);
        urlPropMap.put(portProp.name(), portProp);
        urlPropMap.put(dbNameProp.name(), dbNameProp);
        Property<String> propUrl = new SpringDatasourceURLFirebirdWindowsPropertyImpl(urlPropMap);

        Property<String> unameProp = new SpringDatasourceUsernamePropertyImpl(dbUsernameValue);
        Property<String> passProp = new SpringDatasourcePasswordPropertyImpl(dbPassValue);

        Map<String, Property<String>> propMap = new HashMap<>();
        propMap.put(unameProp.name(), unameProp);
        propMap.put(passProp.name(), passProp);
        propMap.put(propUrl.name(), propUrl);

        Property<String> property = new SpringDatasourcePropertyImpl(propMap);

        assertEquals(
                property.as(),
                String.format(
                        "%s\n%s\n%s\n",
                        propUrl.as(),
                        unameProp.as(),
                        passProp.as()));
        System.out.println(property.as());

        testEnd(SimplePropertyImpl.class, "as()");
    }

    @Test
    public void name() {
        testBegin(SimplePropertyImpl.class, "name()");

        Property<String> property = new SpringDatasourcePropertyImpl(null);
        assertEquals(property.name(), "spring.datasource");
        System.out.println("name: " + property.name());

        testEnd(SimplePropertyImpl.class, "name()");
    }
}
