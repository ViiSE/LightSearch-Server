/*
 *  Copyright 2019 ViiSE.
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
 */

package lightsearch.server.properties;

import lightsearch.server.exception.ReaderException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component("propertiesFileReaderListOfString")
public class PropertiesFileReaderListOfStringImpl implements PropertiesReader<List<String>> {

    private final PropertiesDirectory propertiesDirectory;

    public PropertiesFileReaderListOfStringImpl(PropertiesDirectory propertiesDirectory) {
        this.propertiesDirectory = propertiesDirectory;
    }

    @Override
    public List<String> read() throws ReaderException {
        try {
            return Files.readAllLines(Paths.get(propertiesDirectory.name()), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new ReaderException("Не удалось считать настройки сервера. Сообщение: " + ex.getMessage(), ex.getMessage());
        }
    }
}
