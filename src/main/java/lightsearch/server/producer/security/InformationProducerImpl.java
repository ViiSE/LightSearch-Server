/*
 *    Copyright 2020 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.server.producer.security;

import lightsearch.server.security.DecryptedInformationRSAImpl;
import lightsearch.server.security.EncryptedInformationImpl;
import lightsearch.server.security.Information;
import lightsearch.server.security.Keys;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;

@Service("informationProducer")
public class InformationProducerImpl implements InformationProducer {

    private final ApplicationContext ctx;

    public InformationProducerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Information<String> getEncryptedInformationInstance(String rawData) {
        return (EncryptedInformationImpl) ctx.getBean("encryptedInformation", rawData);
    }

    @Override
    public Information<String> getDecryptedInformationRSAInstance(Information<String> encInfo, Keys<PublicKey, PrivateKey> keys) {
        return (DecryptedInformationRSAImpl) ctx.getBean("decryptedInformationRSA", encInfo, keys);
    }
}
