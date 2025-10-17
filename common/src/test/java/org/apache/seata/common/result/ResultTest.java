/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.seata.common.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void testDefaultConstructor() {
        Result<String> result = new Result<>();
        assertEquals(Result.SUCCESS_CODE, result.getCode());
        assertEquals(Result.SUCCESS_MSG, result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    void testConstructorWithParameters() {
        Result<String> result = new Result<>("404", "Not Found");
        assertEquals("404", result.getCode());
        assertEquals("Not Found", result.getMessage());
        assertFalse(result.isSuccess());
    }

    @Test
    void testIsSuccess() {
        Result<String> successResult = new Result<>(Result.SUCCESS_CODE, "OK");
        assertTrue(successResult.isSuccess());

        Result<String> failResult = new Result<>(Result.FAIL_CODE, "Error");
        assertFalse(failResult.isSuccess());
    }

    @Test
    void testSetAndGetCode() {
        Result<String> result = new Result<>();
        result.setCode("400");
        assertEquals("400", result.getCode());
    }

    @Test
    void testSetAndGetMessage() {
        Result<String> result = new Result<>();
        result.setMessage("Custom message");
        assertEquals("Custom message", result.getMessage());
    }

    @Test
    void testConstants() {
        assertEquals("200", Result.SUCCESS_CODE);
        assertEquals("success", Result.SUCCESS_MSG);
        assertEquals("500", Result.FAIL_CODE);
    }
}
