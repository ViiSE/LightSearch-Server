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

package lightsearch.server.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {

    public static String correctDateTimeInStandardFormWithMs(String rawTime) {
        if(rawTime.length() != 21) {
            if (rawTime.length() < 21)
                return rawTime;
            else
                return rawTime.substring(0, 21);
        }

        return rawTime;
    }

    public static void sleep(int milliseconds) {
        try { Thread.sleep(milliseconds); } catch (InterruptedException ignore) { }
    }

    // 86,400,000 ms = 1 day
    public static long convertDaysToMilliseconds(long dayAmount) {
        return 86400000L * dayAmount;
    }

    public static LocalDateTime convertMillisecondsToDateTime(String milliseconds) {
        long mills = Long.parseLong(milliseconds);
        return convertMillisecondsToDateTime(mills);
    }

    public static LocalDateTime convertMillisecondsToDateTime(long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds*1000), ZoneId.systemDefault());
    }
}
