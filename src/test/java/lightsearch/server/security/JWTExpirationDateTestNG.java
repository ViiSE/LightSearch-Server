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

import lightsearch.server.time.JWTExpiration;
import lightsearch.server.time.JWTExpirationDateImpl;
import lightsearch.server.time.TimeUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static org.testng.Assert.assertEquals;

public class JWTExpirationDateTestNG {

    private JWTExpiration<Date> jwtExpiration;

    @BeforeClass
    @Parameters({"jwtValidDayCount"})
    public void setUpClass(long jwtValidDayCount) {
        jwtExpiration = new JWTExpirationDateImpl(jwtValidDayCount);
    }


    @Test
    @Parameters({"jwtValidDayCount"})
    public void until(long jwtValidDayCount) {
        Date dateActual = jwtExpiration.until();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.MIDNIGHT;
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        Date dateExpected = new Date(
                dateTime.atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                    +
                        TimeUtils.convertDaysToMilliseconds(jwtValidDayCount));

        System.out.println("Date actual: " + dateActual);
        System.out.println("Date expected: " + dateExpected);
        assertEquals(dateActual, dateExpected);
    }
}
