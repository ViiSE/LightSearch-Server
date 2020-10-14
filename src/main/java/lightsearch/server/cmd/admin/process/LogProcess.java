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

import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.log.LoggerFile;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("logProcess")
public class LogProcess implements AdminProcess<AdminCommandResult> {

    private final LoggerFile loggerFile;
    private final AdminCommandResultProducer resultProducer;

    public LogProcess(@Qualifier("loggerFile") LoggerFile loggerFile, AdminCommandResultProducer resultProducer) {
        this.loggerFile = loggerFile;
        this.resultProducer = resultProducer;
    }

    @Override
    public AdminCommandResult apply(AdminCommand ignored) {
        Map<String, List<String>> logMap = new HashMap<>();

        List<File> logFiles = loggerFile.readAll();
        if(!logFiles.isEmpty()) {
            for(File logFile: logFiles) {
                try {
                    List<String> logs = Files.readAllLines(Paths.get(logFile.toURI()), StandardCharsets.UTF_8);
                    String date = logFile.getName().substring(4).replaceFirst("[.][^.]+$", ""); // log_ has a 4 symbol and removed extension
                    logMap.put(date, logs);
                } catch (IOException ignore) {}
            }
        }

        return resultProducer.getAdminCommandResultWithLogsInstance(
                resultProducer.getAdminCommandResultSimpleInstance(
                        true,
                        "OK"),
                logMap);
    }
}
