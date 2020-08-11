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

package test;

import lightsearch.server.initialization.CurrentServerDirectoryTestImpl;
import lightsearch.server.properties.PropertiesDirectory;

public class TestDirectories {

    public static PropertiesDirectory applicationPropertiesDirectory() {
        return () -> new CurrentServerDirectoryTestImpl().name() + "application.properties";
    }

    public static PropertiesDirectory fakePropertiesDirectory() {
        return () -> new CurrentServerDirectoryTestImpl().name() + "application-fake.properties";
    }
}
