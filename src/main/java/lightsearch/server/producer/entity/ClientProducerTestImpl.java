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

package lightsearch.server.producer.entity;

import lightsearch.server.entity.Client;
import lightsearch.server.entity.ClientSimpleImpl;
import lightsearch.server.entity.ClientWithJWTTokenImpl;
import lightsearch.server.entity.JWTTokenHeader;

public class ClientProducerTestImpl implements ClientProducer {

    @Override
    public Client getClientSimpleInstance(String IMEI, String username) {
        return new ClientSimpleImpl(IMEI, username);
    }

    @Override
    public Client getClientWithJWTTokenInstance(Client client, JWTTokenHeader tokenHeader) {
        return new ClientWithJWTTokenImpl(client, tokenHeader);
    }
}
