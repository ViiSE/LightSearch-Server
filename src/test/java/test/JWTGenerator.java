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

package test;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

import static test.TestUtils.path;
import static test.TestUtils.readFile;

public class JWTGenerator {

    public static void main(String[] args) {
        String id = readId();
        String issuer = readIssuer();
        String secret = readSecret();
        String subject = readSubject();
        long timeToLive = readTimeToLive();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(now)
                .setId(id)
                .setIssuer(issuer)
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        if(timeToLive >= 0) {
            long expMills = nowMills + timeToLive;
            Date exp = new Date(expMills);
            jwtBuilder.setExpiration(exp);
        }

        System.out.println("Generated token: " + jwtBuilder.compact());
    }

    private static String readId() {
        return readFile(path("jwtstore/JWTId"));
    }

    private static String readIssuer() {
        return readFile(path("jwtstore/JWTIssuer"));
    }

    private static String readSecret() {
        return readFile(path("jwtstore/JWTSecret"));
    }

    private static String readSubject() {
        return readFile(path("jwtstore/JWTSubject"));
    }

    private static long readTimeToLive() {
        return Long.parseLong(readFile(path("jwtstore/JWTTimeToLive")));
    }
}
