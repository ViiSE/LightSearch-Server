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

package lightsearch.server.security;

import org.springframework.stereotype.Component;

import java.security.*;

@Component("keysRSA")
public class KeysRSAImpl implements Keys<PublicKey, PrivateKey> {

    private KeyPair pair;

    @Override
    public PublicKey publicKey() {
        if(pair == null)
            generate();
        return pair.getPublic();
    }

    @Override
    public PrivateKey privateKey() {
        if(pair == null)
            generate();
        return pair.getPrivate();
    }

    private void generate() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048, new SecureRandom());
            pair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
