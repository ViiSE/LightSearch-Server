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

public class IpValidatorTestNG {

    @Test
    @Parameters({"ipValid"})
    public void validate_valid(String ip) {
        testBegin(IpValidator.class, "validate [valid]");

        try {
            Validator<String> ipValidator = new IpValidator();
            ipValidator.validate(ip);
        } catch (ValidatorException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }

        testEnd(IpValidator.class, "validate [valid]");
    }

    @Test(expectedExceptions = ValidatorException.class)
    @Parameters({"ipInvalid"})
    public void validate_invalid(String ip) throws ValidatorException {
        testBegin(IpValidator.class, "validate [invalid]");

        Validator<String> ipValidator = new IpValidator();
        ipValidator.validate(ip);

        testEnd(IpValidator.class, "validate [invalid]");
    }
}
