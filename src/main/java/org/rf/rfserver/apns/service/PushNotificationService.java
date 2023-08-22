package org.rf.rfserver.apns.service;

import com.eatthepath.json.JsonParser;
import com.eatthepath.json.JsonSerializer;
import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;
import com.eatthepath.pushy.apns.util.concurrent.PushNotificationFuture;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.constant.PushNotificationType;
import org.rf.rfserver.device.service.DeviceTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.rf.rfserver.constant.PushNotificationType.APPROVE;
import static org.rf.rfserver.constant.PushNotificationType.CHAT;

@Service
@RequiredArgsConstructor
public class PushNotificationService {
    private final ApnsClientService apnsClientService;
    private final DeviceTokenService deviceTokenService;
    @Value("${env.APNS_TOPIC}")
    private String apnsTopic;
    public String buildPushNotificatoinPayload(PushNotificationType type, String title, String subTitle, String body, Object content) {
        String threadId = "default";
        if(type == CHAT)
            threadId = "party-" + Long.toString((Long)content);
        final ApnsPayloadBuilder payloadBuilder = new SimpleApnsPayloadBuilder();
        payloadBuilder
                .setAlertTitle(title)
                .setAlertSubtitle(subTitle)
                .setAlertBody(body)
                .setMutableContent(true)
                .setThreadId(threadId)
                .setSound(ApnsPayloadBuilder.DEFAULT_SOUND_FILENAME);
        String payload = payloadBuilder.build();
        JsonParser parser = new JsonParser();
        try {
            Map<String, Object> payloadMap = parser.parseJsonObject(payload);
            payloadMap.put("messageType", type.name());
            if(type == CHAT || type == APPROVE)
                payloadMap.put("partyId", (Long)content);
            payload = JsonSerializer.writeJsonTextAsString(payloadMap);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return payload;
    }
    public void sendPushNotificationToUser(Long userId, String payload) {
        String deviceToken = (String) deviceTokenService.getDeviceTokenByUserId(userId);
        sendPushNotification(deviceToken, payload);
    }
    public void sendPushNotification(String deviceToken, String payload) {
        deviceToken = "<" + deviceToken + ">";
        final String token = TokenUtil.sanitizeTokenString(deviceToken);
        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, apnsTopic, payload);
        ApnsClient apnsClient = apnsClientService.getApnsClient();
        PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture;
        try {
            PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse;
            do {
                pushNotificationResponse = apnsClient.sendNotification(pushNotification).get();
            } while(!pushNotificationResponse.isAccepted());
        } catch(ExecutionException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}