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
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.exception.ReaderException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("propertiesLocalChangerLocal")
public class PropertiesChangerLocalImpl implements PropertiesChanger<List<String>, Property<String>> {

    private final PropertiesReader<List<String>> propertiesReader;

    public PropertiesChangerLocalImpl(PropertiesReader<List<String>> propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    public List<String> change(Property<String> prop) throws PropertiesException {
        try {
            String propAsStr = prop.as();
            List<String> propsCh = Arrays.asList(propAsStr.split("\n"));

            return propertiesReader
                    .read()
                    .stream()
                    .map(property -> {
                        final StringBuilder res = new StringBuilder();
                        propsCh.forEach(propCh -> {
                            String propName = propCh.substring(0, propCh.indexOf("="));
                            if(property.contains(propName))
                                res.append(propCh); });
                        if(res.toString().isEmpty())
                            return property;
                        else
                            return res.toString(); })
                    .collect(Collectors.toList());
        } catch (ReaderException ex) {
            throw new PropertiesException(ex.getMessage(), ex.getLogMessage());
        }
    }
}
