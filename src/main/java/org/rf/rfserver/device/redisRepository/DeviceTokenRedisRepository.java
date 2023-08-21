package org.rf.rfserver.device.redisRepository;

import org.rf.rfserver.redisDomain.DeviceToken;
import org.springframework.data.repository.CrudRepository;

public interface DeviceTokenRedisRepository extends CrudRepository<DeviceToken, Long> {
}
