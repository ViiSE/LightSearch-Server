/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.server.initialization;

import lightsearch.server.service.BlacklistService;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 *
 * @author ViiSE
 */

@Service("blacklistCreatorFromFile")
public class BlacklistCreatorFromFileImpl implements BlacklistCreator {

    private final CurrentServerDirectory currentDirectory;
    private final BlacklistService<String> blacklistService;

    public BlacklistCreatorFromFileImpl(
            CurrentServerDirectory currentDirectory,
            BlacklistService<String> blacklistService) {
        this.currentDirectory = currentDirectory;
        this.blacklistService = blacklistService;
    }

    @Override
    public void create() {
        String currentDirectory = this.currentDirectory.name();

        try(FileInputStream fin = new FileInputStream(currentDirectory + "blacklist");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            String strLine;
            while ((strLine = br.readLine()) != null)
                blacklistService.add(strLine);
        } catch(IOException ex) {
            System.out.println();
            System.out.println("Error blacklist file: " + ex.getMessage());
            System.out.println("Create new blacklist file...");
            try {
                File blacklistFile = new File(currentDirectory + "blacklist");
                if(blacklistFile.createNewFile())
                    System.out.println("Done!");
            } catch(IOException ioEx) {
                throw new RuntimeException("Error: " + ioEx.getMessage());
            }
        }
    }
}
