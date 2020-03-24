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

import io.jsonwebtoken.*;
import lightsearch.server.time.JWTExpirationDateImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.xml.bind.DatatypeConverter;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.catchMessage;

public class JWTFromIMEITestNG {

    private JWT<String> jwt;
    private HashAlgorithm hashAlgorithm = new HashAlgorithmSHA512Impl();

    @BeforeClass
    @Parameters({"secret", "jwtValidDayCount"})
    public void setUpClass(String secret, long jwtValidDayCount) {

        hashAlgorithm = new HashAlgorithmSHA512Impl();
        jwt = new JWTFromIMEIImpl(secret, hashAlgorithm, new JWTExpirationDateImpl(jwtValidDayCount));
    }

    @Test
    @Parameters({"IMEI", "secret"})
    public void generate(String IMEI, String secret) {
        String token = jwt.generate(IMEI);

        System.out.println("Token: " + token);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token).getBody();

            System.out.println("Body:");
            System.out.println("\tId: " + claims.getId());
            System.out.println("\tIssuer: " + claims.getIssuer());
            System.out.println("\tSubject: " + claims.getSubject());
            System.out.println("\tExp: " + claims.getExpiration());

            assertEquals(claims.getSubject(), hashAlgorithm.digest(IMEI));
        } catch (ExpiredJwtException |
                UnsupportedJwtException |
                MalformedJwtException |
                SignatureException |
                IllegalArgumentException ex) {
            catchMessage(ex);
            throw new RuntimeException(ex);
        }
    }
}
