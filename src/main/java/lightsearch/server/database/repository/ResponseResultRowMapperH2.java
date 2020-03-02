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

import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.data.ClientCommandResultDTO;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.entity.ResponseResult;
import lightsearch.server.producer.entity.ClientCommandResultProducer;
import lightsearch.server.producer.entity.ResponseResultProducer;
import lightsearch.server.time.CurrentDateTimePattern;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component("responseResultRowMapperH2")
public class ResponseResultRowMapperH2 implements RowMapper<ResponseResult> {

    private final ResponseResultProducer responseResProducer;
    private final ClientCommandResultProducer clientCmdResProducer;
    private final ObjectMapper objectMapper;

    public ResponseResultRowMapperH2(
            ResponseResultProducer responseResProducer,
            ClientCommandResultProducer clientCmdResProducer,
            ObjectMapper objectMapper) {
        this.responseResProducer = responseResProducer;
        this.clientCmdResProducer = clientCmdResProducer;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseResult mapRow(ResultSet rs, int i) throws SQLException {
        try {
            LocalDateTime dateTime;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CurrentDateTimePattern.dateTimeInStandardFormWithMsForH2());
                dateTime = LocalDateTime.parse(rs.getString("DDOC"), formatter);
            } catch (DateTimeParseException ignore) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CurrentDateTimePattern.dateTimeInStandardFormWithMs());
                    dateTime = LocalDateTime.parse(rs.getString("DDOC"), formatter);
                } catch (DateTimeParseException ignored) {
                    dateTime = rs.getTimestamp("DDOC").toLocalDateTime();
                }
            }

            String resCmd = rs.getString("CMDOUT");

            ClientCommandResultDTO cmdResDTO = objectMapper.readValue(resCmd, ClientCommandResultDTO.class);
            ClientCommandResult cmdRes = clientCmdResProducer.getClientCommandResultFromDatabaseInstance(cmdResDTO);

            return responseResProducer.getResponseResultWithCommandResultInstance(
                    responseResProducer.getResponseResultSimpleInstance(dateTime),
                    cmdRes);
        } catch (IOException ex) {
            throw new SQLException(ex);
        }
    }
}
