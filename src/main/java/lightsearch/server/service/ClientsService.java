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

import lightsearch.server.entity.Sendable;
import lightsearch.server.exception.ClientNotFoundException;

public interface ClientsService<K,V,S> extends Sendable<S> {
    void checkClientByUsernameAndPassword(String username, String password) throws ClientNotFoundException;
    Object addClient(K key, V value);
    Object addClient(K key, String username);
    V client(K key);
    V remove(K key);
    boolean contains(K key);
}
