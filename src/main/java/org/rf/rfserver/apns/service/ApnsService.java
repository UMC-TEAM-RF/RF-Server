package org.rf.rfserver.apns.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.apns.dto.PushDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApnsService {
    private final PushNotificationService notificationService;
    public Boolean sendPush(PushDto pushDto) {
        String payload = notificationService.buildPushNotificatoinPayload(pushDto.getType(), pushDto.getTitle(), pushDto.getBody(), pushDto.getPartyId());
        notificationService.sendPushNotificationToUser(pushDto.getUserId(), payload);
        return true;
    }
}
