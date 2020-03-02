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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientCommandDTO {

    @JsonIgnore private String lsCode;
    @JsonIgnore private String command;
    private String IMEI;
    private String ip;
    private String os;
    private String model;
    private String username;
    private String password;
    private String barcode;
    private String sklad;
    @JsonProperty("tk")
    private String TK;
    private List<ProductDTO> data;
    @JsonProperty("user_ident") private String userIdentifier;
    private String delivery;
    private String cardCode;
    private boolean checkEan13;
    private String factoryBarcode;

    @JsonIgnore
    public void setLsCode(String lsCode) {
        this.lsCode = lsCode;
    }

    @JsonIgnore
    public String getLsCode() {
        return lsCode;
    }

    @JsonIgnore
    public void setCommand(String command) {
        this.command = command;
    }

    @JsonIgnore
    public String getCommand() {
        return command;
    }

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

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setSklad(String sklad) {
        this.sklad = sklad;
    }

    public String getSklad() {
        return sklad;
    }

    @JsonProperty("tk")
    public void setTK(String TK) {
        this.TK = TK;
    }

    @JsonProperty("tk")
    public String getTK() {
        return TK;
    }

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

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCheckEan13(boolean checkEan13) {
        this.checkEan13 = checkEan13;
    }

    public boolean getCheckEan13() {
        return checkEan13;
    }

    public void setFactoryBarcode(String factoryBarcode) {
        this.factoryBarcode = factoryBarcode;
    }

    public String getFactoryBarcode() {
        return factoryBarcode;
    }
}
