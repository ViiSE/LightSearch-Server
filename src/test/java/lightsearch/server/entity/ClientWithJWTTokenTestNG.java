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
import lightsearch.server.data.ClientDTO;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import lightsearch.server.security.JWTGenerator;
import lightsearch.server.security.JWTGeneratorDefaultImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class ClientWithJWTTokenTestNG {

    private JWTTokenHeader tokenHeader;

    @BeforeClass
    @Parameters({"secret", "jwtValidDayCount", "IMEI"})
    public void setUpClass(String secret, long jwtValidDayCount, String IMEI) {
        HashAlgorithm hashAlgorithm = new HashAlgorithmSHA512Impl();
        JWTGenerator<String> jwtGenerator = new JWTGeneratorDefaultImpl(
                secret,
                jwtValidDayCount,
                hashAlgorithm);

        tokenHeader = new JWTTokenHeaderDefaultImpl(
                TestUtils.objectMapperWithJavaTimeModule(),
                jwtGenerator.generate(IMEI));
    }

    @Test
    @Parameters({"IMEI", "username"})
    public void formForSend(String IMEI, String username) throws JsonProcessingException {
        testBegin(ClientWithJWTTokenImpl.class, "formForSend()");

        Client client = new ClientWithJWTTokenImpl(
                new ClientSimpleImpl(IMEI, username),
                tokenHeader);
        ClientDTO clientDTO = (ClientDTO) client.formForSend();

        String imei = clientDTO.getIMEI();
        String uname = clientDTO.getUsername();

        assertNotNull(imei, "IMEI is null!");
        assertNotNull(uname, "Username is null!");
        assertEquals(imei, IMEI);
        assertEquals(uname, username);

        System.out.println(TestUtils.objectMapperWithJavaTimeModule().writeValueAsString(clientDTO));

        testEnd(ClientWithJWTTokenImpl.class, "formForSend()");
    }
}
