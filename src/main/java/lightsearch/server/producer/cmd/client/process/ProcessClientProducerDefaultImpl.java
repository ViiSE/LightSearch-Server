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

package lightsearch.server.producer.cmd.client.process;

import lightsearch.server.cmd.client.process.*;
import lightsearch.server.entity.ClientCommandResult;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("processorClientProducer")
public class ProcessClientProducerDefaultImpl implements ProcessClientProducer<ClientCommandResult> {

    private final ApplicationContext ctx;

    public ProcessClientProducerDefaultImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public ClientProcess<ClientCommandResult> getLoginProcessInstance() {
        return ctx.getBean("loginProcessWithLogger", LoginProcessWithLogger.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getSearchProcessInstance() {
        return ctx.getBean("searchProcessWithLogger", SearchProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getCancelSoftCheckProcessInstance() {
        return ctx.getBean("cancelSoftCheckProcessWithLogger", CancelSoftCheckProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getCloseSoftCheckProcessInstance() {
        return ctx.getBean("closeSoftCheckProcessWithLogger", CloseSoftCheckProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getConfirmSoftCheckProductsProcessInstance() {
        return ctx.getBean("confirmSoftCheckProductsProcessWithLogger", ConfirmSoftCheckProductsProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getOpenSoftCheckProcessInstance() {
        return ctx.getBean("openSoftCheckProcessWithLogger", OpenSoftCheckProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getBindProcessInstance() {
        return ctx.getBean("bindProcessWithLogger", BindProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getBindCheckProcessInstance() {
        return ctx.getBean("bindCheckProcessWithLogger", BindCheckProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getUnbindCheckProcessInstance() {
        return ctx.getBean("unbindCheckProcessWithLogger", UnbindCheckProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getUnbindProcessInstance() {
        return ctx.getBean("unbindProcessWithLogger", UnbindProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getSearchSoftCheckProcessInstance() {
        return ctx.getBean("searchSoftCheckProcessWithLogger", SearchSoftCheckProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getSkladListProcessInstance() {
        return ctx.getBean("skladListProcessWithLogger", SkladListProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getTKListProcessInstance() {
        return ctx.getBean("tkListProcessWithLogger", TKListProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getCheckAuthProcessInstance() {
        return ctx.getBean("checkAuthProcess", CheckAuthProcess.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getKeyProcessInstance() {
        return ctx.getBean("keyProcessWithLogger", KeyProcessWithLoggerImpl.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getLoginProcessEncryptedInstance() {
        return ctx.getBean("loginProcessEncrypted", LoginProcessEncrypted.class);
    }

    @Override
    public ClientProcess<ClientCommandResult> getUpdateSoftCheckProductsProcessInstance() {
        return ctx.getBean("updateSoftCheckProductsProcess", UpdateSoftCheckProductsProcess.class);
    }
}
