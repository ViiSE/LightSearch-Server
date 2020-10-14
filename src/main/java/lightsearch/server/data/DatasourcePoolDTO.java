/*
 *    Copyright 2020 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.server.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DatasourcePool", description = "Настройки пула соединений БД")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatasourcePoolDTO {

    @ApiModelProperty(notes = "Автоматический коммит соединения, когда он возвращается в пул", position = 1)
    private boolean autoCommit;
    @ApiModelProperty(notes = "Максимальное время ожидания клиента соединения пула", position = 2)
    private long connectionTimeout;
    @ApiModelProperty(notes = "Максимальное время простоя соединения в пуле", position = 3)
    private long idleTimeout;
    @ApiModelProperty(notes = "Максимальное время жизни соединения в пуле", position = 4)
    private long maxLifeTime;
    @ApiModelProperty(notes = "Маскимальный размер пула", position = 5)
    private long maximumPoolSize;

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setMaxLifeTime(long maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }

    public long getMaxLifeTime() {
        return maxLifeTime;
    }

    public void setMaximumPoolSize(long maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public long getMaximumPoolSize() {
        return maximumPoolSize;
    }
}
