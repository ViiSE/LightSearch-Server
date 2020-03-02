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

package lightsearch.server.properties;

import lightsearch.server.exception.WriterException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Component("propertiesFileWriterListOfString")
@Scope("prototype")
public class PropertiesFileWriterListOfStringImpl implements PropertiesWriter<List<String>> {

    private final String propertiesDirectory;
    private final boolean append;

    public PropertiesFileWriterListOfStringImpl(String propsDir, boolean append) {
        this.propertiesDirectory = propsDir;
        this.append = append;
    }

    @Override
    public void write(List<String> props) throws WriterException {
        try (FileOutputStream fout = new FileOutputStream(propertiesDirectory, append);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
            for (String prop : props)
                writeln(bw, prop);
        } catch (IOException ex) {
            throw new WriterException("Не удалось записать новые параметры базы данных. Сообщение: " + ex.getMessage(),
                    "Cannot write datasource properties. Exception: " + ex.getMessage());
        }
    }

    private void writeln(BufferedWriter bw, Object value) throws IOException {
        bw.write(value.toString());
        bw.newLine();
    }
}
