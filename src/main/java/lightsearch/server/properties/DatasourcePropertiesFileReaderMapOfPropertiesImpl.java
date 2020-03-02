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

import lightsearch.server.entity.Property;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.producer.entity.PropertyProducer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("datasourcePropertiesFileReaderMapOfProperties")
public class DatasourcePropertiesFileReaderMapOfPropertiesImpl implements PropertiesReader<Map<String, Property<String>>> {

    private final PropertyProducer propertyProducer;
    private final PropertiesDirectory propertiesDirectory;

    public DatasourcePropertiesFileReaderMapOfPropertiesImpl(
            PropertyProducer propertyProducer,
            PropertiesDirectory propertiesDirectory) {
        this.propertyProducer = propertyProducer;
        this.propertiesDirectory = propertiesDirectory;
    }

    @Override
    public Map<String, Property<String>> read() throws ReaderException {
        try {
            List<String> props = Files.readAllLines(Paths.get(propertiesDirectory.name()), StandardCharsets.UTF_8);
            Map<String, Property<String>> properties = new HashMap<>();

            props.forEach(prop -> {
                if(!prop.isEmpty()) {
                    if (prop.contains("=")) {
                        Property<String> property = null;
                        String propName = prop.substring(0, prop.indexOf("="));
                        switch (propName) {
                            case "spring.datasource.url":
                                String url = prop.substring(prop.indexOf("=") + 1);
                                property = propertyProducer.getDatasourceURLPropertyInstance(url);
                                break;
                            case "spring.datasource.driver-class-name":
                                String driverName = prop.substring(prop.indexOf("=") + 1);
                                property = propertyProducer.getDatasourceDriverClassNamePropertyInstance(driverName);
                                break;
                        }
                        if (property != null)
                            properties.put(property.name(), property);
                    }
                }
            });

            return properties;
        } catch (IOException ex) {
            throw new ReaderException("Не удалось считать настройки сервера. Сообщение: " + ex.getMessage(), ex.getMessage());
        }
    }
}
