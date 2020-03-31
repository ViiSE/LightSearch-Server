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
package lightsearch.server.log;

import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("loggerServerDefault")
@Scope("prototype")
public class LoggerServerDefaultImpl implements LoggerServer {

    private final LoggerFile loggerFile;
    private final LoggerWindow loggerWindow;
    private final CurrentDateTime currentDateTime;

    public LoggerServerDefaultImpl(
            @Qualifier("loggerFileDefault") LoggerFile loggerFile,
            LoggerWindow loggerWindow,
            CurrentDateTime currentDateTime) {
        this.loggerFile = loggerFile;
        this.loggerWindow = loggerWindow;
        this.currentDateTime = currentDateTime;
    }

    private void log(Class<?> clazz, LogMessageTypeEnum type, String message) {
        if(message != null) {
            if (!message.isEmpty()) {
                loggerFile.writeLogFile(type.stringValue(), currentDateTime,
                        String.format("{%s} : %s", clazz.getSimpleName(), message));
                loggerWindow.printLog(type.stringValue(), currentDateTime,
                        String.format("{%s} : %s", clazz.getSimpleName(), message));
            }
        }
    }

    @Override
    synchronized public void info(Class<?> clazz, String message) {
        log(clazz, LogMessageTypeEnum.INFO, message);
    }

    @Override
    synchronized public void error(Class<?> clazz, String message) {
        log(clazz, LogMessageTypeEnum.ERROR, message);
    }
}
