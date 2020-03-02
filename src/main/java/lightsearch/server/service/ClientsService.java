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

import lightsearch.server.exception.ClientNotFoundException;

import java.util.Map;

public interface ClientsService<K,V> {
    void checkClientByUsernameAndPassword(String username, String password) throws ClientNotFoundException;
    Map<K,V> clients();
    Object addClient(K key, V value);
    Object addClient(K key, String username);
    V remove(K key);
    boolean contains(K key);
}
