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

import lightsearch.server.data.ClientDTO;
import lightsearch.server.data.JWTTokenHeaderDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("clientWithJwtToken")
@Scope("prototype")
public class ClientWithJWTTokenImpl implements Client {

    private final Client client;
    private final JWTTokenHeader tokenHeader;

    public ClientWithJWTTokenImpl(Client client, JWTTokenHeader tokenHeader) {
        this.client = client;
        this.tokenHeader = tokenHeader;
    }

    @Override
    public Object formForSend() {
        ClientDTO clientDTO = (ClientDTO) client.formForSend();
        JWTTokenHeaderDTO tokenHeaderDTO = (JWTTokenHeaderDTO) tokenHeader.formForSend();
        clientDTO.setToken(tokenHeaderDTO);

        return clientDTO;
    }
}
