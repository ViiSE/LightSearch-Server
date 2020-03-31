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

import lightsearch.server.data.ResponseResultDTO;
import lightsearch.server.entity.ResponseResult;
import lightsearch.server.exception.RepositoryException;
import lightsearch.server.producer.database.RowMapperProducer;
import lightsearch.server.producer.time.DateTimeComparatorProducer;
import lightsearch.server.time.CurrentDateTimePattern;
import lightsearch.server.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository("lsResponseRepositoryH2")
public class LSResponseRepositoryH2Impl implements LSResponseRepository<String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final DateTimeComparatorProducer dateTimeComparatorProducer;
    private final RowMapperProducer rowMapperProducer;

    public LSResponseRepositoryH2Impl(
            DateTimeComparatorProducer dateTimeComparatorProducer,
            @Qualifier("rowMapperProducerH2") RowMapperProducer rowMapperProducer) {
        this.dateTimeComparatorProducer = dateTimeComparatorProducer;
        this.rowMapperProducer = rowMapperProducer;
    }

    @Override
    public ResponseResult read(String lsCode) throws RepositoryException {
        String pattern = CurrentDateTimePattern.dateTimeInStandardFormWithMs();

        LocalDateTime dateTimeStop = LocalDateTime.now().plusSeconds(30);
        DateTimeComparator dateTimeComparator = dateTimeComparatorProducer.getDateTimeComparatorDefaultInstance(pattern);

        jdbcTemplate.setQueryTimeout(30);
        try {
            while (dateTimeStop.isAfter(LocalDateTime.now())) {
                try {
                    ResponseResult result = jdbcTemplate.queryForObject(
                            "SELECT LSCODE, DDOC, CMDOUT, STATE FROM LS_RESPONSE WHERE (LSCODE = ?) AND (STATE = ?)",
                            new Object[]{lsCode, false},
                            rowMapperProducer.getResponseResultRowMapperInstance());
                    LocalDateTime nowMax = LocalDate.now().atTime(LocalTime.MAX);
                    LocalDateTime nowMin = LocalDate.now().atStartOfDay();
                    if(result != null) {
                        ResponseResultDTO resultDTO = (ResponseResultDTO) result.formForSend();
                        if (resultDTO != null)
                            if (dateTimeComparator.isBefore(resultDTO.getDateTime(), nowMax)
                                    && dateTimeComparator.isAfter(resultDTO.getDateTime(), nowMin))
                                return result;
                    }
                } catch(DataAccessException ex) {
                    if(ex.getMessage() != null)
                        if(!ex.getMessage().contains("Incorrect result size"))
                            throw new RepositoryException(
                                    "Произошла ошибка на сервере. Попробуйте позже.", ex.getMessage());
                }
            }
            throw new RepositoryException("Время ожидания запроса истекло.", "Request timeout.");
        } catch (QueryTimeoutException ex) {
            throw new RepositoryException("Время ожидания запроса истекло.", "Request timeout");
        } catch (DataAccessException ex) {
            throw new RepositoryException(
                    "Произошла ошибка на сервере. Попробуйте позже.", ex.getMessage());
        }
    }

    @Override
    public void update(String lsCode) throws RepositoryException {
        try {
            jdbcTemplate.setQueryTimeout(30);
            jdbcTemplate.update("UPDATE LS_RESPONSE SET STATE = ? WHERE LSCODE = ?", true, lsCode);
        } catch (QueryTimeoutException ex) {
            throw new RepositoryException("Время ожидания запроса истекло.", "Request timeout.");
        }
    }
}
