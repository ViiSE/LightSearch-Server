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

package lightsearch.server.properties;

import lightsearch.server.data.AdminCommandDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDatabaseImpl;
import lightsearch.server.entity.Property;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.exception.WriterException;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.producer.entity.PropertyProducer;
import lightsearch.server.producer.entity.PropertyProducerTestImpl;
import lightsearch.server.producer.properties.PropertiesWriterProducer;
import lightsearch.server.producer.properties.PropertiesWriterProducerTestImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static test.message.TestMessage.*;

public class PropertiesChangerFileTestNG {

    private PropertiesChanger<Void, Property<String>> propChanger;
    private AdminCommand admCmd;
    private PropertyProducer propProducer;
    private PropertiesWriter<List<String>> propsWriter;
    private PropertiesReader<List<String>> propsReader;

    private List<String> oldProps;

    @BeforeClass
    @Parameters({"dbName", "dbUsername", "dbPass", "dbIp", "dbPort"})
    public void setUpClass(String dbName, String dbUsername, String dbPass, String ip, int port) throws ReaderException {
        AdminCommandDTO adminCommandDTO = new AdminCommandDTO();
        adminCommandDTO.setDbName(dbName);
        adminCommandDTO.setUsername(dbUsername);
        adminCommandDTO.setPassword(dbPass);
        adminCommandDTO.setIp(ip);
        adminCommandDTO.setPort(port);

        admCmd = new AdminCommandDatabaseImpl(adminCommandDTO);
        propProducer = new PropertyProducerTestImpl();
        CurrentServerDirectory curDir = new CurrentServerDirectoryTestImpl();
        PropertiesDirectory propDir = new ApplicationPropertiesDirectoryImpl(curDir);
        propsReader = new PropertiesFileReaderListOfStringImpl(propDir);

        PropertiesChanger<List<String>, Property<String>> propChangerLocal = new PropertiesChangerLocalImpl(propsReader);
        PropertiesWriterProducer propsWriterProducer = new PropertiesWriterProducerTestImpl();

        propChanger = new PropertiesChangerFileImpl(propDir, propChangerLocal, propsWriterProducer);
        propsWriter = propsWriterProducer.getPropertiesFileWriterListOfStringInstance(propDir.name(), false);
        oldProps = propsReader.read();
    }

    @Test
    public void change() {
        testBegin(PropertiesChangerFileImpl.class, "change()");

        Property<String> prop = new SpringDatasourcePropertyCreatorImpl(propProducer).create(admCmd);

        try {
            propChanger.change(prop);

            List<String> newProps = propsReader.read();
            assertEquals(newProps.size(), oldProps.size());
            for(int i = 0; i < newProps.size(); i++) {
                String oldPr = oldProps.get(i);
                String newPr = newProps.get(i);

                if(newPr.isEmpty())
                    continue;

                String prName = newPr.substring(0, newPr.indexOf("="));
                if(prName.contains("spring.datasource.url"))
                    assertNotEquals(oldPr, newPr);
                if(prName.contains("spring.datasource.username"))
                    assertNotEquals(oldPr, newPr);
                if(prName.contains("spring.datasource.password"))
                    assertNotEquals(oldPr, newPr);
            }

            System.out.println("Before properties: ");
            oldProps.forEach(System.out::println);
            System.out.println("+---------------------------------------------+");
            System.out.println("After properties: ");
            newProps.forEach(System.out::println);
            System.out.println("+---------------------------------------------+");

        } catch (PropertiesException | ReaderException e) {
            catchMessage(e);
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void shutdownClass() throws WriterException {
        System.out.println("Refresh application.properties...");
        propsWriter.write(oldProps);
        System.out.println("Done!");

        testEnd(PropertiesChangerFileImpl.class, "change()");
    }
}
