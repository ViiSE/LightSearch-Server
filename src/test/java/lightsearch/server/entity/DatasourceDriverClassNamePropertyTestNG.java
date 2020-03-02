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

public class DatasourceDriverClassNamePropertyTestNG {

    @Test
    @Parameters({"driverClassName"})
    public void as(String driverClassName) {
        testBegin(DatasourceDriverClassNamePropertyImpl.class, "as()");

        Property<String> property = new DatasourceDriverClassNamePropertyImpl(driverClassName);
        assertEquals(property.as(), driverClassName);
        System.out.println(property.as());

        testEnd(DatasourceDriverClassNamePropertyImpl.class, "as()");
    }

    @Test
    @Parameters({"driverClassName"})
    public void name(String value) {
        testBegin(DatasourceDriverClassNamePropertyImpl.class, "name()");

        Property<String> property = new DatasourceDriverClassNamePropertyImpl(value);
        assertEquals(property.name(), "datasource.driver-class-name");
        System.out.println("name: " + property.name());

        testEnd(DatasourceDriverClassNamePropertyImpl.class, "name()");
    }
}
