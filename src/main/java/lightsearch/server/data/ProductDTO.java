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
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Product", description = "Товар")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    @ApiModelProperty(notes = "ИД товара", position = 1)
    private String id;
    @ApiModelProperty(notes = "Подразделение", position = 2)
    private String subdiv;
    @ApiModelProperty(notes = "Имя", position = 3)
    private String name;
    @ApiModelProperty(notes = "Количество", position = 4)
    private String amount;
    @ApiModelProperty(notes = "Цена", position = 5)
    private String price;
    @ApiModelProperty(notes = "Единица измерения", position = 6)
    private String ei;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSubdiv(String subdiv) {
        this.subdiv = subdiv;
    }

    public String getSubdiv() {
        return subdiv;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAmount(float amount) {
        this.amount = String.valueOf(amount);
    }

    public String getAmount() {
        return amount;
    }

    public void setPrice(float price) {
        this.price = String.valueOf(price);
    }

    public String getPrice() {
        return price;
    }

    public void setEi(String ei) {
        this.ei = ei;
    }

    public String getEi() {
        return ei;
    }
}
