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

package lightsearch.server.cmd.client.process;

import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.producer.entity.ClientCommandResultProducer;
import lightsearch.server.security.Keys;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Component("keyRsaProcess")
public class KeyRSAProcess implements ClientProcess<ClientCommandResult> {

    private final ClientCommandResultProducer resultProducer;
    private final Keys<PublicKey, PrivateKey> keys;

    public KeyRSAProcess(ClientCommandResultProducer resultProducer, Keys<PublicKey, PrivateKey> keys) {
        this.resultProducer = resultProducer;
        this.keys = keys;
    }

    @Override
    public ClientCommandResult apply(ClientCommand ignore) {
        return resultProducer.getClientCommandResultKeyInstance(
                Base64.getEncoder().encodeToString(keys.publicKey().getEncoded()),
                "RSA",
                "RSA/ECB/OAEPWithSHA1AndMGF1Padding");
    }
}
