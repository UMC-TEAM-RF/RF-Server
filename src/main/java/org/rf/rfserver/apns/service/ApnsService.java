package org.rf.rfserver.apns.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.apns.dto.PushDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApnsService {
    private final PushNotificationService notificationService;
    @Async
    public void sendPush(PushDto pushDto) {
        String payload = notificationService.buildPushNotificatoinPayload(pushDto.getType(), pushDto.getTitle(), pushDto.getSubTitle(), pushDto.getBody(), pushDto.getPartyId());
        log.info("Push Content : {}", payload);
        notificationService.sendPushNotificationToUser(pushDto.getUserId(), payload);
    }
}