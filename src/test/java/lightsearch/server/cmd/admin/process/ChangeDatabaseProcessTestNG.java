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

package lightsearch.server.cmd.admin.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import lightsearch.server.checker.Checker;
import lightsearch.server.checker.CommandCheckerAdminChangeDatabaseImpl;
import lightsearch.server.checker.LightSearchCheckerDefaultImpl;
import lightsearch.server.data.AdminChangeDatabaseCommandDTO;
import lightsearch.server.data.AdminCommandSimpleResultDTO;
import lightsearch.server.data.DatasourcePoolDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandDatabaseImpl;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.entity.Property;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.exception.WriterException;
import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.producer.entity.AdminCommandResultProducerTestImpl;
import lightsearch.server.producer.entity.PropertyProducerTestImpl;
import lightsearch.server.producer.properties.PropertiesWriterProducer;
import lightsearch.server.producer.properties.PropertiesWriterProducerTestImpl;
import lightsearch.server.properties.*;
import lightsearch.server.validator.PortValidator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.TestUtils;

import java.util.List;

import static test.message.TestMessage.*;

public class ChangeDatabaseProcessTestNG {

    private AdminProcess<AdminCommandResult> chDbProc;
    private List<String> oldProps;

    private PropertiesWriter<List<String>> propsWriter;
    PropertiesReader<List<String>> propsReader;

    @BeforeClass
    public void setUpClass() throws ReaderException {
        AdminCommandResultProducer admCmdResProducer = new AdminCommandResultProducerTestImpl();
        PropertyCreator<String, AdminCommand> propCr = new SpringDatasourcePropertyCreatorImpl(new PropertyProducerTestImpl());
        PropertiesDirectory propsDir = new ApplicationPropertiesDirectoryImpl(new CurrentServerDirectoryTestImpl());
        propsReader = new PropertiesFileReaderListOfStringImpl(propsDir);
        PropertiesWriterProducer propsWriterProducer = new PropertiesWriterProducerTestImpl();

        PropertiesChanger<Void, Property<String>> propCh = new PropertiesChangerFileImpl(
                propsDir,
                new PropertiesChangerLocalImpl(propsReader),
                propsWriterProducer);
        Checker<AdminCommand> cmdChecker = new CommandCheckerAdminChangeDatabaseImpl(
                new LightSearchCheckerDefaultImpl(),
                new PortValidator());

        chDbProc = new ChangeDatabaseProcess(
                admCmdResProducer,
                propCr,
                propCh,
                cmdChecker);

        oldProps = propsReader.read();

        propsWriter = new PropertiesFileWriterListOfStringImpl(propsDir.name(), false);

        testBegin(ChangeDatabaseProcess.class);
    }

    @Test
    public void apply() throws JsonProcessingException, ReaderException {
        testMethod("apply()");

        AdminCommand admCmd = new AdminCommandDatabaseImpl(new AdminChangeDatabaseCommandDTO() {{
            setHost("127.0.0.1");
            setPort(5432);
            setDbName("newDBName");
            setUsername("databaseUser");
            setPassword("5tRONGP455!");
            setAdditional("encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251&amp;defaultResultSetHoldable=true");
            setDbType("firebirdsql");
            setPool(new DatasourcePoolDTO() {{
                setMaxLifeTime(10000L);
                setMaximumPoolSize(10L);
                setIdleTimeout(3000L);
                setConnectionTimeout(5000L);
                setAutoCommit(true);
            }});
        }});
        AdminCommandResult cmdRes = chDbProc.apply(admCmd);
        AdminCommandSimpleResultDTO cmdResDTO = (AdminCommandSimpleResultDTO) cmdRes.formForSend();

        System.out.println("Response: ");
        System.out.println(TestUtils
                .objectMapperWithJavaTimeModule()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(cmdResDTO));
        System.out.println("--------------------------");

        System.out.println("Before props: ");
        oldProps.forEach(System.out::println);
        System.out.println("--------------------");

        System.out.println("After props: ");
        propsReader.read().forEach(System.out::println);
    }

    @AfterClass
    public void teardownClass() throws WriterException {
        System.out.println("Refresh application.properties...");
        propsWriter.write(oldProps);
        System.out.println("Done!");

        testEnd(ChangeDatabaseProcess.class);
    }
}
