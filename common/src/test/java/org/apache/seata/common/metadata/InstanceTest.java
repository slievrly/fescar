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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InstanceTest {

    @Test
    void testGetInstance() {
        Instance instance1 = Instance.getInstance();
        Instance instance2 = Instance.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2); // Should be singleton
    }

    @Test
    void testGetInstances() {
        assertNotNull(Instance.getInstances());
    }

    @Test
    void testSetAndGetNamespace() {
        Instance instance = Instance.getInstance();
        instance.setNamespace("test-namespace");
        assertEquals("test-namespace", instance.getNamespace());
    }

    @Test
    void testSetAndGetClusterName() {
        Instance instance = Instance.getInstance();
        instance.setClusterName("test-cluster");
        assertEquals("test-cluster", instance.getClusterName());
    }

    @Test
    void testSetAndGetUnit() {
        Instance instance = Instance.getInstance();
        instance.setUnit("test-unit");
        assertEquals("test-unit", instance.getUnit());
    }

    @Test
    void testSetAndGetRole() {
        Instance instance = Instance.getInstance();
        instance.setRole(ClusterRole.LEADER);
        assertEquals(ClusterRole.LEADER, instance.getRole());
    }

    @Test
    void testSetAndGetControl() {
        Instance instance = Instance.getInstance();
        Node.Endpoint endpoint = new Node.Endpoint();
        endpoint.setHost("localhost");
        endpoint.setPort(8091);

        instance.setControl(endpoint);
        assertEquals(endpoint, instance.getControl());
    }

    @Test
    void testSetAndGetTransaction() {
        Instance instance = Instance.getInstance();
        Node.Endpoint endpoint = new Node.Endpoint();
        endpoint.setHost("localhost");
        endpoint.setPort(8092);

        instance.setTransaction(endpoint);
        assertEquals(endpoint, instance.getTransaction());
    }

    @Test
    void testSetAndGetWeight() {
        Instance instance = Instance.getInstance();
        instance.setWeight(2.5);
        assertEquals(2.5, instance.getWeight());
    }

    @Test
    void testSetAndIsHealthy() {
        Instance instance = Instance.getInstance();
        instance.setHealthy(false);
        assertFalse(instance.isHealthy());

        instance.setHealthy(true);
        assertTrue(instance.isHealthy());
    }

    @Test
    void testSetAndGetTerm() {
        Instance instance = Instance.getInstance();
        instance.setTerm(100L);
        assertEquals(100L, instance.getTerm());
    }

    @Test
    void testSetAndGetTimestamp() {
        Instance instance = Instance.getInstance();
        long timestamp = System.currentTimeMillis();
        instance.setTimestamp(timestamp);
        assertEquals(timestamp, instance.getTimestamp());
    }

    @Test
    void testSetAndGetMetadata() {
        Instance instance = Instance.getInstance();
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", 123);

        instance.setMetadata(metadata);
        assertEquals(metadata, instance.getMetadata());
    }

    @Test
    void testAddMetadata() {
        Instance instance = Instance.getInstance();
        instance.setMetadata(new HashMap<>()); // Reset metadata

        instance.addMetadata("key1", "value1");
        instance.addMetadata("key2", 456);

        Map<String, Object> metadata = instance.getMetadata();
        assertEquals(2, metadata.size());
        assertEquals("value1", metadata.get("key1"));
        assertEquals(456, metadata.get("key2"));
    }

    @Test
    void testClone() {
        Instance original = Instance.getInstance();
        original.setNamespace("namespace1");
        original.setClusterName("cluster1");
        original.setUnit("unit1");
        original.setWeight(1.5);
        original.setHealthy(true);
        original.setTerm(10L);
        original.setTimestamp(123456L);

        Instance cloned = original.clone();

        assertNotSame(original, cloned);
        assertEquals(original.getNamespace(), cloned.getNamespace());
        assertEquals(original.getClusterName(), cloned.getClusterName());
        assertEquals(original.getUnit(), cloned.getUnit());
        assertEquals(original.getWeight(), cloned.getWeight());
        assertEquals(original.isHealthy(), cloned.isHealthy());
        assertEquals(original.getTerm(), cloned.getTerm());
        assertEquals(original.getTimestamp(), cloned.getTimestamp());
    }

    @Test
    void testToJsonString() {
        Instance instance = Instance.getInstance();
        instance.setNamespace("test");
        instance.setClusterName("cluster");

        ObjectMapper mapper = new ObjectMapper();
        String json = instance.toJsonString(mapper);

        assertNotNull(json);
        assertTrue(json.contains("test"));
        assertTrue(json.contains("cluster"));
    }

    @Test
    void testHashCodeAndEquals() {
        Instance instance1 = Instance.getInstance();
        Node.Endpoint endpoint1 = new Node.Endpoint();
        endpoint1.setHost("host1");
        endpoint1.setPort(8091);
        instance1.setControl(endpoint1);
        instance1.setTransaction(endpoint1);

        Instance instance2 = instance1.clone();
        instance2.setControl(endpoint1);
        instance2.setTransaction(endpoint1);

        assertEquals(instance1.hashCode(), instance2.hashCode());
        assertEquals(instance1, instance2);
    }
}
