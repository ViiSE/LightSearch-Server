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

import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryFromFileImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class ApplicationPropertiesDirectoryTestNG {

    private CurrentServerDirectory curDir;

    @BeforeClass
    public void setUpClass() {
        curDir = new CurrentServerDirectoryFromFileImpl();
    }

    @Test
    public void name() {
        testBegin(ApplicationPropertiesDirectoryImpl.class, "name()");

        PropertiesDirectory propDir = new ApplicationPropertiesDirectoryImpl(curDir);
        String name = propDir.name();

        System.out.println(name);

        assertNotNull(name);
        assertFalse(name.isEmpty());
        assertTrue(name.contains(File.separator + "config" + File.separator + "application.properties"));

        testEnd(ApplicationPropertiesDirectoryImpl.class, "name()");
    }
}
