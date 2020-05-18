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

package lightsearch.server.initialization;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.*;

public class VersionFromFileTestNG {

    private static final String VERSION = "3.7.0";

    private Version version;

    CurrentServerDirectory curDir = new CurrentServerDirectoryTestImpl();

    @BeforeClass
    public void setUpClass() {
        version = new VersionFromFileImpl(curDir);
        testBegin(VersionFromFileImpl.class);
    }

    @Test(priority = 1)
    public void code_versionFileIsNotFound() throws IOException {
        testMethod("code() [version file is not found]");

        Path filePath = Paths.get(curDir.name()+ "VERSION");
        try {
            Files.delete(filePath);
        } catch (IOException ex) {
            catchMessage(ex);
        }

        String v = version.code();
        assertEquals(v, "UNKNOWN");
        System.out.println("Version: " + v);

        File f = new File(curDir.name() + "VERSION");
        if(f.createNewFile())
            System.out.println("File created!");
    }

    @Test(priority = 2)
    public void code_blacklistFileIsFound() {
        testMethod("code() [version file is found]");

        preparation();

        String v = version.code();
        System.out.println("Version: " + v);

        assertEquals(v, VERSION);
    }

    private void preparation() {
        try (FileOutputStream fout = new FileOutputStream(curDir.name() + "VERSION", true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
            bw.write(VERSION);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @AfterClass
    public void teardownClass() {
        testEnd(VersionFromFileImpl.class);
    }
}
