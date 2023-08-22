package org.rf.rfserver.apns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.constant.PushNotificationType;

@Getter
@Setter
@AllArgsConstructor
public class PushDto {
    private PushNotificationType type;
    private Long userId; // 받는 사용자 id
    private String title;
    private String subTitle;
    private String body;
    private Long partyId; // CHAT, APPROVE 용
}
