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

import java.util.ArrayList;
import java.util.List;

public class BlacklistServiceTestImpl implements BlacklistService<String> {

    private static final List<String> blacklist = new ArrayList<>();

    @Override
    public List<String> blacklist() {
        return blacklist;
    }

    @Override
    public boolean remove(String IMEI) {
        return blacklist.remove(IMEI);
    }

    @Override
    public boolean add(String IMEI) {
        return blacklist.add(IMEI);
    }

    @Override
    public boolean contains(String IMEI) {
        return blacklist.contains(IMEI);
    }
}
