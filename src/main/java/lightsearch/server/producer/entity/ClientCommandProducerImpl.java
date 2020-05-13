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

import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientCommandProducer")
public class ClientCommandProducerImpl implements ClientCommandProducer {

    private final ApplicationContext ctx;

    public ClientCommandProducerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public ClientCommand getClientCommandSimpleInstance(String commandName) {
        return (ClientCommand) ctx.getBean("clientCommandSimple", commandName);
    }

    @Override
    public ClientCommand getClientCommandWithIMEIInstance(ClientCommand command, String IMEI) {
        return (ClientCommand) ctx.getBean("clientCommandWithIMEI", command, IMEI);
    }

    @Override
    public ClientCommand getClientCommandWithBarcodeInstance(ClientCommand command, String barcode) {
        return (ClientCommand) ctx.getBean("clientCommandWithBarcode", command, barcode);
    }

    @Override
    public ClientCommand getClientCommandWithCardCodeInstance(ClientCommand command, String cardCode) {
        return (ClientCommand) ctx.getBean("clientCommandWithCardCode", command, cardCode);
    }

    @Override
    public ClientCommand getClientCommandWithDeliveryInstance(ClientCommand command, String delivery) {
        return (ClientCommand) ctx.getBean("clientCommandWithDelivery", command, delivery);
    }

    @Override
    public ClientCommand getClientCommandWithIpInstance(ClientCommand command, String ip) {
        return (ClientCommand) ctx.getBean("clientCommandWithIp", command, ip);
    }

    @Override
    public ClientCommand getClientCommandWithModelInstance(ClientCommand command, String model) {
        return (ClientCommand) ctx.getBean("clientCommandWithModel", command, model);
    }

    @Override
    public ClientCommand getClientCommandWithOsInstance(ClientCommand command, String os) {
        return (ClientCommand) ctx.getBean("clientCommandWithOs", command, os);
    }

    @Override
    public ClientCommand getClientCommandWithProductsInstance(ClientCommand command, List<Product> products) {
        return (ClientCommand) ctx.getBean("clientCommandWithProducts", command, products);
    }

    @Override
    public ClientCommand getClientCommandWithSkladInstance(ClientCommand command, String sklad) {
        return (ClientCommand) ctx.getBean("clientCommandWithSklad", command, sklad);
    }

    @Override
    public ClientCommand getClientCommandWithTKInstance(ClientCommand command, String TK) {
        return (ClientCommand) ctx.getBean("clientCommandWithTK", command, TK);
    }

    @Override
    public ClientCommand getClientCommandWithUserIdentifierInstance(ClientCommand command, String userIdentifier) {
        return (ClientCommand) ctx.getBean("clientCommandWithUserIdentifier", command, userIdentifier);
    }

    @Override
    public ClientCommand getClientCommandWithUsernameAndPasswordInstance(ClientCommand command, String username, String password) {
        return (ClientCommand) ctx.getBean("clientCommandWithUsernameAndPassword", command, username, password);
    }

    @Override
    public ClientCommand getClientCommandWithFactoryBarcodeInstance(ClientCommand command, String factoryBarcode) {
        return (ClientCommand) ctx.getBean("clientCommandWithFactoryBarcode", command, factoryBarcode);
    }

    @Override
    public ClientCommand getClientCommandWithCheckEAN13Instance(ClientCommand command, boolean checkEan13) {
        return (ClientCommand) ctx.getBean("clientCommandWithCheckEAN13", command, checkEan13);
    }

    @Override
    public ClientCommand getClientCommandWithUsernameInstance(ClientCommand command, String username) {
        return (ClientCommand) ctx.getBean("clientCommandWithUsername", command, username);
    }

    @Override
    public ClientCommand getClientCommandWithEncryptedDataInstance(ClientCommand command, String encData) {
        return (ClientCommand) ctx.getBean("clientCommandWithEncryptedData", command, encData);
    }
}
