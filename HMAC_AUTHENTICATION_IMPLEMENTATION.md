# HMAC Authentication Implementation for RegisterTMRequest

## Overview
This implementation extends the `RegisterTMRequest` message with HMAC-based authentication fields while maintaining backward compatibility with older versions.

## Changes Made

### 1. Core Protocol Changes
**File: `core/src/main/java/org/apache/seata/core/protocol/RegisterTMRequest.java`**

Added four new fields to support HMAC authentication:
- `accessKey` (String): The access key for authentication
- `digest` (String): The HMAC digest generated from the message
- `timestamp` (Long): Timestamp when the request was created
- `authVersion` (String): Version of the authentication algorithm (e.g., "V4")

Each field includes standard getter and setter methods.

### 2. Seata Serializer Codec Changes
**File: `serializer/seata-serializer-seata/src/main/java/org/apache/seata/serializer/seata/protocol/RegisterTMRequestCodec.java`**

Extended the codec to encode and decode the new HMAC fields:

**Encoding:**
- Writes each field in order: accessKey, digest, timestamp, authVersion
- Uses standard length-prefixed encoding for strings (2-byte length + UTF-8 bytes)
- Uses 8-byte long for timestamp
- Null values are properly handled

**Decoding (Backward Compatible):**
- Reads the basic fields first (version, applicationId, transactionServiceGroup, extraData)
- Checks remaining buffer size before attempting to read each HMAC field
- If buffer ends before HMAC fields, gracefully returns without error
- This allows old messages (without HMAC fields) to be decoded successfully

### 3. Protobuf Serializer Changes
**File: `serializer/seata-serializer-protobuf/src/main/resources/protobuf/org/apache/seata/protocol/transcation/registerTMRequest.proto`**

Extended the protobuf definition with new fields:
```protobuf
message RegisterTMRequestProto {
    AbstractIdentifyRequestProto abstractIdentifyRequest = 1;
    string accessKey = 2;
    string digest = 3;
    int64 timestamp = 4;
    string authVersion = 5;
}
```

**File: `serializer/seata-serializer-protobuf/src/main/java/org/apache/seata/serializer/protobuf/convertor/RegisterTMRequestConvertor.java`**

Updated the convertor to handle the new fields:
- `convert2Proto`: Populates new fields if they are non-null
- `convert2Model`: Reads new fields and only sets them if they are non-empty/non-zero

### 4. Client Integration
**File: `core/src/main/java/org/apache/seata/core/rpc/netty/TmNettyRemotingClient.java`**

Updated the `getPoolKeyFunction` method to populate HMAC fields when creating RegisterTMRequest:
- Sets accessKey from configuration
- Generates digest using the configured signer
- Sets current timestamp
- Sets authVersion from signer

## Backward Compatibility

The implementation ensures backward compatibility in multiple ways:

### 1. Decode Compatibility
- **Old client → New server**: Old clients send messages without HMAC fields. The new server's codec checks for remaining bytes before reading HMAC fields and gracefully handles their absence.
- **New client → Old server**: While new clients will send HMAC fields, old servers using the old codec will only read the basic fields and ignore any additional data.

### 2. Optional Fields
All new fields are optional and can be null:
- If not set by the client, they are encoded as empty/zero values
- The decoder handles missing fields by leaving them as null

### 3. Proto3 Compatibility
Using proto3 syntax ensures that unset fields are handled gracefully with default values.

## Testing

### Unit Tests
Added comprehensive unit tests in:
1. `RegisterTMRequestSerializerTest`:
   - `test_codec_with_hmac_fields()`: Tests full encode/decode with HMAC fields
   - `test_backward_compatibility_old_message()`: Tests that old messages without HMAC fields decode correctly

2. `RegisterTMRequestConvertorTest`:
   - `convert2Proto_withHmacFields()`: Tests protobuf conversion with HMAC fields
   - `convert2Proto_backwardCompatibility()`: Tests protobuf conversion without HMAC fields

All tests verify:
- Successful encoding and decoding
- Field values are preserved correctly
- Old messages without HMAC fields are handled gracefully
- New fields remain null when not present in encoded data

## Version Compatibility Matrix

| Client Version | Server Version | Result |
|---------------|---------------|--------|
| Old (without HMAC) | Old (without HMAC) | ✅ Works - Basic fields only |
| Old (without HMAC) | New (with HMAC) | ✅ Works - Server reads basic fields, HMAC fields are null |
| New (with HMAC) | Old (without HMAC) | ✅ Works - Server reads basic fields, ignores extra data |
| New (with HMAC) | New (with HMAC) | ✅ Works - Full HMAC authentication supported |

## Conclusion

This implementation successfully extends RegisterTMRequest with HMAC authentication capabilities while maintaining full backward compatibility with existing deployments. The design allows for gradual migration from non-authenticated to authenticated communication.
