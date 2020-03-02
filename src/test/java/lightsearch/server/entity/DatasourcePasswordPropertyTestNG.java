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

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class DatasourcePasswordPropertyTestNG {

    @Test
    @Parameters({"dbPassValue"})
    public void as(String pass) {
        testBegin(DatasourcePasswordPropertyImpl.class, "as()");

        Property<String> property = new DatasourcePasswordPropertyImpl(pass);
        assertEquals(property.as(), pass);
        System.out.println(property.as());

        testEnd(DatasourcePasswordPropertyImpl.class, "as()");
    }

    @Test
    @Parameters({"dbPassValue"})
    public void name(String value) {
        testBegin(DatasourcePasswordPropertyImpl.class, "name()");

        Property<String> property = new DatasourcePasswordPropertyImpl(value);
        assertEquals(property.name(), "datasource.password");
        System.out.println("name: " + property.name());

        testEnd(DatasourcePasswordPropertyImpl.class, "name()");
    }
}
