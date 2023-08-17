package org.rf.rfserver.apns.service;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ApnsClientService {
    private ApnsClient apnsClient;
    public ApnsClientService(@Value("${env.APNS_AUTH_KEY_PATH}") String authKeyPath, @Value("${env.APNS_TEAM_ID}") String apnsTeamId, @Value("${env.APNS_KEY_ID}") String apnsKeyId) {
        try {
            final ApnsClientBuilder apnsClientBuilder = new ApnsClientBuilder()
//                    .setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST) // 배포용
                    .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST) // 개발용
                    .setSigningKey(ApnsSigningKey.loadFromPkcs8File(new File(authKeyPath), apnsTeamId, apnsKeyId));
            apnsClient = apnsClientBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ApnsClient getApnsClient() {
        return apnsClient;
    }
}
