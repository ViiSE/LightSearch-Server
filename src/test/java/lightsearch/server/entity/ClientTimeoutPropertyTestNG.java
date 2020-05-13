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

public class ClientTimeoutPropertyTestNG {

    @Test
    @Parameters({"toutValue"})
    public void as(int value) {
        testBegin(SimplePropertyImpl.class, "as()");

        Property<String> property = new ClientTimeoutPropertyImpl(value);
        assertEquals(property.as(), property.name() + "=" + value);
        System.out.println(property.as());

        testEnd(SimplePropertyImpl.class, "as()");
    }

    @Test
    @Parameters({"toutValue"})
    public void name(int value) {
        testBegin(SimplePropertyImpl.class, "name()");

        Property<String> property = new ClientTimeoutPropertyImpl(value);
        assertEquals(property.name(), "lightsearch.server.jwt-valid-day-count");
        System.out.println("name: " + property.name());

        testEnd(SimplePropertyImpl.class, "name()");
    }
}
