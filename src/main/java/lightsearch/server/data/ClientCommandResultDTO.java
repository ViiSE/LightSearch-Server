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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientCommandResultDTO {

    private boolean isDone;
    private String message;
    @JsonProperty("ident") private String userIdentifier;
    @JsonProperty("tk_list") private List<String> TKList;
    @JsonProperty("sklad_list") private List<String> skladList;
    private List<ProductDTO> data;
    private String token;

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

    @JsonProperty("user_ident")
    public String getUserIdentifier() {
        return userIdentifier;
    }

    @JsonProperty("user_ident")
    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    @JsonProperty("tk_list")
    public List<String> getTKList() {
        return TKList;
    }

    @JsonProperty("tk_list")
    public void setTKList(List<String> TKList) {
        this.TKList = TKList;
    }

    @JsonProperty("sklad_list")
    public List<String> getSkladList() {
        return skladList;
    }

    @JsonProperty("sklad_list")
    public void setSkladList(List<String> skladList) {
        this.skladList = skladList;
    }

    public List<ProductDTO> getData() {
        return data;
    }

    public void setData(List<ProductDTO> data) {
        this.data = data;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
