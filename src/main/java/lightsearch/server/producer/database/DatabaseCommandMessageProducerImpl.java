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

package lightsearch.server.producer.database;

import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.entity.ClientCommand;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("databaseCommandMessageProducer")
public class DatabaseCommandMessageProducerImpl implements DatabaseCommandMessageProducer {

    private final ApplicationContext ctx;

    public DatabaseCommandMessageProducerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageConnectionWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageSearchDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageSearchWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageOpenSoftCheckWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageCancelSoftCheckWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageCloseSoftCheckWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageConfirmSoftCheckProductsWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageBindCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageBindCheckWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageBindDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageBindWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageUnbindCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageUnbindCheckWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageUnbindDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageUnbindWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageSearchSoftCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageSearchSoftCheckWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageTKListDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageTKListWindowsJSON", command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageSkladListDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean("databaseCommandMessageSkladListWindowsJSON", command);
    }
}
