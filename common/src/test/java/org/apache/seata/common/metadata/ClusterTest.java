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

import org.apache.seata.common.metadata.namingserver.Unit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClusterTest {

    @Test
    void testDefaultConstructor() {
        Cluster cluster = new Cluster();
        assertNull(cluster.getClusterName());
        assertNull(cluster.getClusterType());
        assertNotNull(cluster.getUnitData());
        assertTrue(cluster.getUnitData().isEmpty());
    }

    @Test
    void testGetAndSetClusterName() {
        Cluster cluster = new Cluster();
        cluster.setClusterName("test-cluster");
        assertEquals("test-cluster", cluster.getClusterName());
    }

    @Test
    void testGetAndSetClusterType() {
        Cluster cluster = new Cluster();
        cluster.setClusterType("primary");
        assertEquals("primary", cluster.getClusterType());
    }

    @Test
    void testGetAndSetUnitData() {
        Cluster cluster = new Cluster();
        Unit unit1 = new Unit();
        Unit unit2 = new Unit();
        List<Unit> units = Arrays.asList(unit1, unit2);

        cluster.setUnitData(units);
        assertEquals(2, cluster.getUnitData().size());
        assertEquals(units, cluster.getUnitData());
    }

    @Test
    void testAppendUnits() {
        Cluster cluster = new Cluster();
        Unit unit1 = new Unit();
        Unit unit2 = new Unit();
        List<Unit> units = Arrays.asList(unit1, unit2);

        cluster.appendUnits(units);
        assertEquals(2, cluster.getUnitData().size());

        // Append more units
        Unit unit3 = new Unit();
        cluster.appendUnits(Arrays.asList(unit3));
        assertEquals(3, cluster.getUnitData().size());
    }

    @Test
    void testAppendUnit() {
        Cluster cluster = new Cluster();
        Unit unit1 = new Unit();
        Unit unit2 = new Unit();

        cluster.appendUnit(unit1);
        assertEquals(1, cluster.getUnitData().size());

        cluster.appendUnit(unit2);
        assertEquals(2, cluster.getUnitData().size());
        assertTrue(cluster.getUnitData().contains(unit1));
        assertTrue(cluster.getUnitData().contains(unit2));
    }
}
