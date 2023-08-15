package org.rf.rfserver.user.dto.sign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRes {
    String accessToken;
    String refreshToken;

    public LoginRes(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}