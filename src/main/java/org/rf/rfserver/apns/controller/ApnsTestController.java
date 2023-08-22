package org.rf.rfserver.apns.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.apns.dto.PushDto;
import org.rf.rfserver.apns.service.ApnsService;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.device.service.DeviceTokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apns")
public class ApnsTestController {
    private final ApnsService apnsService;
    private final DeviceTokenService deviceTokenService;
    @PutMapping("/test-setTestDeviceToken/{userId}/{deviceToken}")
    public BaseResponse<Boolean> setDeviceToken(@PathVariable Long userId, @PathVariable String deviceToken) {
        deviceTokenService.setDeviceToken(userId, deviceToken); // 테스트용 device token 입력 하여 사용
        return new BaseResponse<>(true);
    }
    @PostMapping("/test")
    public BaseResponse<Boolean> sendPush(@RequestBody PushDto pushDto) {
        apnsService.sendPush(pushDto);
        return new BaseResponse<>(true);
    }
}
