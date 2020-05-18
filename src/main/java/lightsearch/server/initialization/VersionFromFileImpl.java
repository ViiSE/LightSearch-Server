/*
 *    Copyright 2020 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.server.initialization;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Component("versionFromFile")
public class VersionFromFileImpl implements Version {

    private final CurrentServerDirectory curDir;


    public VersionFromFileImpl(@Qualifier("currentServerDirectoryFromFile") CurrentServerDirectory curDir) {
        this.curDir = curDir;
    }

    @Override
    public String code() {
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(curDir.name() + "VERSION")))) {
            return br.readLine();
        } catch (IOException ex) {
            return "UNKNOWN";
        }
    }
}
