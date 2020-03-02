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
import lightsearch.server.time.TimeUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Random;

@Component("jwtGeneratorDefault")
public class JWTGeneratorDefaultImpl implements JWTGenerator<String> {

    private final String secret;
    private final long jwtValidDayCount;
    private final HashAlgorithm hashAlgorithm;

    public JWTGeneratorDefaultImpl(
            @Value("${lightsearch.server.jwt-secret}") String secret,
            @Value("#{new Long('${lightsearch.server.jwt-valid-day-count}')}") long jwtValidDayCount,
            @Qualifier("hashAlgorithmsSHA512") HashAlgorithm hashAlgorithm) {
        this.secret = secret;
        this.jwtValidDayCount = jwtValidDayCount;
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public String generate(String IMEI) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        double salt = new Random().nextGaussian();
        String id = hashAlgorithm.digest(IMEI + salt);
        String subject = hashAlgorithm.digest(IMEI);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(now)
                .setId(id)
                .setIssuer("lightsearch")
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        long expMills = nowMills + TimeUtils.convertDaysToMilliseconds(jwtValidDayCount);
        Date exp = new Date(expMills);

        jwtBuilder.setExpiration(exp);

        return jwtBuilder.compact();
    }
}
