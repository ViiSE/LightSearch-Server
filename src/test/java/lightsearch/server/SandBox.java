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

package lightsearch.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.*;
import java.util.Date;

public class SandBox {

    /* ~Welcome to the SandBox! You can do whatever you want here. This is experiments zone. Enjoy!~ */

    @Test
    public void sandBox() throws JsonProcessingException, IOException {
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.MIDNIGHT;
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        long dateTimeMills = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Date oldDate = new Date(dateTimeMills);

        System.out.println(dateTimeMills);
        System.out.println(oldDate.toInstant().toEpochMilli());
    }
}
