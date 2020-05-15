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

package lightsearch.server.checker;

import lightsearch.server.data.ClientCommandDTO;
import lightsearch.server.data.ProductDTO;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.exception.CheckerException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("commandCheckerClientUpdateSoftCheckProducts")
public class CommandCheckerClientUpdateSoftCheckProductsImpl implements Checker<ClientCommand> {

    private final LightSearchChecker checker;

    public CommandCheckerClientUpdateSoftCheckProductsImpl(LightSearchChecker checker) {
        this.checker = checker;
    }

    @Override
    public void check(ClientCommand command) throws CheckerException {
        ClientCommandDTO commandDTO = (ClientCommandDTO) command.formForSend();

        List<ProductDTO> products = commandDTO.getData();

        if(checker.isEmpty(commandDTO.getUserIdentifier(), commandDTO.getCardCode()))
            throw new CheckerException("Неверный формат команды.", "UpdateSoftCheckProducts: wrong command format.");

        if(checker.isNull(commandDTO.getUserIdentifier(), commandDTO.getCardCode(), commandDTO.getData()))
            throw new CheckerException("Неверный формат команды.", "UpdateSoftCheckProducts: wrong command format.");

        if(products.size() == 0)
            throw new CheckerException("Товары для проверки отсутствуют!", "UpdateSoftCheckProducts: data is null.");

        long nullIdCount = products
                .stream()
                .filter(product -> product.getId() == null)
                .count();
        if(nullIdCount != 0)
            throw new CheckerException("У некоторых товаров отсутствует ID!", "UpdateSoftCheckProducts: product: id is null.");

        long emptyIdCount = products
                .stream()
                .filter(product -> product.getId().isEmpty())
                .count();
        if(emptyIdCount != 0)
            throw new CheckerException("У некоторых товаров отсутствует ID!", "UpdateSoftCheckProducts: product: id is empty.");
    }
}
