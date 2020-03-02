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

import lightsearch.server.exception.CheckerException;
import lightsearch.server.service.BlacklistService;
import org.springframework.stereotype.Component;

@Component("commandCheckerClientDefault")
public class CommandCheckerClientDefaultImpl implements Checker<String> {

    private final BlacklistService<String> blacklistService;

    public CommandCheckerClientDefaultImpl(BlacklistService<String> blacklistService) {
        this.blacklistService = blacklistService;
    }

    @Override
    public void check(String IMEI) throws CheckerException {
        if(blacklistService.contains(IMEI))
            throw new CheckerException("Извините, но вы находитесь в черном списке.",
                    "Client " + IMEI + " in the blacklist.");
    }
}
