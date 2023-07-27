package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserReq {
    private String loginId;
    private String password;
    private int entrance;
    private String university;
    private String nickName;
    private String country;
    private String interestingLanguage;
    private String introduce;
    private String mbti;
}
