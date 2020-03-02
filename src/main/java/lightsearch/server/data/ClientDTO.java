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

package lightsearch.server.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Client", description = "Клиент")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {

    @ApiModelProperty(notes = "Заголовок JWT токена", position = 1)
    private JWTTokenHeaderDTO token;
    @ApiModelProperty(notes = "Уникальный идентификатор пользователя", position = 2)
    private final String IMEI;
    @ApiModelProperty(notes = "Имя пользователя", position = 3)
    private final String username;

    public ClientDTO(String IMEI, String username) {
        this.IMEI = IMEI;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getIMEI() {
        return IMEI;
    }

    @JsonProperty("token")
    public JWTTokenHeaderDTO getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(JWTTokenHeaderDTO token) {
        this.token = token;
    }
}
