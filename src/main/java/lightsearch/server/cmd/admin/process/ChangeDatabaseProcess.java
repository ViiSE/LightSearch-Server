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
package lightsearch.server.cmd.admin.process;

import lightsearch.server.checker.Checker;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.entity.Property;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.properties.PropertiesChanger;
import lightsearch.server.properties.PropertyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("changeDatabaseProcess")
public class ChangeDatabaseProcess implements AdminProcess<AdminCommandResult> {

    private final AdminCommandResultProducer admCmdResProducer;
    private final PropertyCreator<String, AdminCommand> propertyCreator;
    private final PropertiesChanger<Void, Property<String>> propertiesChanger;
    private final Checker<AdminCommand> checker;

    public ChangeDatabaseProcess(
            AdminCommandResultProducer admCmdResProducer,
            @Qualifier("springDatasourcePropertyCreator") PropertyCreator<String, AdminCommand> propertyCreator,
            PropertiesChanger<Void, Property<String>> propertiesChanger,
            @Qualifier("commandCheckerAdminChangeDatabase") Checker<AdminCommand> checker) {
        this.admCmdResProducer = admCmdResProducer;
        this.propertyCreator = propertyCreator;
        this.propertiesChanger = propertiesChanger;
        this.checker = checker;
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            checker.check(command);
            Property<String> prop = propertyCreator.create(command);
            propertiesChanger.change(prop);
            return admCmdResProducer.getAdminCommandResultSimpleInstance(true, "Datasource has been changed.");
        } catch (PropertiesException | CheckerException ex) {
            return admCmdResProducer.getAdminCommandResultSimpleInstance(false, ex.getMessage());
        }
    }
}
