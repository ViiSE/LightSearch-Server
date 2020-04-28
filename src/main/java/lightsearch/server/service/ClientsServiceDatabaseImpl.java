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

import lightsearch.server.checker.Checker;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.entity.Client;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.ClientNotFoundException;
import lightsearch.server.producer.entity.ClientCommandProducer;
import lightsearch.server.security.HashAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service("clientsServiceDatabase")
public class ClientsServiceDatabaseImpl implements ClientsService<String, Client> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ClientsService<String, Client> clientsService;
    private final HashAlgorithm hashAlgorithm;
    private final ClientCommandProducer clientCommandProducer;
    private final Checker<ClientCommand> clientChecker;

    public ClientsServiceDatabaseImpl(
            @Qualifier("clientsServiceDefault") ClientsService<String, Client> clientsService,
            @Qualifier("hashAlgorithmsSHA512") HashAlgorithm hashAlgorithm,
            ClientCommandProducer clientCommandProducer,
            @Qualifier("clientCheckerDatabaseConnection") Checker<ClientCommand> clientChecker) {
        this.clientsService = clientsService;
        this.hashAlgorithm = hashAlgorithm;
        this.clientCommandProducer = clientCommandProducer;
        this.clientChecker = clientChecker;
    }

    @Override
    public void checkClientByUsernameAndPassword(String username, String password) throws ClientNotFoundException {
        try {
            String passHash = hashAlgorithm.digest(password);
            try {
                jdbcTemplate.queryForObject(
                        "SELECT id FROM LS_USERS WHERE username=? and passhash=?",
                        new Object[]{username.getBytes("windows-1251"), passHash.getBytes("windows-1251")},
                        Integer.class);
            } catch(EmptyResultDataAccessException ignore) {
                try {
                    clientChecker.check(clientCommandProducer.getClientCommandWithUsernameAndPasswordInstance(
                            clientCommandProducer.getClientCommandSimpleInstance(ClientCommands.LOGIN),
                            username,
                            password));
                    jdbcTemplate.update("INSERT INTO LS_USERS (username, passhash) VALUES (?,?)", username.getBytes("windows-1251"), passHash.getBytes("windows-1251"));
                } catch (CheckerException ex) {
                    throw new ClientNotFoundException(ex.getMessage(), ex.getLogMessage());
                }
            }
        } catch (UnsupportedEncodingException ignore) {}
    }

    @Override
    public Map<String, Client> clients() {
        return clientsService.clients();
    }

    @Override
    public Object addClient(String IMEI, Client client) {
        return clientsService.addClient(IMEI, client);
    }

    @Override
    public Object addClient(String IMEI, String username) {
        return clientsService.addClient(IMEI, username);
    }

    @Override
    public Client client(String IMEI) {
        return clientsService.client(IMEI);
    }

    @Override
    public Client remove(String IMEIHash) {
        return clientsService.remove(IMEIHash);
    }

    @Override
    public boolean contains(String IMEIHash) {
        return clientsService.contains(IMEIHash);
    }
}
