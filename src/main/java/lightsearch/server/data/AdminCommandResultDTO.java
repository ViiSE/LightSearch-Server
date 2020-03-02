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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCommandResultDTO {

    private final boolean isDone;
    private final String message;
    private List<String> blacklist;
    private List<ClientDTO> clients;

    @JsonCreator
    public AdminCommandResultDTO(
            @JsonProperty("is_done") boolean isDone,
            @JsonProperty("message") String message) {
        this.isDone = isDone;
        this.message = message;
    }

    public void setClients(List<ClientDTO> clients) {
        this.clients = clients;
    }

    public void setBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public List<ClientDTO> getClients() {
        return clients;
    }
}
