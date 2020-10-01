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

package lightsearch.server.producer.entity;

import lightsearch.server.data.AdminCommandDTO;
import lightsearch.server.entity.AdminCommand;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminCommandProducer")
public class AdminCommandProducerImpl implements AdminCommandProducer {

    private final ApplicationContext ctx;

    public AdminCommandProducerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public AdminCommand getAdminCommandAddBlacklistInstance(String IMEI) {
        return (AdminCommand) ctx.getBean("adminCommandAddBlacklist", IMEI);
    }

    @Override
    public AdminCommand getAdminCommandClientTimeoutInstance(int timeout) {
        return (AdminCommand) ctx.getBean("adminCommandClientTimeout", timeout);
    }

    @Override
    public AdminCommand getAdminCommandDatabaseInstance(AdminCommandDTO adminCommandDTO) {
        return (AdminCommand) ctx.getBean("adminCommandDatabase", adminCommandDTO);
    }

    @Override
    public AdminCommand getAdminCommandDelBlacklistInstance(String IMEI) {
        return (AdminCommand) ctx.getBean("adminCommandDelBlacklist", IMEI);
    }

    @Override
    public AdminCommand getAdminCommandDelBlacklistInstance(List<String> IMEIList) {
        return (AdminCommand) ctx.getBean("adminCommandDelBlacklistList", IMEIList);
    }

    @Override
    public AdminCommand getAdminCommandClientKickInstance(String IMEI) {
        return (AdminCommand) ctx.getBean("adminCommandKick", IMEI);
    }
}
