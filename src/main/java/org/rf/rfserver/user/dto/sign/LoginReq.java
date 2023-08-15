package org.rf.rfserver.user.dto.sign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class loginReq {
    private String loginId;
    private String password;
    private String deviceToken;
}
