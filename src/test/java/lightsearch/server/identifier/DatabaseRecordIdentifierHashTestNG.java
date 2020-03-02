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

package lightsearch.server.identifier;

import lightsearch.server.security.HashAlgorithmsSHA256Impl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static test.message.TestMessage.*;

public class DatabaseRecordIdentifierHashTestNG {

    private DatabaseRecordIdentifier<String> dbRecIdent;

    @BeforeClass
    public void setUpClass() {
        dbRecIdent = new DatabaseRecordIdentifierHashImpl(new HashAlgorithmsSHA256Impl());

        testBegin(DatabaseRecordIdentifierHashImpl.class);
    }

    @Test
    public void next_noCollisions() {
        testMethod("next() [not collisions]");

        int MILLIONS = 1000000;
        String prev = dbRecIdent.next();
        for(int i = 0; i < MILLIONS; i++) {
            String next = dbRecIdent.next();
            assertNotEquals(next, prev);
            prev = next;
        }
    }

    @AfterClass
    public void teardownClass() {
        testEnd(DatabaseRecordIdentifierHashImpl.class);
    }
}
