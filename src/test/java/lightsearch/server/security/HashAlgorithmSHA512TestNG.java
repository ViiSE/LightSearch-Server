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

public class HashAlgorithmSHA512TestNG {
    //message - SomeSecretText
    private final String digestOfSomeSecretTextSHA512 = "4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c5291";

    private HashAlgorithm hashAlg;

    @BeforeClass
    public void setUpClass() {
        hashAlg = new HashAlgorithmSHA512Impl();
        testBegin(HashAlgorithmSHA512Impl.class);
    }

    @Test
    private void digest() {
        testMethod("digest()");

        String someSecretTest = "SomeSecretText";
        String digest = hashAlg.digest(someSecretTest);

        System.out.println("digestOfSomeSecretTextSHA512 = " + digestOfSomeSecretTextSHA512);
        System.out.println("digest                       = " + digest);

        assertEquals(digestOfSomeSecretTextSHA512, digest);
    }

    @Test
    private void isDigest_true() {
        testMethod("isDigest() [true]");

        boolean isDigest = hashAlg.isDigest(digestOfSomeSecretTextSHA512);

        System.out.println("digest = " + digestOfSomeSecretTextSHA512);
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
                {"4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Length is less than 128]"},
                {"g4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [g]]"},
                {"G4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [G]]"},
                {"h4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [h]]"},
                {"H4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [H]]"},
                {"i4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [i]]"},
                {"I4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [I]]"},
                {"j4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [j]]"},
                {"J4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [J]]"},
                {"k4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [k]]"},
                {"K4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [K]]"},
                {"l4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [l]]"},
                {"L4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [L]]"},
                {"m4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [m]]"},
                {"M4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [M]]"},
                {"n4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [n]]"},
                {"N4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [N]]"},
                {"o4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [o]]"},
                {"O4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [O]]"},
                {"p4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [p]]"},
                {"P4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [P]]"},
                {"q4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [q]]"},
                {"Q4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [Q]]"},
                {"r4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [r]]"},
                {"R4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [R]]"},
                {"s4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [s]]"},
                {"S4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [S]]"},
                {"t4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [t]]"},
                {"T4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [T]]"},
                {"u4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [u]]"},
                {"U4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [U]]"},
                {"v4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [v]]"},
                {"V4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [V]]"},
                {"w4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [w]]"},
                {"W4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [W]]"},
                {"x4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [x]]"},
                {"X4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [X]]"},
                {"y4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [y]]"},
                {"Y4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [Y]]"},
                {"z4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [z]]"},
                {"Z4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c529", "[Invalid character [Z]]"},
                {"4dcbe8462086ed9898c2fcccb591609d1cbb6d3760c8e51230e49e7a4bfeefd15227518f5a5ef42a10511395664e5a585d2f1cef7b298f2316826c5b3c8c52912", "[Length is more than 128]"}
        };
    }

    @AfterClass
    public void teardownClass() {
        testEnd(HashAlgorithmSHA512Impl.class);
    }
}
