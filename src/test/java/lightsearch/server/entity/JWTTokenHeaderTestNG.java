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

package lightsearch.server.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.data.JWTTokenHeaderDTO;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.security.JWT;
import lightsearch.server.security.JWTFromIMEIImpl;
import lightsearch.server.time.JWTExpirationDateImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestUtils;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class JWTTokenHeaderTestNG {

    private String token;
    private ObjectMapper mapper = TestUtils.objectMapperWithJavaTimeModule();

    @BeforeClass
    @Parameters({"secret", "jwtValidDayCount", "IMEI"})
    public void setUpClass(String secret, long jwtValidDayCount, String IMEI) {
        HashAlgorithm hashAlgorithm = new HashAlgorithmSHA512Impl();
        JWT<String> jwt = new JWTFromIMEIImpl(
                secret,
                hashAlgorithm,
                new JWTExpirationDateImpl(jwtValidDayCount));

        token = jwt.generate(IMEI);
    }

    @Test
    public void formForSend() throws JsonProcessingException {
        testBegin(JWTTokenHeaderImpl.class, "formForSend()");

        JWTTokenHeader tokenHeader = new JWTTokenHeaderImpl(
                mapper,
                token);
        JWTTokenHeaderDTO headerDTO = (JWTTokenHeaderDTO) tokenHeader.formForSend();

        assertNotNull(headerDTO.getAlg(), "Alg is null!");
        assertNotNull(headerDTO.getIss(), "Typ is null!");
        assertNotNull(headerDTO.getValidTo(), "Exp is null!");

        System.out.println(mapper.writeValueAsString(headerDTO));

        testEnd(JWTTokenHeaderImpl.class, "formForSend()");
    }
}
