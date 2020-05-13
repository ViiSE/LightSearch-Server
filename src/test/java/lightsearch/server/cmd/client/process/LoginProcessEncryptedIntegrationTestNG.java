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

package lightsearch.server.cmd.client.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.Processes;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.entity.*;
import lightsearch.server.security.Keys;
import lightsearch.server.security.KeysRSAImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
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

@SpringBootTest(classes = LightSearchServer.class)
@TestPropertySource(locations = "classpath:/test-firebird.properties")
public class LoginProcessEncryptedIntegrationTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Processes<ClientCommand, ClientCommandResult> holder;

    @Autowired
    private Keys<PublicKey, PrivateKey> keys = new KeysRSAImpl();

    private ClientCommand clientCommand;

    @BeforeClass
    @Parameters({"IMEI", "ip", "os", "userIdent", "model", "username", "password"})
    public void setUpClass(String IMEI, String ip, String os, String userIdent, String model, String username, String password) throws JsonProcessingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        ClientCommand cmd = new ClientCommandWithUsernameAndPasswordImpl(
                new ClientCommandWithIMEIImpl(
                        new ClientCommandWithIpImpl(
                                new ClientCommandWithOsImpl(
                                        new ClientCommandWithUserIdentifierImpl(
                                                new ClientCommandWithModelImpl(
                                                        new ClientCommandSimpleImpl(
                                                                ClientCommands.LOGIN),
                                                        model),
                                                userIdent),
                                        os),
                                ip),
                        IMEI),
                username,
                password);
        String encData = encryptedText(
                Base64.getEncoder().encodeToString(keys.publicKey().getEncoded()),
                mapper.writeValueAsString(cmd.formForSend()));

        clientCommand = new ClientCommandWithEncryptedDataImpl(
                new ClientCommandSimpleImpl(ClientCommands.LOGIN_ENCRYPTED),
                encData);
    }

    @Test
    public void apply() throws JsonProcessingException {
        ClientCommandResult result = holder.get(ClientCommands.LOGIN_ENCRYPTED).apply(clientCommand);

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.formForSend()));
    }

    private String encryptedText(String text, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        byte[] publicBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);

        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }
}
