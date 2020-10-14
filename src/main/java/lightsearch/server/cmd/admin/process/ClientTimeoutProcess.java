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
import lightsearch.server.entity.Property;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.properties.PropertiesReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("clientTimeoutProcess")
public class ClientTimeoutProcess implements AdminProcess<AdminCommandResult> {

    private final PropertiesReader<Property<Integer>> propReaderJwtValidDay;
    private final AdminCommandResultProducer admCmdResProducer;

    public ClientTimeoutProcess(
            @Qualifier("propertiesReaderJwtValidDay") PropertiesReader<Property<Integer>> propReaderJwtValidDay,
            AdminCommandResultProducer admCmdResProducer) {
        this.propReaderJwtValidDay = propReaderJwtValidDay;
        this.admCmdResProducer = admCmdResProducer;
    }

    @Override
    public AdminCommandResult apply(AdminCommand ignored) {
        try {
            return admCmdResProducer.getAdminCommandResultWithClientTimeoutInstance(
                    admCmdResProducer.getAdminCommandResultSimpleInstance(
                            true,
                            "OK"),
                    propReaderJwtValidDay.read().as());
        } catch (ReaderException ex) {
            return admCmdResProducer.getAdminCommandResultWithClientTimeoutInstance(
                    admCmdResProducer.getAdminCommandResultSimpleInstance(
                            false,
                            ex.getMessage()),
                    0L);
        }
    }
}
