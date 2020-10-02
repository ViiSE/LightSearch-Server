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

import lightsearch.server.entity.Client;
import lightsearch.server.entity.ClientSimpleImpl;
import lightsearch.server.exception.ClientNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsServiceTestImpl implements ClientsService<String, Client, List<Client>> {

    private int i = 1;
    private final Map<String, Client> clientsMap = new HashMap<>();

    public ClientsServiceTestImpl() {}

    public ClientsServiceTestImpl(List<Client> clients) {
        for(Client client : clients) {
            clientsMap.put("id" + i, client);
            ++i;
        }
    }

    @Override
    public void checkClientByUsernameAndPassword(String username, String password) throws ClientNotFoundException {
        throw new ClientNotFoundException("Not supported", "Not supported");
    }

    @Override
    public Object addClient(String IMEI, Client client) {
        if(IMEI != null)
            if(!(IMEI.isEmpty()))
                if(client != null) {
                        clientsMap.put("id" + i, client);
                        ++i;
                }
        return IMEI;
    }

    @Override
    public Object addClient(String IMEI, String username) {
        return addClient(IMEI, new ClientSimpleImpl(IMEI, username));
    }

    @Override
    public Client client(String IMEI) {
        return clientsMap.get(IMEI);
    }

    @Override
    public Client remove(String key) {
        return clientsMap.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return clientsMap.containsKey(key);
    }

    @Override
    public List<Client> formForSend() {
        return new ArrayList<>(clientsMap.values());
    }
}
