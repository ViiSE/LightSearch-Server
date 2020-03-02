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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "AdminCommandResultWithClients", description = "Результат работы команды администратора со списком клиентов")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCommandResultWithClientsDTO {

    @ApiModelProperty(notes = "Статус выполненной команды", position = 1)
    private final boolean isDone;
    @ApiModelProperty(notes = "Сообщение", position = 2)
    private final String message;
    @ApiModelProperty(notes = "Список клиентов", position = 3)
    private List<ClientDTO> clients;

    @JsonCreator
    public AdminCommandResultWithClientsDTO(
            @JsonProperty("is_done") boolean isDone,
            @JsonProperty("message") String message) {
        this.isDone = isDone;
        this.message = message;
    }

    public void setClients(List<ClientDTO> clients) {
        this.clients = clients;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public String getMessage() {
        return message;
    }

    public List<ClientDTO> getClients() {
        return clients;
    }
}
