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

import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.BlacklistServiceTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class BlacklistCreatorFromFileTestNG {

    private BlacklistCreator blacklistCreator;

    BlacklistService<String> blService = new BlacklistServiceTestImpl();
    CurrentServerDirectory curDir = new CurrentServerDirectoryTestImpl();

    @BeforeClass
    public void setUpClass() {
        blacklistCreator = new BlacklistCreatorFromFileImpl(curDir, blService);
        testBegin(BlacklistCreator.class);
    }

    @Test(priority = 1)
    public void create_blacklistFileIsNotFound() {
        testMethod("create() [blacklist file is not found]");

        Path filePath = Paths.get(curDir.name()+ "blacklist");
        try {
            Files.delete(filePath);
        } catch (IOException ex) {
            catchMessage(ex);
        }

        blacklistCreator.create();
        assertTrue(blService.blacklist().isEmpty());
        assertTrue(Files.exists(Paths.get(curDir.name()+ "blacklist")));
    }

    @Test(priority = 2)
    public void create_blacklistFileIsFound() {
        testMethod("create() [blacklist file is found]");

        preparation();

        blacklistCreator.create();
        System.out.println("blacklist: ");
        blService.blacklist().forEach(System.out::println);

        assertEquals(blService.blacklist().size(), 5);
        assertTrue(Files.exists(Paths.get(curDir.name()+ "blacklist")));
    }

    private void preparation() {
        try (FileOutputStream fout = new FileOutputStream(curDir.name() + "blacklist", true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
            for(int i = 0; i < 5; i++) {
                bw.write("IMEI" + i);
                bw.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @AfterClass
    public void teardownClass() {
        System.out.println("Clear blacklist file...");
        try (FileOutputStream fout = new FileOutputStream(curDir.name() + "blacklist", false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                bw.write("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        testEnd(BlacklistCreator.class);
    }
}
