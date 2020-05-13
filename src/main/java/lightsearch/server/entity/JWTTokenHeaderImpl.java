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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.data.JWTTokenHeaderDTO;
import lightsearch.server.time.TimeUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component("jwtTokenHeader")
@Scope("prototype")
public class JWTTokenHeaderImpl implements JWTTokenHeader {

    private final ObjectMapper mapper;
    private final String token;

    public JWTTokenHeaderImpl(ObjectMapper mapper, String token) {
        this.mapper = mapper;
        this.token = token;
    }

    @Override
    public Object formForSend() {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            String header1 = new String(decoder.decode(token.split("\\.")[0]));
            String header2 = new String(decoder.decode(token.split("\\.")[1]));

            String alg = header1.replaceAll("\"", "");
            alg = alg.substring(alg.indexOf(":") + 1, alg.indexOf("}"));

            Map<String, String> header2Values = mapper.readValue(header2, new TypeReference<Map<String, String>>() {});
            String iss = header2Values.get("iss");
            String exp = header2Values.get("exp");
            String id = header2Values.get("jti");

            JWTTokenHeaderDTO jwtTokenHeaderDTO = new JWTTokenHeaderDTO();
            jwtTokenHeaderDTO.setAlg(alg);
            jwtTokenHeaderDTO.setIss(iss);
            jwtTokenHeaderDTO.setId(id);
            jwtTokenHeaderDTO.setValidTo(TimeUtils.convertMillisecondsToDateTime(exp));

            return jwtTokenHeaderDTO;
        } catch (IOException ignore) { }

        return new JWTTokenHeaderDTO();
    }
}
