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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static lightsearch.server.log.LogMessageTypeEnum.ERROR;
import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@Component("loggerWindowSlf4j")
public class LoggerWindowSlf4jImpl implements LoggerWindow {

    private static final Logger loggerWindow = LoggerFactory.getLogger(LoggerServer.class);

    @Override
    public void printLog(String type, CurrentDateTime currentDateTime, String message) {
        if(type.equals(ERROR.stringValue()))
            loggerWindow.error(message);
        else if(type.equals(INFO.stringValue()))
            loggerWindow.info(message);
    }
}
