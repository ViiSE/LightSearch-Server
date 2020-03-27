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

import java.util.List;

@ApiModel(value = "ClientTKListCommandResult", description = "Результат работы команды выгрузки списка ТК")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientTKListCommandResultDTO {

    @ApiModelProperty(notes = "Статус выполненной команды", position = 1)
    private boolean isDone;
    @ApiModelProperty(notes = "Сообщение", position = 2)
    private String message;
    @ApiModelProperty(notes = "Список ТК", position = 4)
    @JsonProperty("tk_list") private List<String> TKList;

    public void setIsDone(String isDone) {
        this.isDone = isDone.equalsIgnoreCase("true");
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @JsonProperty("tk_list")
    public List<String> getTKList() {
        return TKList;
    }

    @JsonProperty("tk_list")
    public void setTKList(List<String> TKList) {
        this.TKList = TKList;
    }
}
