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

import lightsearch.server.constants.HashAlgorithms;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ViiSE
 */
@Component("hashAlgorithmsSHA512")
public class HashAlgorithmSHA512Impl implements HashAlgorithm {

    @Override
    public String digest(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HashAlgorithms.SHA512);
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for(byte b : hash) {
                String hex = Integer.toString((b & 0xff) + 0x100, 16).substring(1);
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch(NoSuchAlgorithmException ignore) {
           return null;
        }
    }

    @Override
    public boolean isDigest(String digest) {
        Pattern pattern = Pattern.compile("^([a-f0-9]{128})$");
        Matcher matcher = pattern.matcher(digest);
        return matcher.matches();
    }
}
