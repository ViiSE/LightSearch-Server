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

package lightsearch.server.constants;

import java.util.ArrayList;
import java.util.List;

public class DatasourceConstants {

    public static final String JDBC = "jdbc";
    public static final String URL = "spring.datasource.url";
    public static final String USERNAME = "spring.datasource.username";
    public static final String PASSWORD = "spring.datasource.password";
    public static final String SCRIPT_ENCODING = "spring.datasource.sql-script-encoding";
    public static final String DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
    public static final String DB_TYPE = "dbType";
    public static final String DB_NAME = "dbName";
    public static final String ADDITIONAL = "additional";
    public static final String IP = "ip";
    public static final String PORT = "port";
    public static final String POOL_AUTO_COMMIT = "spring.datasource.hikari.auto-commit";
    public static final String POOL_CONNECTION_TIMEOUT = "spring.datasource.hikari.connection-timeout";
    public static final String POOL_IDLE_TIMEOUT = "spring.datasource.hikari.idle-timeout";
    public static final String POOL_MAX_LIFE_TIME = "spring.datasource.hikari.max-lifetime";
    public static final String MAXIMUM_POOL_SIZE = "spring.datasource.hikari.maximum-pool-size";

    public static final List<String> listOf = new ArrayList<>() {{
        add(JDBC);
        add(URL);
        add(USERNAME);
        add(PASSWORD);
        add(SCRIPT_ENCODING);
        add(DRIVER_CLASS_NAME);
        add(DB_TYPE);
        add(DB_NAME);
        add(ADDITIONAL);
        add(IP);
        add(PORT);
        add(POOL_AUTO_COMMIT);
        add(POOL_CONNECTION_TIMEOUT);
        add(POOL_IDLE_TIMEOUT);
        add(POOL_MAX_LIFE_TIME);
        add(MAXIMUM_POOL_SIZE);
    }};
}
