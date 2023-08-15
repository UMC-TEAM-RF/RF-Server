package org.rf.rfserver.user.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    private String loginId;
    private String password;
    private String deviceToken;
}
