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
import lightsearch.server.entity.AdminCommandAddBlacklistImpl;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.entity.Client;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.initialization.BlacklistDirectory;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.service.BlacklistService;
import lightsearch.server.service.ClientsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author ViiSE
 */
@Component("addBlacklistProcess")
public class AddBlacklistProcess implements AdminProcess<AdminCommandResult> {

    private final ClientsService<String, Client> clientsService;
    private final BlacklistService<String> blacklistService;
    private final Checker<AdminCommand> checker;
    private final BlacklistDirectory blacklistDirectory;
    private final AdminCommandResultProducer admCmdResProducer;

    public AddBlacklistProcess(
            @Qualifier("clientsServiceDatabase") ClientsService<String, Client> clientsService,
            BlacklistService<String> blacklistService,
            @Qualifier("commandCheckerAdminAddBlacklist") Checker<AdminCommand> checker,
            BlacklistDirectory blacklistDirectory,
            AdminCommandResultProducer admCmdResProducer) {
        this.clientsService = clientsService;
        this.blacklistService = blacklistService;
        this.checker = checker;
        this.blacklistDirectory = blacklistDirectory;
        this.admCmdResProducer = admCmdResProducer;
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            checker.check(command);

            AdminCommandAddBlacklistImpl cmd = (AdminCommandAddBlacklistImpl) command;

            blacklistService.add(cmd.IMEI());
            try (FileOutputStream fout = new FileOutputStream(blacklistDirectory.name(), true);
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                bw.write(cmd.IMEI());
                bw.newLine();
            } catch (IOException ex) {
                blacklistService.remove(cmd.IMEI());
                return admCmdResProducer.getAdminCommandResultSimpleInstance(
                        false,
                        "Cannot add client to the blacklist. Exception: " + ex.getMessage());
            }

            clientsService.clients().remove(cmd.IMEI());
            return admCmdResProducer.getAdminCommandResultSimpleInstance(
                    true,
                    "Client " + cmd.IMEI() + " has been added to the blacklist.");
        } catch (CheckerException ex) {
            return admCmdResProducer.getAdminCommandResultSimpleInstance(
                    false,
                    ex.getMessage());

        }
    }
}
