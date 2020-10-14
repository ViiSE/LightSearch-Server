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

package lightsearch.server.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AdminChangeDatabaseCommand", description = "Команда изменения регистрационных данных БД")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminChangeDatabaseCommandDTO {

    @ApiModelProperty(notes = "Имя пользователя БД", position = 1)
    private String username;
    @ApiModelProperty(notes = "Пароль пользователя БД", position = 2)
    private String password;
    @ApiModelProperty(notes = "Хост БД", position = 3)
    private String host;
    @ApiModelProperty(notes = "Порт БД", position = 4)
    private int port;
    @ApiModelProperty(notes = "Имя БД", position = 5)
    private String dbName;
    @ApiModelProperty(notes = "Тип БД (название СУБД)", position = 6)
    private String dbType;
    @ApiModelProperty(notes = "Кодировка скриптов SQL", position = 7)
    private String scriptEncoding;
    @ApiModelProperty(notes = "Имя JDBC драйвера", position = 8)
    private String driverClassName;
    @ApiModelProperty(notes = "Дополнительные параметры", position = 9)
    private String additional;
    @ApiModelProperty(notes = "Настройки пула соединений", position = 10)
    private DatasourcePoolDTO pool;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setScriptEncoding(String scriptEncoding) {
        this.scriptEncoding = scriptEncoding;
    }

    public String getScriptEncoding() {
        return scriptEncoding;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getAdditional() {
        return additional;
    }

    public void setPool(DatasourcePoolDTO pool) {
        this.pool = pool;
    }

    public DatasourcePoolDTO getPool() {
        return pool;
    }
}
