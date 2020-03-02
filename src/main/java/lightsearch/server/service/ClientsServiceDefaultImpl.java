/*
 *  Copyright 2019 ViiSE.
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
 */

package lightsearch.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.entity.Client;
import lightsearch.server.exception.ClientNotFoundException;
import lightsearch.server.producer.entity.ClientProducer;
import lightsearch.server.producer.entity.JWTTokenHeaderProducer;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("clientsServiceDefault")
public class ClientsServiceDefaultImpl implements ClientsService<String, Client> {

    private final static Map<String, Client> clients = new HashMap<>();
    private final HashAlgorithm hashAlgorithm;
    private final ObjectMapper mapper;
    private final ClientProducer clientProducer;
    private final JWTGenerator<String> jwtGenerator;
    private final JWTTokenHeaderProducer jwtTokenHeaderProducer;

    public ClientsServiceDefaultImpl(
            @Qualifier("hashAlgorithmsSHA512") HashAlgorithm hashAlgorithm,
            ObjectMapper mapper,
            ClientProducer clientProducer,
            JWTGenerator<String> jwtGenerator,
            JWTTokenHeaderProducer jwtTokenHeaderProducer) {
        this.hashAlgorithm = hashAlgorithm;
        this.mapper = mapper;
        this.clientProducer = clientProducer;
        this.jwtGenerator = jwtGenerator;
        this.jwtTokenHeaderProducer = jwtTokenHeaderProducer;
    }

    @Override
    public void checkClientByUsernameAndPassword(String username, String password) throws ClientNotFoundException {
        throw new ClientNotFoundException("Not supported", "Not supported");
    }

    @Override
    public Map<String, Client> clients() {
        return clients;
    }

    @Override
    public Object addClient(String IMEI, Client client) {
        if(IMEI != null)
            if(!(IMEI.isEmpty()))
                if(client != null) {
                    String jwtToken = jwtGenerator.generate(IMEI);

                    Client cl = clientProducer.getClientWithJWTTokenInstance(
                            client,
                            jwtTokenHeaderProducer.getJWTTokenHeaderDefaultInstance(mapper, jwtToken));

                    String IMEIHash = hashAlgorithm.isDigest(IMEI) ? IMEI : hashAlgorithm.digest(IMEI);
                    clients.put(IMEIHash, cl);

                    return jwtToken;
                }
        return "";
    }

    @Override
    public Object addClient(String IMEI, String username) {
        String IMEIHash = hashAlgorithm.digest(IMEI);
        Client client = clientProducer.getClientSimpleInstance(IMEIHash, username);
        return addClient(IMEI, client);
    }

    @Override
    public Client remove(String IMEIHash) {
        return hashAlgorithm.isDigest(IMEIHash)
                ? clients.remove(IMEIHash)
                : clients.remove(hashAlgorithm.digest(IMEIHash));
    }

    @Override
    public boolean contains(String IMEIHash) {
        return hashAlgorithm.isDigest(IMEIHash)
                ? clients.containsKey(IMEIHash)
                : clients.containsKey(hashAlgorithm.digest(IMEIHash));
    }
}
