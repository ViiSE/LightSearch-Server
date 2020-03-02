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

@ApiModel(value = "ClientConfirmSoftCheckProductsCommand", description = "Команда клиента для подтверждения товаров мягкого чека")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientConfirmSoftCheckProductsCommandDTO {

    @ApiModelProperty(notes = "Товары мягкого чека", position = 1)
    private List<ProductDTO> data;
    @ApiModelProperty(notes = "Уникальный идентификатор пользователя", position = 2)
    @JsonProperty("user_ident") private String userIdentifier;
    @ApiModelProperty(notes = "Код карточки, за которой закреплен мягкий чек", position = 3)
    private String cardCode;

    public void setData(List<ProductDTO> data) {
        this.data = data;
    }

    public List<ProductDTO> getData() {
        return data;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    @JsonProperty("user_ident")
    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardCode() {
        return cardCode;
    }
}
