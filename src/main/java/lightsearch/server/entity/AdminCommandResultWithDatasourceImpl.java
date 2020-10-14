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

package lightsearch.server.entity;

import lightsearch.server.data.AdminCommandResultWithDatasourceDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandResultWithDatasource")
@Scope("prototype")
public class AdminCommandResultWithDatasourceImpl implements AdminCommandResult {

    private final AdminCommandResultWithDatasourceDTO dto;

    public AdminCommandResultWithDatasourceImpl(AdminCommandResultWithDatasourceDTO dto) {
        this.dto = dto;
    }

    @Override
    public Object formForSend() {
        return dto;
    }

    @Override
    public boolean isDone() {
        return dto.getIsDone();
    }

    @Override
    public String message() {
        return dto.getMessage();
    }
}
