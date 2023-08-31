package org.rf.rfserver.device.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.device.redisRepository.DeviceTokenRedisRepository;
import org.rf.rfserver.redisDomain.DeviceToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceTokenService {
    private final DeviceTokenRedisRepository deviceTokenRedisRepository;
    public Boolean setDeviceToken(Long userId, String deviceToken) {
        deviceTokenRedisRepository.save(new DeviceToken(userId, deviceToken));
        return true;
    }
    public String getDeviceTokenByUserId(Long userId) {
        try {
            return deviceTokenRedisRepository.findById(userId).get().getDeviceToken();
        } catch(Exception e) {
            log.info("No Device Token for : " + userId);
            return null;
        }
    }
    public Boolean deleteDeviceTokenByUserId(Long userId) {
        deviceTokenRedisRepository.deleteById(userId);
        return true;
    }
}
