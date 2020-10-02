/*
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lightsearch.server.service;

import lightsearch.server.security.HashAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("blacklistService")
public class BlacklistServiceImpl implements BlacklistService<String> {

    private final List<String> blacklist = new ArrayList<>();
    private final HashAlgorithm hashAlgorithm;

    public BlacklistServiceImpl(@Qualifier("hashAlgorithmsSHA512") HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public List<String> blacklist() {
        return new ArrayList<>(blacklist);
    }

    @Override
    public boolean remove(String IMEI) {
        String hashIMEI = create(IMEI);
        return blacklist.remove(hashIMEI);
    }

    @Override
    public String add(String IMEI) {
        String hashIMEI = create(IMEI);
        blacklist.add(hashIMEI);
        return hashIMEI;
    }

    @Override
    public boolean contains(String IMEI) {
        String hashIMEI = create(IMEI);
        return blacklist.contains(hashIMEI);
    }

    private String create(String IMEI) {
        return hashAlgorithm.isDigest(IMEI) ? IMEI : hashAlgorithm.digest(IMEI);
    }
}
