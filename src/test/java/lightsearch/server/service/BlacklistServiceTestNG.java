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

package lightsearch.server.service;

import lightsearch.server.security.HashAlgorithm;
import lightsearch.server.security.HashAlgorithmSHA512Impl;
import org.testng.annotations.*;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class BlacklistServiceTestNG {

    private BlacklistService<String> blacklistService;

    @BeforeClass
    @Parameters({"hashIMEI", "IMEI"})
    public void setUpClass(String hashIMEI, String IMEI) {
        HashAlgorithm hashAlgorithm = new HashAlgorithmSHA512Impl();
        blacklistService = new BlacklistServiceImpl(hashAlgorithm);
        blacklistService.add(hashIMEI);
        blacklistService.add(IMEI);

        testBegin(BlacklistServiceImpl.class);
    }

    @Test(priority = 1, dataProvider = "createDP")
    public void add(String IMEI, String cause) {
        testMethod("add() " + cause);

        blacklistService.add(IMEI);
        System.out.println("Added: " + IMEI);

        assertTrue(blacklistService.blacklist().size() > 1);
    }

    @Test(priority = 2)
    @Parameters({"hashIMEI", "IMEI"})
    public void remove(String hashIMEI, String IMEI) {
        testMethod("remove()");

        System.out.println("Blacklist: ");
        blacklistService.blacklist().forEach(System.out::println);
        System.out.println("+--------------------+");

        blacklistService.remove(hashIMEI);
        assertFalse(blacklistService.contains(hashIMEI));

        blacklistService.remove(IMEI);
        assertFalse(blacklistService.contains(IMEI));
    }

    @Test(priority = 3)
    @Parameters({"hashIMEI"})
    public void contains(String hashIMEI) {
        testMethod("contains()");

        blacklistService.add(hashIMEI);
        assertTrue(blacklistService.contains(hashIMEI));
    }

    @DataProvider(name = "createDP")
    public Object[][] createDP() {
        return new Object[][] {
                {"111111111111111", "not hash"},
                {"26aef7977416d7c882416606d3e15375a79a82f15b9ba71f5ef4fd2275a2d1c2d611163a6c44af1fafee43b4df6a80ba6e3f3cf3f937670aa24f2a712258dd06", "hash"},
        };
    }

    @AfterClass
    public void teardownClass() {
        testEnd(BlacklistServiceImpl.class);
    }
}
