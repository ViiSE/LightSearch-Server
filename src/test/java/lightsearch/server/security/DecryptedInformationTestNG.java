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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static test.message.TestMessage.*;

public class DecryptedInformationTestNG {

    @BeforeClass
    public void setUpClass() {
        testBegin(DecryptedInformationRSAImpl.class);
    }

    @Test
    public void data() throws InformationException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        testMethod("data()");

        Keys<PublicKey, PrivateKey> keys = new KeysRSAImpl();
        String encText = encryptedText(Base64.getEncoder().encodeToString(keys.publicKey().getEncoded()));
        System.out.println("Encrypted: " + encText);

        Information<String> decInfo = new DecryptedInformationRSAImpl(
                new EncryptedInformationImpl(encText),
                keys);
        String decText = decInfo.data();

        System.out.println("Decrypted: " + decText);

        testEnd(DecryptedInformationRSAImpl.class, "data()");
    }

    @Test(expectedExceptions = InformationException.class)
    public void data_failedEncData() throws InformationException {
        testMethod("data() [failed encrypted data]");

        Keys<PublicKey, PrivateKey> keys = new KeysRSAImpl();
        String encText = "Wrong encrypted data";

        Information<String> decInfo = new DecryptedInformationRSAImpl(
                new EncryptedInformationImpl(encText),
                keys);
        decInfo.data();
    }

    private String encryptedText(String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        String text = "HELLO!";

        byte[] publicBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);

        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    @AfterClass
    public void shutdownClass() {
        testEnd(DecryptedInformationRSAImpl.class);
    }
}
