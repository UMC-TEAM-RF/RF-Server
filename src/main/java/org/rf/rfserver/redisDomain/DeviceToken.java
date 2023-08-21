package org.rf.rfserver.redisDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@AllArgsConstructor
@RedisHash("userDeviceToken")
public class DeviceToken {
    @Id
    private Long userId;
    private String deviceToken;
}
