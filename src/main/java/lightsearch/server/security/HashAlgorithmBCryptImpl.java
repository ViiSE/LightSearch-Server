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
package lightsearch.server.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("hashAlgorithmsBCrypt")
public class HashAlgorithmBCryptImpl implements HashAlgorithm {

    @Override
    public String digest(String pass) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        return passEncoder.encode(pass);
    }

    @Override
    public boolean isDigest(String digest) {
        if (digest.startsWith("$2a$10$")) {
            return digest.length() == 60;
        }

        return false;
    }
}
