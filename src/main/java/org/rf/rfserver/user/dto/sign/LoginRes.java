package org.rf.rfserver.user.dto.sign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class loginRes {
    String accessToken;
    String refreshToken;

    public loginRes(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}