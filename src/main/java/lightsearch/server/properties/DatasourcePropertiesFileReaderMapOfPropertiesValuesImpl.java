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

import lightsearch.server.constants.DatasourceConstants;
import lightsearch.server.exception.ReaderException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("datasourcePropertiesFileReaderMapOfPropertiesValues")
public class DatasourcePropertiesFileReaderMapOfPropertiesValuesImpl implements PropertiesReader<Map<String, Object>> {

    private final PropertiesReader<List<String>> propertiesReader;

    public DatasourcePropertiesFileReaderMapOfPropertiesValuesImpl(PropertiesReader<List<String>> propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    public Map<String, Object> read() throws ReaderException {
        try {
            List<String> props = propertiesReader.read();
            Map<String, Object> properties = new HashMap<>();

            props.forEach(prop -> {
                if(!prop.isEmpty()) {
                    if (prop.contains("=")) {
                        String propName = prop.substring(0, prop.indexOf("="));
                        switch (propName) {
                            case DatasourceConstants.URL:
                                List<String> urlParams = List.of(getPropValue(prop).split(":"));
                                for(int i = 0; i < urlParams.size(); i++) {
                                    if(urlParams.get(i).equals(DatasourceConstants.JDBC))
                                        continue;
                                    if(urlParams.get(i).equals("h2")) {
                                        if (checkUrlH2(urlParams.get(i + 1))) {
                                            properties.put(DatasourceConstants.DB_TYPE, urlParams.get(i) + ":" + urlParams.get(++i));
                                            String notRealDbName = urlParams.get(++i);
                                            int additionalIdx = notRealDbName.indexOf(";");
                                            if (additionalIdx >= 0) {
                                                properties.put(DatasourceConstants.DB_NAME, notRealDbName.substring(0, notRealDbName.indexOf(";")));
                                                properties.put(DatasourceConstants.ADDITIONAL, notRealDbName.substring(notRealDbName.indexOf(";") + 1));
                                            } else {
                                                properties.put(DatasourceConstants.DB_NAME, notRealDbName);
                                                properties.put(DatasourceConstants.ADDITIONAL, "");
                                            }
                                        } else
                                            properties.put(DatasourceConstants.DB_TYPE, urlParams.get(i));
                                    } else {
                                        String dbType = urlParams.get(i);
                                        String dbIp = urlParams.get(++i).substring(2); // Remove '//' (jdbc:firebirdsql://)
                                        String portNameAndAdditional = urlParams.get(++i);
                                        int port = Integer.parseInt(portNameAndAdditional.substring(0, portNameAndAdditional.indexOf("/")));
                                        String dbName = portNameAndAdditional.substring(portNameAndAdditional.indexOf("/"), portNameAndAdditional.indexOf("?"));
                                        String additional = portNameAndAdditional.substring(portNameAndAdditional.indexOf("?") + 1);

                                        properties.put(DatasourceConstants.DB_TYPE, dbType);
                                        properties.put(DatasourceConstants.DB_NAME, dbName);
                                        properties.put(DatasourceConstants.ADDITIONAL, additional);
                                        properties.put(DatasourceConstants.IP, dbIp);
                                        properties.put(DatasourceConstants.PORT, port);
                                    }
                                }
                                break;
                            case DatasourceConstants.DRIVER_CLASS_NAME:
                            case DatasourceConstants.SCRIPT_ENCODING:
                            case DatasourceConstants.USERNAME:
                            case DatasourceConstants.PASSWORD:
                                properties.put(propName, getPropValue(prop));
                                break;
                            case DatasourceConstants.POOL_AUTO_COMMIT:
                                properties.put(propName, Boolean.parseBoolean(getPropValue(prop)));
                                break;
                            case DatasourceConstants.POOL_CONNECTION_TIMEOUT:
                            case DatasourceConstants.POOL_IDLE_TIMEOUT:
                            case DatasourceConstants.POOL_MAX_LIFE_TIME:
                            case DatasourceConstants.MAXIMUM_POOL_SIZE:
                                properties.put(propName, Long.parseLong(getPropValue(prop)));
                                break;
                        }
                    }
                }
            });

            return properties;
        } catch (ReaderException ex) {
            throw new ReaderException(ex.getMessage(), ex.getLogMessage());
        }
    }

    private String getPropValue(String prop) {
        return prop.substring(prop.indexOf("=") + 1);
    }

    private boolean checkUrlH2(String urlH2) {
        if(urlH2.equals("file"))
            return true;
        if(urlH2.equals("mem"))
            return true;
        if(urlH2.equals("tcp"))
            return true;
        if(urlH2.equals("ssl"))
            return true;
        if(urlH2.equals("zip"))
            return true;

        return false;
    }
}
