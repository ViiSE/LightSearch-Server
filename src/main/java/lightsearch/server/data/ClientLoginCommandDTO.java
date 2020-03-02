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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ClientLoginCommand", description = "Команда клиента для авторизации")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientLoginCommandDTO {

    @ApiModelProperty(notes = "IMEI клиента", position = 1)
    private String IMEI;
    @ApiModelProperty(notes = "IP-адрес клиента", position = 2)
    private String ip;
    @ApiModelProperty(notes = "ОС клиента", position = 3)
    private String os;
    @ApiModelProperty(notes = "Модель устройства клиента", position = 4)
    private String model;
    @ApiModelProperty(notes = "Имя пользователя", position = 5)
    private String username;
    @ApiModelProperty(notes = "Пароль пользователя", position = 6)
    private String password;
    @ApiModelProperty(notes = "Уникальный идентификатор клиента", position = 7)
    @JsonProperty("user_ident") private String userIdentifier;

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return os;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

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

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    @JsonProperty("user_ident")
    public String getUserIdentifier() {
        return userIdentifier;
    }
}
