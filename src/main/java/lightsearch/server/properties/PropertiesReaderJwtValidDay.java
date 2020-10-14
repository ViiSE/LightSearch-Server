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

package lightsearch.server.properties;

import lightsearch.server.entity.Property;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.producer.entity.PropertyProducer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Component("propertiesReaderJwtValidDay")
public class PropertiesReaderJwtValidDay implements PropertiesReader<Property<Integer>> {

    private final PropertiesDirectory propertiesDirectory;
    private final PropertyProducer propertyProducer;

    public PropertiesReaderJwtValidDay(PropertiesDirectory propertiesDirectory, PropertyProducer propertyProducer) {
        this.propertiesDirectory = propertiesDirectory;
        this.propertyProducer = propertyProducer;
    }

    @Override
    public Property<Integer> read() throws ReaderException {
        try {
            Optional<String> propOpt = Files.readAllLines(Paths.get(propertiesDirectory.name()), StandardCharsets.UTF_8)
                    .stream()
                    .filter(pr -> pr.contains("lightsearch.server.jwt-valid-day-count"))
                    .findFirst();
            if(propOpt.isPresent()) {
                String prop = propOpt.get();
                int timeoutValue = Integer.parseInt(prop.substring(prop.indexOf("=") + 1));
                return propertyProducer.getClientTimeoutPropertyValueInstance(timeoutValue);
            } else {
                throw new ReaderException("Не удалось считать настройки сервера. Сообщение: isPresent() - false", "isPresent() - false");
            }
        } catch (IOException ex) {
            throw new ReaderException("Не удалось считать настройки сервера. Сообщение: " + ex.getMessage(), ex.getMessage());
        }
    }
}
