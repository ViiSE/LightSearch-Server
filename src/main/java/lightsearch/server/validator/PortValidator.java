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

@Component("portValidator")
public class PortValidator implements Validator<Integer> {

    @Override
    public void validate(Integer port) throws ValidatorException {
        if(port > 65535)
            throw new ValidatorException("The port value exceeds the maximum allowed value (65535)!", "Wrong port number");
        if(port < 1024)
            throw new ValidatorException("The port value is less than the minimum value (1024)!", "Wrong port number");
    }
}
