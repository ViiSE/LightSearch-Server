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

package lightsearch.server.validator;

import lightsearch.server.exception.ValidatorException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

public class PortValidatorTestNG {

    @Test
    @Parameters({"portValid"})
    public void validate_valid(int port) {
        testBegin(PortValidator.class, "validate [valid]");

        try {
            Validator<Integer> portValidator = new PortValidator();
            portValidator.validate(port);
        } catch (ValidatorException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }

        testEnd(PortValidator.class, "validate [valid]");
    }

    @Test(expectedExceptions = ValidatorException.class)
    @Parameters({"portInvalid"})
    public void validate_invalid(int port) throws ValidatorException {
        testBegin(PortValidator.class, "validate [invalid]");

        Validator<Integer> portValidator = new PortValidator();
        portValidator.validate(port);

        testEnd(PortValidator.class, "validate [invalid]");
    }
}
