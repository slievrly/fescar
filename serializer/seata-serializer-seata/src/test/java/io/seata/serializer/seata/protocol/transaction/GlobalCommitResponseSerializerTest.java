/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.serializer.seata.protocol.transaction;

import io.seata.core.exception.TransactionExceptionCode;
import io.seata.core.model.GlobalStatus;
import io.seata.core.protocol.ProtocolConstants;
import io.seata.core.protocol.ResultCode;
import io.seata.core.protocol.transaction.GlobalCommitResponse;
import io.seata.serializer.seata.SeataSerializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * The type Global commit response codec test.
 *
 * @author zhangsen
 */
public class GlobalCommitResponseSerializerTest {

    /**
     * The Seata codec.
     */
    SeataSerializer seataSerializer = new SeataSerializer();

    /**
     * Test codec.
     */
    @Test
    public void test_codec(){
        GlobalCommitResponse globalCommitResponse = new GlobalCommitResponse();
        globalCommitResponse.setGlobalStatus(GlobalStatus.AsyncCommitting);
        globalCommitResponse.setMsg("aaa");
        globalCommitResponse.setResultCode(ResultCode.Failed);
        globalCommitResponse.setTransactionExceptionCode(TransactionExceptionCode.GlobalTransactionStatusInvalid);

        byte[] bytes = seataSerializer.serialize(globalCommitResponse, ProtocolConstants.VERSION_CURRENT);

        GlobalCommitResponse globalCommitResponse2 = seataSerializer.deserialize(bytes,
            ProtocolConstants.VERSION_CURRENT);

        assertThat(globalCommitResponse2.getGlobalStatus()).isEqualTo(globalCommitResponse.getGlobalStatus());
        assertThat(globalCommitResponse2.getMsg()).isEqualTo(globalCommitResponse.getMsg());
        assertThat(globalCommitResponse2.getResultCode()).isEqualTo(globalCommitResponse.getResultCode());
        assertThat(globalCommitResponse2.getTransactionExceptionCode()).isEqualTo(globalCommitResponse.getTransactionExceptionCode());
    }


}
