package org.rf.rfserver.device.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.device.redisRepository.DeviceTokenRedisRepository;
import org.rf.rfserver.redisDomain.DeviceToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {
    private final DeviceTokenRedisRepository deviceTokenRedisRepository;
    public Boolean setDeviceToken(Long userId, String deviceToken) {
        deviceTokenRedisRepository.save(new DeviceToken(userId, deviceToken));
        return true;
    }
    public String getDeviceTokenByUserId(Long userId) {
        return deviceTokenRedisRepository.findById(userId).get().getDeviceToken();
    }
    public Boolean deleteDeviceTokenByUserId(Long userId) {
        deviceTokenRedisRepository.deleteById(userId);
        return true;
    }
}
