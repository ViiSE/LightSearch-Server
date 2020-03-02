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

@ApiModel(value = "ClientBindCheckCommand", description = "Команда клиента для проверки привязки товара")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientBindCheckCommandDTO {

    @ApiModelProperty(notes = "Строка поиска", position = 1)
    private String barcode;
    @ApiModelProperty(notes = "true - штрих-код, указанный в <code>barcode</code>, является стандартом EAN-13," +
            "false - <code>barcode</code> может принимать любое значение", position = 2)
    private boolean checkEan13;

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setCheckEan13(boolean checkEan13) {
        this.checkEan13 = checkEan13;
    }

    public boolean getCheckEan13() {
        return checkEan13;
    }
}
