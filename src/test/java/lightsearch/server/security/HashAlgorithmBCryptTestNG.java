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

package lightsearch.server.security;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static test.message.TestMessage.*;

public class HashAlgorithmBCryptTestNG {
    //message - SomeSecretText
    private final String digestOfSomeSecretTextBCrypt = "$2a$10$SIPinpyWlZhMii/F9PKPYu.egbkg/koecFAtNVL6uhTxLGG./V24C";

    private HashAlgorithm hashAlg;

    @BeforeClass
    public void setUpClass() {
        hashAlg = new HashAlgorithmBCryptImpl();
        testBegin(HashAlgorithmBCryptImpl.class);
    }

    @Test
    private void digest() {
        testMethod("digest()");

        String someSecretTest = "SomeSecretText";
        String digest = hashAlg.digest(someSecretTest);

        System.out.println("digestOfSomeSecretTextBCrypt = " + digestOfSomeSecretTextBCrypt);
        System.out.println("digest                       = " + digest);

        assertNotEquals(digestOfSomeSecretTextBCrypt, digest);
    }

    @Test
    private void isDigest_true() {
        testMethod("isDigest() [true]");

        boolean isDigest = hashAlg.isDigest(digestOfSomeSecretTextBCrypt);

        System.out.println("digest = " + digestOfSomeSecretTextBCrypt);
        System.out.println("isDigest = " + isDigest);

        assertTrue(isDigest);
    }

    @Test(dataProvider = "isDigestFalse")
    private void isDigest_false(String fakeDigest, String cause) {
        testMethod("digest() " + cause);

        boolean isDigest = hashAlg.isDigest(fakeDigest);

        System.out.println("digest = " + fakeDigest);
        System.out.println("isDigest = " + isDigest);

        assertFalse(isDigest);
    }

    @DataProvider(name = "isDigestFalse")
    public Object[][] createFakeDigests() {
        return new Object[][] {
                {"$2a$11$SIPinpyWlZhMii/F9PKPYu.egbkg/koecFAtNVL6uhTxLGG./V24C", "[No starts with $2$10$]"},
                {"$2a$10$SIPinpyWlZhMii/F9PKPYu.egbkg/koecFAtNVL6uhTxLGG./V24F9PKPYuC", "[Length is more than 60]"},
                {"$2a$10$SIPinpyWlZhMii/F9PKPYu.egbkg/koecFAtxLGG./V24C", "[Length is less than 60"}
        };
    }

    @AfterClass
    public void teardownClass() {
        testEnd(HashAlgorithmBCryptImpl.class);
    }
}
