package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchUserReq {
    private String nickName = null;
    private String password = null;
    private String interestingLanguage = null;
    private String introduce = null;
    private String mbti = null;
}
