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
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.exception.CheckerException;
import org.springframework.stereotype.Component;

@Component("commandCheckerClientCloseSoftCheck")
public class CommandCheckerClientCloseSoftCheckImpl implements Checker<ClientCommand> {

    private final LightSearchChecker checker;

    public CommandCheckerClientCloseSoftCheckImpl(LightSearchChecker checker) {
        this.checker = checker;
    }

    @Override
    public void check(ClientCommand command) throws CheckerException {
        ClientCommandDTO cmdDTO = (ClientCommandDTO) command.formForSend();

        if(checker.isEmpty(cmdDTO.getUserIdentifier(), cmdDTO.getCardCode(), cmdDTO.getDelivery()))
            throw new CheckerException("Неверный формат команды.", "CloseSoftCheck: wrong command format.");

        if(checker.isNull(cmdDTO.getUserIdentifier(), cmdDTO.getCardCode(), cmdDTO.getDelivery()))
            throw new CheckerException("Неверный формат команды.", "CloseSoftCheck: wrong command format.");
    }
}
