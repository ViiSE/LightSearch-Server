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

package lightsearch.server.database.repository;

import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.exception.RepositoryException;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("lsRequestRepositoryH2")
public class LSRequestRepositoryH2Impl implements LSRequestRepository<Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final LSRequestRowChecker<String> rowChecker;
    private final CurrentDateTime currentDateTime;

    public LSRequestRepositoryH2Impl(LSRequestRowChecker<String> rowChecker, CurrentDateTime currentDateTime) {
        this.rowChecker = rowChecker;
        this.currentDateTime = currentDateTime;
    }

    @Override
    public void write(Long lsCode, DatabaseCommandMessage message) throws RepositoryException {
        try {
            String ddoc   = currentDateTime.dateTimeInStandardFormat();
            String cmdin  = message.asString();

            if(rowChecker.isExist(lsCode.toString())) {
                jdbcTemplate.setQueryTimeout(30);
                jdbcTemplate.update("INSERT INTO LS_REQUEST (LSCODE, DDOC, CMDIN, STATE) VALUES (?,?,?,?)",
                        lsCode, ddoc, cmdin, true);
            } else
                throw new RepositoryException(
                        "Запись с данным LSCODE уже существует!",
                        "Record with LSCODE = " + lsCode + " already exist.");
        } catch (QueryTimeoutException ex) {
            throw new RepositoryException(
                    "Время ожидания запроса истекло.",
                    "Request timeout.");
        }
    }
}
