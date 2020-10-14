/*
 *    Copyright 2020 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package lightsearch.server.cmd.admin.process;

import lightsearch.server.constants.DatasourceConstants;
import lightsearch.server.data.AdminCommandResultWithDatasourceDTO;
import lightsearch.server.data.DatasourcePoolDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.properties.PropertiesReader;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("DatasourceProcess")
public class DatasourceProcess implements AdminProcess<AdminCommandResult> {

    private final AdminCommandResultProducer admCmdResProducer;
    private final PropertiesReader<Map<String, Object>> propertiesReader;

    public DatasourceProcess(AdminCommandResultProducer admCmdResProducer,
                             PropertiesReader<Map<String, Object>> propertiesReader) {
        this.admCmdResProducer = admCmdResProducer;
        this.propertiesReader = propertiesReader;
    }

    @Override
    public AdminCommandResult apply(AdminCommand ignored) {
        try {
            Map<String, Object> props = propertiesReader.read();
            AdminCommandResultWithDatasourceDTO dto = new AdminCommandResultWithDatasourceDTO(true, "OK");
            dto.setUsername(String.valueOf(props.getOrDefault(DatasourceConstants.USERNAME, "")));
            dto.setPassword(String.valueOf(props.getOrDefault(DatasourceConstants.PASSWORD, "")));
            dto.setHost(String.valueOf(props.getOrDefault(DatasourceConstants.IP, "")));
            dto.setPort(Integer.parseInt(String.valueOf(props.getOrDefault(DatasourceConstants.PORT, 5432))));
            dto.setDbName(String.valueOf(props.getOrDefault(DatasourceConstants.DB_NAME, "")));
            dto.setDbType(String.valueOf(props.getOrDefault(DatasourceConstants.DB_TYPE, "")));
            dto.setAdditional(String.valueOf(props.getOrDefault(DatasourceConstants.ADDITIONAL, "")));
            dto.setDriverClassName(String.valueOf(props.getOrDefault(DatasourceConstants.DRIVER_CLASS_NAME, "")));
            dto.setScriptEncoding(String.valueOf(props.getOrDefault(DatasourceConstants.SCRIPT_ENCODING, "")));

            DatasourcePoolDTO poolDto = new DatasourcePoolDTO();
            poolDto.setAutoCommit(Boolean.parseBoolean(String.valueOf(props.getOrDefault(DatasourceConstants.POOL_AUTO_COMMIT, true))));
            poolDto.setConnectionTimeout(Long.parseLong(String.valueOf(props.getOrDefault(DatasourceConstants.POOL_CONNECTION_TIMEOUT, 30000))));
            poolDto.setIdleTimeout(Long.parseLong(String.valueOf(props.getOrDefault(DatasourceConstants.POOL_IDLE_TIMEOUT, 600000))));
            poolDto.setMaxLifeTime(Long.parseLong(String.valueOf(props.getOrDefault(DatasourceConstants.POOL_MAX_LIFE_TIME, 1800000))));
            poolDto.setMaximumPoolSize(Long.parseLong(String.valueOf(props.getOrDefault(DatasourceConstants.MAXIMUM_POOL_SIZE, 10))));
            dto.setPool(poolDto);

            return admCmdResProducer.getAdminCommandResultWithDatasourceInstance(dto);
        } catch (ReaderException ex) {
            return admCmdResProducer.getAdminCommandResultWithDatasourceInstance(
                    new AdminCommandResultWithDatasourceDTO(false, ex.getMessage()));
        }
    }
}
