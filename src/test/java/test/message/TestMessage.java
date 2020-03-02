/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.message;

/**
 *
 * @author ViiSE
 */
public class TestMessage {
    
    private enum IDENTIFY {
        BEGIN {
            @Override
            public String toString() { return "BEGIN"; }
        },
        END {
            @Override
            public String toString() { return "END"; }
        }
    }

    private static void printTestMessage(String className, String methodName, IDENTIFY identify) {
        System.out.println("-----------------------------------------------");
        System.out.println(className +  ". Method:" + methodName + ". Test " + identify.toString());
        System.out.println("-----------------------------------------------");
    }

    private static void printTestMessage(String className, IDENTIFY identify) {
        System.out.println("-----------------------------------------------");
        System.out.println(className + ". Test " + identify.toString());
        System.out.println("-----------------------------------------------");
    }

    private static void printTestMessage(String methodName) {
        System.out.println("[Method: " + methodName + "]");
        System.out.println("-----------------------------------------------");
    }

    public static void testBegin(String className, String methodName) {
        printTestMessage(className, methodName, IDENTIFY.BEGIN);
    }

    public static void testBegin(Class<?> clazz, String methodName) {
        printTestMessage(clazz.getSimpleName(), methodName, IDENTIFY.BEGIN);
    }

    public static void testBegin(String className) {
        printTestMessage(className, IDENTIFY.BEGIN);
    }

    public static void testBegin(Class<?> clazz) {
        printTestMessage(clazz.getSimpleName(), IDENTIFY.BEGIN);
    }

    public static void testEnd(String className, String methodName) {
        printTestMessage(className, methodName, IDENTIFY.END);
    }

    public static void testEnd(Class<?> clazz, String methodName) {
        printTestMessage(clazz.getSimpleName(), methodName, IDENTIFY.END);
    }

    public static void testEnd(String className) {
        printTestMessage(className, IDENTIFY.END);
    }

    public static void testEnd(Class<?> clazz) {
        printTestMessage(clazz.getSimpleName(), IDENTIFY.END);
    }

    public static void testMethod(String methodName) {
        printTestMessage(methodName);
    }

    public static void catchMessage(Exception ex) {
        System.out.println("CATCH! Exception: " + ex.getMessage());
    }

}
