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

package lightsearch.server.validator;

import lightsearch.server.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component("clientTimeoutValidator")
public class ClientTimeoutValidator implements Validator<Integer> {

    @Override
    public void validate(Integer clientTimeout) throws ValidatorException {
        if(clientTimeout <= 0)
            throw new ValidatorException(
                    "Значение времени жизни токена должно быть больше нуля!",
                    "Invalid client timeout value. The timeout value may be greater than zero.");
    }
}
