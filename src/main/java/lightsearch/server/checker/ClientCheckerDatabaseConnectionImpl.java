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

package lightsearch.server.checker;

import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.Property;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.properties.PropertiesReader;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@Component("clientCheckerDatabaseConnection")
public class ClientCheckerDatabaseConnectionImpl implements Checker<ClientCommand> {

    private final PropertiesReader<Map<String, Property<String>>> propertiesReader;

    public ClientCheckerDatabaseConnectionImpl(PropertiesReader<Map<String, Property<String>>> propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    public void check(ClientCommand command) throws CheckerException {
        try {
            Map<String, Property<String>> propertyMap = propertiesReader.read();

            String driverName = propertyMap.get("datasource.driver-class-name").as();
            String url = propertyMap.get("datasource.url").as();

            ClientCommandDTO clientCommandDTO = (ClientCommandDTO) command.formForSend();

            String username = clientCommandDTO.getUsername();
            String password = clientCommandDTO.getPassword();
            Class.forName(driverName);
            DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            throw new CheckerException(ex.getMessage(), "Ошибка драйвера JDBC.");
        } catch (SQLException ex) {
            //335544344 - неверное имя базы
            //335544345 - неверное имя юзера или пароль
            //335544721 - неверный порт или ip, или сервер отключен
            switch (ex.getErrorCode()) {
                case 335544344:
                    throw new CheckerException("Invalid database name", "Неверное имя базы данных.");
                case 335544345:
                    throw new CheckerException("Invalid username and/or password", "Неверное имя пользователя и/или пароль.");
                case 335544721:
                    throw new CheckerException("Invalid port and/or host, or server is shut down", "Неверный порт и/или хост, или сервер отключен.");
            }
            throw new CheckerException(ex.getMessage(), "Неизвестная ошибка.");
        } catch (ReaderException ex) {
            throw new CheckerException(ex.getMessage(), ex.getLogMessage());
        }
    }
}
