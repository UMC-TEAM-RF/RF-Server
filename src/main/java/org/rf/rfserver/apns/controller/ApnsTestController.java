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
    @PutMapping("/test-setTestDeviceToken")
    public BaseResponse<Boolean> setDeviceToken() {
        deviceTokenService.setDeviceToken(1L,(String)""); // 테스트용 device token 입력 하여 사용
        return new BaseResponse<>(true);
    }
    @PostMapping("/test")
    public BaseResponse<Boolean> sendPush(@RequestBody PushDto pushDto) {
        return new BaseResponse<>(apnsService.sendPush(pushDto));
    }
}
