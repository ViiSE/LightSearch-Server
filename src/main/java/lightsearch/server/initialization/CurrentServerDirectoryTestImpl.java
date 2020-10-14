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

import java.io.File;

/**
 *
 * @author ViiSE
 */
public class CurrentServerDirectoryTestImpl implements CurrentServerDirectory {
    
    @Override
    public String name() {
        return System.getProperty("user.dir") + File.separator + ResourcesFilesPath.getResourcesFilesPath();
    }

    private static class ResourcesFilesPath {

        private static final String oldResourcesFilePath = levelUp() + levelUp() +
                dir("src") + dir("test") + dir("resources");

        private static final String resourcesFilesPath =
                dir("src") + dir("test") + dir("resources");

        public static String getResourcesFilesPath() {
            return resourcesFilesPath;
        }

        public static String getOldResourcesFilePath() { return oldResourcesFilePath; }

        private static String levelUp() {
            return ".." + File.separator;
        }

        private static String dir(String dirName) {
            return dirName + File.separator;
        }
    }
}
