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

package lightsearch.server.time;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.*;

public class DateTimeComparatorTestNG {

    private DateTimeComparator dateTimeComparator;
    private CurrentDateTime curDateTime = new CurrentDateTimeDefaultImpl();

    @BeforeClass
    public void setUpClass() {
        String pattern = CurrentDateTimePattern.dateTimeInStandardForm();
        dateTimeComparator = new DateTimeComparatorDefaultImpl(pattern);

        testBegin(DateTimeComparator.class);
    }

    @Test(dataProvider = "dateTimeDP")
    public void isAfter(Object date, String cause) {
        testMethod("isAfter() " + cause);

        LocalDateTime beforeDate = LocalDate.now().atStartOfDay();
        System.out.println("date: " + date);
        System.out.println("beforeDate: " + beforeDate);
        boolean isAfter = dateTimeComparator.isAfter(date, beforeDate);

        System.out.println("isAfter: " + isAfter);
        assertTrue(isAfter);
    }

    @Test(dataProvider = "dateTimeDP")
    public void isBefore(Object date, String cause) {
        testMethod("isBefore() " + cause);

        LocalDateTime afterDate = LocalDate.now().atTime(LocalTime.MAX);
        System.out.println("date: " + date);
        System.out.println("afterDate: " + afterDate);
        boolean isBefore = dateTimeComparator.isBefore(date, afterDate);

        System.out.println("isBefore: " + isBefore);
        assertTrue(isBefore);
    }

    @DataProvider(name = "dateTimeDP")
    public Object[][] createIsAfterDP() {
        return new Object[][] {
                {LocalDateTime.now(), "[LocalDateTime instance]"},
                {curDateTime.dateTimeInStandardFormat(), "[String instance]"},
        };
    }

    @AfterClass
    public void teardownClass() {
        testEnd(DateTimeComparator.class);
    }
}
