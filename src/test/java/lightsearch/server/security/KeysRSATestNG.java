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

package lightsearch.server.security;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

public class KeysRSATestNG {

    private Keys<PublicKey, PrivateKey> keys;

    @BeforeClass
    public void setUpClass() {
        keys = new KeysRSAImpl();
        testBegin(KeysRSAImpl.class);
    }

    @Test
    public void publicKey() {
        testMethod("publicKey()");

        PublicKey publicKey = keys.publicKey();
        assertNotNull(publicKey, "PublicKey is null!");
        assertEquals(publicKey.getAlgorithm(), "RSA", "Algorithm is not RSA!");
        System.out.println("Done!");
    }

    @Test
    public void privateKey() {
        testMethod("privateKey()");

        PrivateKey privateKey = keys.privateKey();
        assertNotNull(privateKey, "PrivateKey is null!");
        assertEquals(privateKey.getAlgorithm(), "RSA", "Algorithm is not RSA!");

        System.out.println("Done!");
    }

    @AfterClass
    public void shutdownClass() {
        testEnd(KeysRSAImpl.class);
    }
}
