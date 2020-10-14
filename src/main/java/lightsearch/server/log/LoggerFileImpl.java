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

import com.github.viise.papka.exception.NotFoundException;
import com.github.viise.papka.search.Search;
import com.github.viise.papka.search.SearchByStartWith;
import com.github.viise.papka.search.SearchFilesInSystem;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ViiSE
 */
@Component("loggerFile")
public class LoggerFileImpl implements LoggerFile {

    private final LogDirectory logDirectory;
    private final LightSearchChecker checker;

    public LoggerFileImpl(
            @Qualifier("logDirectory") LogDirectory logDirectory,
            LightSearchChecker checker) {
        this.logDirectory = logDirectory;
        this.checker = checker;
    }

    @Override
    public synchronized void write(String type, CurrentDateTime currentDateTime, String message) {
        if(!checker.isEmpty(type, message)) {
            try(OutputStream fout = new FileOutputStream(
                    logDirectory.name() + "log_" + currentDateTime.dateLog() + ".txt", true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                bw.write("[" + currentDateTime.dateTimeWithDot() + "] " + type + ": " + message);
                bw.newLine();
            } catch(IOException ex) {
                System.out.println("[" + currentDateTime.dateTimeWithDot() + "] Log file Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public List<File> readAll() {
        try {
            Search<List<File>, String> logSearch = new SearchFilesInSystem(logDirectory.name());
            return new SearchByStartWith<>(logSearch).answer("log_");
        } catch (NotFoundException ex) {
            return new ArrayList<>();
        }
    }
}
