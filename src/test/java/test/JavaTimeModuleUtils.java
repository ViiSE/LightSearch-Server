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

package test;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaTimeModuleUtils {

    private JavaTimeModule module;

    public JavaTimeModuleUtils createJavaTimeModule() {
        module = new JavaTimeModule();
        return JavaTimeModuleUtils.this;
    }

    public JavaTimeModuleUtils addLocalDateTimeSerializer(String pattern) {
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
        module.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        return this;
    }

    public JavaTimeModuleUtils addLocalDateTimeDeserializer(String pattern) {
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return this;
    }

    public JavaTimeModuleUtils addLocalDateDeserializer(String pattern) {
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(DateTimeFormatter.ofPattern(pattern));
        module.addDeserializer(LocalDate.class, localDateDeserializer);

        return this;
    }

    public JavaTimeModuleUtils addLocalDateSerializer(String pattern) {
        LocalDateSerializer localDateSerializer = new LocalDateSerializer(DateTimeFormatter.ofPattern(pattern));
        module.addSerializer(LocalDate.class, localDateSerializer);

        return this;
    }

    public JavaTimeModule javaTimeModule() {
        return module;
    }
}
