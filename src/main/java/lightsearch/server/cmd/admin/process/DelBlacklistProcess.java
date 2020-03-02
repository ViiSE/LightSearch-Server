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
import lightsearch.server.entity.AdminCommandDelBlacklistImpl;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.initialization.BlacklistDirectory;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.service.BlacklistService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 *
 * @author ViiSE
 */
@Component("delBlacklistProcess")
public class DelBlacklistProcess implements AdminProcess<AdminCommandResult> {

    private final BlacklistDirectory blacklistDirectory;
    private final BlacklistService<String> blacklistService;
    private final Checker<AdminCommand> checker;
    private final AdminCommandResultProducer admCmdResultProducer;

    public DelBlacklistProcess(
            BlacklistDirectory blacklistDirectory,
            BlacklistService<String> blacklistService,
            @Qualifier("commandCheckerAdminDelBlacklist") Checker<AdminCommand> checker,
            AdminCommandResultProducer admCmdResultProducer) {
        this.blacklistDirectory = blacklistDirectory;
        this.checker = checker;
        this.blacklistService = blacklistService;
        this.admCmdResultProducer = admCmdResultProducer;
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            checker.check(command);
            AdminCommandDelBlacklistImpl cmd = (AdminCommandDelBlacklistImpl) command;

            blacklistService.remove(cmd.IMEI());
            try (FileOutputStream fout = new FileOutputStream(blacklistDirectory.name(), false);
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                List<String> blacklist = blacklistService.blacklist();
                for (String IMEI : blacklist) {
                    bw.write(IMEI);
                    bw.newLine();
                }
            } catch (IOException ex) {
                blacklistService.add(cmd.IMEI());
                return admCmdResultProducer.getAdminCommandResultSimpleInstance(
                        false,
                        "Cannot removed client from the blacklist. Exception: " + ex.getMessage());
            }

            return admCmdResultProducer.getAdminCommandResultSimpleInstance(
                    true,
                    "Client " + cmd.IMEI() + " has been removed from the blacklist.");
        } catch (CheckerException ex) {
            return admCmdResultProducer.getAdminCommandResultSimpleInstance(
                    false,
                    ex.getMessage());
        }
    }
}
