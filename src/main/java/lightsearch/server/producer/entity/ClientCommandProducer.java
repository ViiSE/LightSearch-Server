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

import java.util.List;

public interface ClientCommandProducer {
    ClientCommand getClientCommandSimpleInstance(String commandName);
    ClientCommand getClientCommandWithIMEIInstance(ClientCommand command, String IMEI);
    ClientCommand getClientCommandWithBarcodeInstance(ClientCommand command, String barcode);
    ClientCommand getClientCommandWithCardCodeInstance(ClientCommand command, String cardCode);
    ClientCommand getClientCommandWithDeliveryInstance(ClientCommand command, String delivery);
    ClientCommand getClientCommandWithIpInstance(ClientCommand command, String ip);
    ClientCommand getClientCommandWithModelInstance(ClientCommand command, String model);
    ClientCommand getClientCommandWithOsInstance(ClientCommand command, String os);
    ClientCommand getClientCommandWithProductsInstance(ClientCommand command, List<Product> products);
    ClientCommand getClientCommandWithSkladInstance(ClientCommand command, String sklad);
    ClientCommand getClientCommandWithTKInstance(ClientCommand command, String TK);
    ClientCommand getClientCommandWithUserIdentifierInstance(ClientCommand command, String userIdentifier);
    ClientCommand getClientCommandWithUsernameAndPasswordInstance(ClientCommand command, String username, String password);
    ClientCommand getClientCommandWithFactoryBarcodeInstance(ClientCommand command, String factoryBarcode);
    ClientCommand getClientCommandWithCheckEAN13Instance(ClientCommand command, boolean checkEan13);
    ClientCommand getClientCommandWithUsernameInstance(ClientCommand command, String username);
}
