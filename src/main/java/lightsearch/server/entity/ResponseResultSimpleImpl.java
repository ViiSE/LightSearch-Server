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

package lightsearch.server.entity;

import lightsearch.server.data.ResponseResultDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("responseResultSimple")
@Scope("prototype")
public class ResponseResultSimpleImpl implements ResponseResult {

    private final LocalDateTime localDateTime;

    public ResponseResultSimpleImpl(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public Object formForSend() {
        ResponseResultDTO responseResultDTO = new ResponseResultDTO();
        responseResultDTO.setDateTime(localDateTime);
        return responseResultDTO;
    }
}
