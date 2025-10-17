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
package org.apache.seata.serializer.protobuf.convertor;

import org.apache.seata.core.protocol.RegisterTMRequest;
import org.apache.seata.serializer.protobuf.generated.RegisterTMRequestProto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterTMRequestConvertorTest {

    @Test
    public void convert2Proto() {

        RegisterTMRequest registerRMRequest = new RegisterTMRequest();
        registerRMRequest.setVersion("123");
        registerRMRequest.setTransactionServiceGroup("group");
        registerRMRequest.setExtraData("extraData");
        registerRMRequest.setApplicationId("appId");
        RegisterTMRequestConvertor convertor = new RegisterTMRequestConvertor();
        RegisterTMRequestProto proto = convertor.convert2Proto(registerRMRequest);
        RegisterTMRequest real = convertor.convert2Model(proto);

        assertThat((real.getTypeCode())).isEqualTo(registerRMRequest.getTypeCode());
        assertThat((real.getVersion())).isEqualTo(registerRMRequest.getVersion());
        assertThat((real.getTransactionServiceGroup())).isEqualTo(registerRMRequest.getTransactionServiceGroup());
        assertThat((real.getExtraData())).isEqualTo(registerRMRequest.getExtraData());
        assertThat((real.getApplicationId())).isEqualTo(registerRMRequest.getApplicationId());
    }

    @Test
    public void convert2Proto_withHmacFields() {

        RegisterTMRequest registerRMRequest = new RegisterTMRequest();
        registerRMRequest.setVersion("2.0");
        registerRMRequest.setTransactionServiceGroup("testGroup");
        registerRMRequest.setExtraData("extraData");
        registerRMRequest.setApplicationId("testApp");
        registerRMRequest.setAccessKey("testAccessKey");
        registerRMRequest.setDigest("testDigest");
        registerRMRequest.setTimestamp(1234567890L);
        registerRMRequest.setAuthVersion("V4");

        RegisterTMRequestConvertor convertor = new RegisterTMRequestConvertor();
        RegisterTMRequestProto proto = convertor.convert2Proto(registerRMRequest);
        RegisterTMRequest real = convertor.convert2Model(proto);

        assertThat((real.getTypeCode())).isEqualTo(registerRMRequest.getTypeCode());
        assertThat((real.getVersion())).isEqualTo(registerRMRequest.getVersion());
        assertThat((real.getTransactionServiceGroup())).isEqualTo(registerRMRequest.getTransactionServiceGroup());
        assertThat((real.getExtraData())).isEqualTo(registerRMRequest.getExtraData());
        assertThat((real.getApplicationId())).isEqualTo(registerRMRequest.getApplicationId());
        assertThat((real.getAccessKey())).isEqualTo(registerRMRequest.getAccessKey());
        assertThat((real.getDigest())).isEqualTo(registerRMRequest.getDigest());
        assertThat((real.getTimestamp())).isEqualTo(registerRMRequest.getTimestamp());
        assertThat((real.getAuthVersion())).isEqualTo(registerRMRequest.getAuthVersion());
    }

    @Test
    public void convert2Proto_backwardCompatibility() {

        RegisterTMRequest registerRMRequest = new RegisterTMRequest();
        registerRMRequest.setVersion("1.0");
        registerRMRequest.setTransactionServiceGroup("oldGroup");
        registerRMRequest.setExtraData("oldExtra");
        registerRMRequest.setApplicationId("oldApp");

        RegisterTMRequestConvertor convertor = new RegisterTMRequestConvertor();
        RegisterTMRequestProto proto = convertor.convert2Proto(registerRMRequest);
        RegisterTMRequest real = convertor.convert2Model(proto);

        assertThat((real.getTypeCode())).isEqualTo(registerRMRequest.getTypeCode());
        assertThat((real.getVersion())).isEqualTo(registerRMRequest.getVersion());
        assertThat((real.getTransactionServiceGroup())).isEqualTo(registerRMRequest.getTransactionServiceGroup());
        assertThat((real.getExtraData())).isEqualTo(registerRMRequest.getExtraData());
        assertThat((real.getApplicationId())).isEqualTo(registerRMRequest.getApplicationId());
        assertThat((real.getAccessKey())).isNull();
        assertThat((real.getDigest())).isNull();
        assertThat((real.getTimestamp())).isNull();
        assertThat((real.getAuthVersion())).isNull();
    }
}
