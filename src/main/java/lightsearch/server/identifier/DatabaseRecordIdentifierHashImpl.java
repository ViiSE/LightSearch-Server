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
package lightsearch.server.identifier;

import lightsearch.server.security.HashAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 *
 * @author ViiSE
 */
@Component("databaseRecordIdentifierHash")
public class DatabaseRecordIdentifierHashImpl implements DatabaseRecordIdentifier<String> {

    private static final Random rand = new Random();
    private final HashAlgorithm hashAlgorithm;

    public DatabaseRecordIdentifierHashImpl(@Qualifier("hashAlgorithmsSHA256") HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public String next() {
        String msg = LocalDateTime.now().toString() + rand.nextGaussian();
        return hashAlgorithm.digest(msg);
    }
}
