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
package org.apache.seata.core.context;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Context core test.
 *
 */
public class ContextCoreTest {

    private final String firstKey = "first_key";
    private final String firstValue = "first_value";
    private final String secondKey = "second_key";
    private final String secondValue = "second_value";
    private final String notExistKey = "not_exist_key";

    /**
     * Test put.
     */
    @Test
    public void testPut() {
        ContextCore load = ContextCoreLoader.load();
        assertThat(load.put(firstKey, firstValue)).isNull();
        assertThat(load.put(secondKey, secondValue)).isNull();
        assertThat(load.put(firstKey, secondValue)).isEqualTo(firstValue);
        assertThat(load.put(secondKey, firstValue)).isEqualTo(secondValue);
        // clear keys
        load.remove(firstKey);
        load.remove(secondKey);
    }

    /**
     * Test get.
     */
    @Test
    public void testGet() {
        ContextCore load = ContextCoreLoader.load();
        load.put(firstKey, firstValue);
        load.put(secondKey, firstValue);
        assertThat(load.get(firstKey)).isEqualTo(firstValue);
        assertThat(load.get(secondKey)).isEqualTo(firstValue);
        load.put(firstKey, secondValue);
        load.put(secondKey, secondValue);
        assertThat(load.get(firstKey)).isEqualTo(secondValue);
        assertThat(load.get(secondKey)).isEqualTo(secondValue);
        assertThat(load.get(notExistKey)).isNull();
        // clear keys
        load.remove(firstKey);
        load.remove(secondKey);
        load.remove(notExistKey);
    }

    /**
     * Test entries.
     */
    @Test
    public void testEntries() {
        ContextCore load = ContextCoreLoader.load();
        load.put(firstKey, firstValue);
        load.put(secondKey, firstValue);
        Map<String, Object> entries = load.entries();
        assertThat(entries.get(firstKey)).isEqualTo(firstValue);
        assertThat(entries.get(secondKey)).isEqualTo(firstValue);
        load.remove(firstKey);
        load.remove(secondKey);
        load.remove(notExistKey);
    }

    /**
     * Test remove.
     */
    @Test
    public void testRemove() {
        ContextCore load = ContextCoreLoader.load();
        load.put(firstKey, firstValue);
        load.put(secondKey, secondValue);
        assertThat(load.remove(firstKey)).isEqualTo(firstValue);
        assertThat(load.remove(secondKey)).isEqualTo(secondValue);
        assertThat(load.remove(notExistKey)).isNull();
    }
}
