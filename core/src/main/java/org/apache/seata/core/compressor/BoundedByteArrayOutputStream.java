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

import java.io.ByteArrayOutputStream;

/**
 * Byte array output with a maximum size checked before each write.
 */
public class BoundedByteArrayOutputStream extends ByteArrayOutputStream {

    private final int maxOutputSize;

    public BoundedByteArrayOutputStream(int maxOutputSize) {
        super(Math.min(8192, maxOutputSize));
        this.maxOutputSize = maxOutputSize;
    }

    @Override
    public synchronized void write(int value) {
        checkSize(1);
        super.write(value);
    }

    @Override
    public synchronized void write(byte[] bytes, int offset, int length) {
        if (offset < 0 || length < 0 || offset > bytes.length - length) {
            throw new IndexOutOfBoundsException();
        }
        checkSize(length);
        super.write(bytes, offset, length);
    }

    private void checkSize(int length) {
        if (length > maxOutputSize - count) {
            throw new IllegalArgumentException("Decompressed data exceeds maximum size: " + maxOutputSize);
        }
    }
}
