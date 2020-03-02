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

import java.util.List;

@ApiModel(value = "ClientSearchCommandResult", description = "Результат работы команды поиска")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientSearchCommandResultDTO {

    @ApiModelProperty(notes = "Статус выполненной команды", position = 1)
    private boolean isDone;
    @ApiModelProperty(notes = "Сообщение", position = 2)
    private String message;
    @ApiModelProperty(notes = "Найденные товары", position = 3)
    private List<ProductDTO> data;

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

    public List<ProductDTO> getData() {
        return data;
    }

    public void setData(List<ProductDTO> data) {
        this.data = data;
    }
}
