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
package org.apache.seata.core.compressor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedByteArrayOutputStreamTest {

    @Test
    void checksBeforeWriting() {
        BoundedByteArrayOutputStream out = new BoundedByteArrayOutputStream(3);
        out.write(1);
        out.write(new byte[] {2, 3}, 0, 2);
        assertThrows(IllegalArgumentException.class, () -> out.write(4));
        assertThrows(IllegalArgumentException.class, () -> out.write(new byte[] {4}, 0, 1));
        assertArrayEquals(new byte[] {1, 2, 3}, out.toByteArray());
        out.write(new byte[0], 0, 0);
        assertThrows(IndexOutOfBoundsException.class, () -> out.write(new byte[1], 0, Integer.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> new BoundedByteArrayOutputStream(-1));
    }

    @Test
    void legacyImplementationsMustOptIntoBoundedDecompression() {
        Compressor legacy = new Compressor() {
            public byte[] compress(byte[] bytes) {
                return bytes;
            }

            public byte[] decompress(byte[] bytes) {
                fail("Legacy method must not be called for bounded decompression");
                return bytes;
            }
        };
        assertThrows(UnsupportedOperationException.class, () -> legacy.decompress(new byte[1], 1));
    }
}
