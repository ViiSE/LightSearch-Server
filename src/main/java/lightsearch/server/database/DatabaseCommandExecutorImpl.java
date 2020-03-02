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

package lightsearch.server.database;

import lightsearch.server.data.ResponseResultDTO;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.repository.LSRequestRepository;
import lightsearch.server.database.repository.LSResponseRepository;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.entity.ResponseResult;
import lightsearch.server.exception.CommandExecutorException;
import lightsearch.server.exception.RepositoryException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("databaseCommandExecutor")
public class DatabaseCommandExecutorImpl implements CommandExecutor<ClientCommandResult, DatabaseCommandMessage> {

    private final DatabaseRecordIdentifier<String> identifier;
    private final LSRequestRepository<String> lsRequestRepository;
    private final LSResponseRepository<String> lsResponseRepository;

    public DatabaseCommandExecutorImpl(
            @Qualifier("databaseRecordIdentifierHash") DatabaseRecordIdentifier<String> identifier,
            @Qualifier("lsRequestRepositoryHashWindows1251") LSRequestRepository<String> lsRequestRepository,
            @Qualifier("lsResponseRepositoryHashFirebird") LSResponseRepository<String> lsResponseRepository) {
        this.identifier = identifier;
        this.lsRequestRepository = lsRequestRepository;
        this.lsResponseRepository = lsResponseRepository;
    }

    @Override
    public ClientCommandResult exec(DatabaseCommandMessage message) throws CommandExecutorException {
        try {
            String hash = identifier.next();

            lsRequestRepository.write(hash, message);
            ResponseResult result = lsResponseRepository.read(hash);
            ResponseResultDTO resultDTO = (ResponseResultDTO) result.formForSend();
            return resultDTO.getCmdRes();
        } catch (RepositoryException ex) {
            throw new CommandExecutorException(ex);
        }
    }
}
