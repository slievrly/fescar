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
package org.apache.seata.common.metadata;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetadataResponseTest {

    @Test
    void testSetAndGetNodes() {
        MetadataResponse response = new MetadataResponse();
        Node node1 = new Node();
        Node node2 = new Node();
        List<Node> nodes = Arrays.asList(node1, node2);

        response.setNodes(nodes);
        assertEquals(nodes, response.getNodes());
        assertEquals(2, response.getNodes().size());
    }

    @Test
    void testSetAndGetStoreMode() {
        MetadataResponse response = new MetadataResponse();
        response.setStoreMode("file");
        assertEquals("file", response.getStoreMode());

        response.setStoreMode("db");
        assertEquals("db", response.getStoreMode());
    }

    @Test
    void testSetAndGetTerm() {
        MetadataResponse response = new MetadataResponse();
        response.setTerm(100L);
        assertEquals(100L, response.getTerm());

        response.setTerm(200L);
        assertEquals(200L, response.getTerm());
    }

    @Test
    void testDefaultValues() {
        MetadataResponse response = new MetadataResponse();
        assertNull(response.getNodes());
        assertNull(response.getStoreMode());
        assertEquals(0L, response.getTerm());
    }
}
