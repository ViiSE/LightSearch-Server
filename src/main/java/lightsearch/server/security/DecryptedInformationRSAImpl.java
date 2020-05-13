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

import lightsearch.server.exception.InformationException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Component("decryptedInformationRSA")
@Scope("prototype")
public class DecryptedInformationRSAImpl implements Information<String> {

    private final Information<String> encryptedInformation;
    private final Keys<PublicKey, PrivateKey> keys;

    public DecryptedInformationRSAImpl(
            Information<String> encryptedInformation,
            Keys<PublicKey, PrivateKey> keys) {
        this.encryptedInformation = encryptedInformation;
        this.keys = keys;
    }

    @Override
    public String data() throws InformationException {
        try {
            byte[] bytes = Base64.getDecoder().decode(encryptedInformation.data());

            Cipher decryptCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, keys.privateKey());

            return new String(decryptCipher.doFinal(bytes), StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException |
                IllegalBlockSizeException |
                BadPaddingException |
                NoSuchAlgorithmException |
                IllegalArgumentException |
                InvalidKeyException ex) {
            throw new InformationException("Произошла внутренняя ошибка в LightSearch", ex.getMessage());
        }
    }
}
