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

package lightsearch.server.producer.entity;

import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.entity.ResponseResult;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("responseResultProducerDefault")
public class ResponseResultProducerDefaultImpl implements ResponseResultProducer {

    private final ApplicationContext ctx;

    public ResponseResultProducerDefaultImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public ResponseResult getResponseResultSimpleInstance(LocalDateTime localDateTime) {
        return (ResponseResult) ctx.getBean("responseResultSimple", localDateTime);
    }

    @Override
    public ResponseResult getResponseResultWithCommandResultInstance(
            ResponseResult responseResult, ClientCommandResult clientCommandResult) {
        return (ResponseResult) ctx.getBean("responseResultWithCommandResult", responseResult, clientCommandResult);
    }
}
