/*
 *  Copyright 2020 ViiSE.
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
 *
 */

package lightsearch.server.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel(value = "JWTTokenHeader", description = "Заголовок JWT токена")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JWTTokenHeaderDTO {

    @ApiModelProperty(notes = "Тип используемого алгоритма", position = 1)
    private String alg;
    @ApiModelProperty(notes = "Поставщик JWT токена", position = 2)
    private String iss;
    @ApiModelProperty(notes = "Идентификатор JWT токена", position = 3)
    private String id;
    @ApiModelProperty(example = "2020-05-05 10:00:00", notes = "Срок годности JWT токена", position = 4)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validTo;

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getAlg() {
        return alg;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getIss() {
        return iss;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }
}
