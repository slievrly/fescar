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
package org.apache.seata.common.rpc.http;

import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HttpContextTest {

    @Test
    void testConstructorWithAllParameters() {
        ChannelHandlerContext context = Mockito.mock(ChannelHandlerContext.class);
        String request = "test request";

        HttpContext<String> httpContext = new HttpContext<>(request, context, true, HttpContext.HTTP_2_0);

        assertEquals(request, httpContext.getRequest());
        assertEquals(context, httpContext.getContext());
        assertTrue(httpContext.isKeepAlive());
        assertEquals(HttpContext.HTTP_2_0, httpContext.getHttpVersion());
        assertTrue(httpContext.isHttp2());
        assertFalse(httpContext.isAsync());
    }

    @Test
    void testConstructorWithDefaultHttpVersion() {
        ChannelHandlerContext context = Mockito.mock(ChannelHandlerContext.class);
        String request = "test request";

        HttpContext<String> httpContext = new HttpContext<>(request, context, false);

        assertEquals(request, httpContext.getRequest());
        assertEquals(context, httpContext.getContext());
        assertFalse(httpContext.isKeepAlive());
        assertEquals(HttpContext.HTTP_1_1, httpContext.getHttpVersion());
        assertFalse(httpContext.isHttp2());
        assertFalse(httpContext.isAsync());
    }

    @Test
    void testIsHttp2WithHttp11() {
        ChannelHandlerContext context = Mockito.mock(ChannelHandlerContext.class);
        HttpContext<String> httpContext = new HttpContext<>("request", context, true, HttpContext.HTTP_1_1);

        assertFalse(httpContext.isHttp2());
    }

    @Test
    void testSettersAndGetters() {
        ChannelHandlerContext context = Mockito.mock(ChannelHandlerContext.class);
        ChannelHandlerContext newContext = Mockito.mock(ChannelHandlerContext.class);

        HttpContext<String> httpContext = new HttpContext<>("request", context, true);

        // Test setRequest and getRequest
        httpContext.setRequest("new request");
        assertEquals("new request", httpContext.getRequest());

        // Test setContext and getContext
        httpContext.setContext(newContext);
        assertEquals(newContext, httpContext.getContext());

        // Test setKeepAlive and isKeepAlive
        httpContext.setKeepAlive(false);
        assertFalse(httpContext.isKeepAlive());

        // Test setAsync and isAsync
        httpContext.setAsync(true);
        assertTrue(httpContext.isAsync());

        // Test setHttpVersion and getHttpVersion
        httpContext.setHttpVersion(HttpContext.HTTP_2_0);
        assertEquals(HttpContext.HTTP_2_0, httpContext.getHttpVersion());
        assertTrue(httpContext.isHttp2());
    }
}
