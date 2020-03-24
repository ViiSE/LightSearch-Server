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

package lightsearch.server.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lightsearch.server.time.JWTExpiration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Random;

@Component("jwtFromIMEI")
public class JWTFromIMEIImpl implements JWT<String> {

    private final String secret;
    private final HashAlgorithm hashAlgorithm;
    private final JWTExpiration<Date> jwtExpiration;

    public JWTFromIMEIImpl(
            @Value("${lightsearch.server.jwt-secret}") String secret,
            @Qualifier("hashAlgorithmsSHA512") HashAlgorithm hashAlgorithm,
            JWTExpiration<Date> jwtExpiration) {
        this.secret = secret;
        this.hashAlgorithm = hashAlgorithm;
        this.jwtExpiration = jwtExpiration;
    }

    @Override
    public String generate(String IMEI) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date validDate = jwtExpiration.until();

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        double salt = new Random().nextGaussian();
        String id = hashAlgorithm.digest(IMEI + salt);
        String subject = hashAlgorithm.digest(IMEI);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(validDate)
                .setId(id)
                .setIssuer("lightsearch")
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        long expMills = validDate.toInstant().toEpochMilli();
        Date exp = new Date(expMills);

        jwtBuilder.setExpiration(exp);

        return jwtBuilder.compact();
    }
}
