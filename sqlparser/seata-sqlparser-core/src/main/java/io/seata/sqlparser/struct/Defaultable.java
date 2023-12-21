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
package io.seata.sqlparser.struct;

import java.sql.SQLException;
import java.util.List;

/**
 * The default expr able.
 *
 * @author jsbxyyx
 */
public interface Defaultable {

    /**
     * get primary key values by default keyword.
     *
     * @return pk values by default
     * @throws SQLException the sql exception
     */
    List<Object> getPkValuesByDefault() throws SQLException;

    /**
     * get primary key values by default keyword.
     *
     * @param pkKey the pk key
     * @return pk values by default
     * @throws SQLException the sql exception
     */
    List<Object> getPkValuesByDefault(String pkKey) throws SQLException;

}
