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
import lightsearch.server.data.AdminCommandResultWithHashIMEIDTO;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandHashIMEIImpl;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.producer.entity.AdminCommandResultProducer;
import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.service.BlacklistService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("hashIMEIProcess")
public class HashIMEIProcess implements AdminProcess<AdminCommandResult> {

    private final HashAlgorithm hashAlgorithm;
    private final Checker<AdminCommand> checker;
    private final AdminCommandResultProducer adminCommandResultProducer;

    public HashIMEIProcess(
            @Qualifier("hashAlgorithmsSHA512") HashAlgorithm hashAlgorithm,
            @Qualifier("commandCheckerAdminHashIMEI") Checker<AdminCommand> checker,
            AdminCommandResultProducer adminCommandResultProducer) {
        this.hashAlgorithm = hashAlgorithm;
        this.checker = checker;
        this.adminCommandResultProducer = adminCommandResultProducer;
    }

    @Override
    public AdminCommandResult apply(AdminCommand admCmd) {
        try {
            checker.check(admCmd);
            String IMEI = ((AdminCommandHashIMEIImpl) admCmd).IMEI();
            return adminCommandResultProducer
                    .getAdminCommandResultHashIMEIInstance(
                            adminCommandResultProducer.getAdminCommandResultSimpleInstance(true, "OK"),
                            hashAlgorithm.digest(IMEI));
        } catch (CheckerException e) {
            return adminCommandResultProducer
                    .getAdminCommandResultHashIMEIInstance(
                            adminCommandResultProducer.getAdminCommandResultSimpleInstance(false, e.getMessage()),
                            "");
        }
    }
}
