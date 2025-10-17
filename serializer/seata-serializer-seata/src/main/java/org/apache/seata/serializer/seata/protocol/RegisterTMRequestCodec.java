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
package org.apache.seata.serializer.seata.protocol;

import io.netty.buffer.ByteBuf;
import org.apache.seata.core.protocol.RegisterTMRequest;

import java.nio.ByteBuffer;

/**
 * The type Register tm request codec.
 *
 */
public class RegisterTMRequestCodec extends AbstractIdentifyRequestCodec {

    @Override
    public Class<?> getMessageClassType() {
        return RegisterTMRequest.class;
    }

    @Override
    protected <T> void doEncode(T t, ByteBuf out) {
        super.doEncode(t, out);

        RegisterTMRequest registerTMRequest = (RegisterTMRequest) t;

        String accessKey = registerTMRequest.getAccessKey();
        if (accessKey != null) {
            byte[] bs = accessKey.getBytes(UTF8);
            out.writeShort((short) bs.length);
            if (bs.length > 0) {
                out.writeBytes(bs);
            }
        } else {
            out.writeShort((short) 0);
        }

        String digest = registerTMRequest.getDigest();
        if (digest != null) {
            byte[] bs = digest.getBytes(UTF8);
            out.writeShort((short) bs.length);
            if (bs.length > 0) {
                out.writeBytes(bs);
            }
        } else {
            out.writeShort((short) 0);
        }

        Long timestamp = registerTMRequest.getTimestamp();
        if (timestamp != null) {
            out.writeLong(timestamp);
        } else {
            out.writeLong(0L);
        }

        String authVersion = registerTMRequest.getAuthVersion();
        if (authVersion != null) {
            byte[] bs = authVersion.getBytes(UTF8);
            out.writeShort((short) bs.length);
            if (bs.length > 0) {
                out.writeBytes(bs);
            }
        } else {
            out.writeShort((short) 0);
        }
    }

    @Override
    public <T> void decode(T t, ByteBuffer in) {
        RegisterTMRequest registerTMRequest = (RegisterTMRequest) t;

        if (in.remaining() < 2) {
            return;
        }
        short len = in.getShort();
        if (len > 0) {
            if (in.remaining() < len) {
                return;
            }
            byte[] bs = new byte[len];
            in.get(bs);
            registerTMRequest.setVersion(new String(bs, UTF8));
        } else {
            return;
        }

        if (in.remaining() < 2) {
            return;
        }
        len = in.getShort();
        if (len > 0) {
            if (in.remaining() < len) {
                return;
            }
            byte[] bs = new byte[len];
            in.get(bs);
            registerTMRequest.setApplicationId(new String(bs, UTF8));
        }

        if (in.remaining() < 2) {
            return;
        }
        len = in.getShort();
        if (in.remaining() < len) {
            return;
        }
        byte[] bs = new byte[len];
        in.get(bs);
        registerTMRequest.setTransactionServiceGroup(new String(bs, UTF8));

        if (in.remaining() < 2) {
            return;
        }
        len = in.getShort();
        if (len > 0) {
            if (in.remaining() < len) {
                return;
            }
            bs = new byte[len];
            in.get(bs);
            registerTMRequest.setExtraData(new String(bs, UTF8));
        }

        // HMAC authentication fields - backward compatible
        if (in.remaining() < 2) {
            return;
        }
        len = in.getShort();
        if (len > 0) {
            if (in.remaining() < len) {
                return;
            }
            bs = new byte[len];
            in.get(bs);
            registerTMRequest.setAccessKey(new String(bs, UTF8));
        }

        if (in.remaining() < 2) {
            return;
        }
        len = in.getShort();
        if (len > 0) {
            if (in.remaining() < len) {
                return;
            }
            bs = new byte[len];
            in.get(bs);
            registerTMRequest.setDigest(new String(bs, UTF8));
        }

        if (in.remaining() < 8) {
            return;
        }
        long timestamp = in.getLong();
        if (timestamp > 0) {
            registerTMRequest.setTimestamp(timestamp);
        }

        if (in.remaining() < 2) {
            return;
        }
        len = in.getShort();
        if (len > 0) {
            if (in.remaining() < len) {
                return;
            }
            bs = new byte[len];
            in.get(bs);
            registerTMRequest.setAuthVersion(new String(bs, UTF8));
        }
    }
}
