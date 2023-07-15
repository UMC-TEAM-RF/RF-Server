package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUserRes {
    private String nickName;
    private String university;
    private String interestingLanguage;
    private String introduce;
    private String country;
    private String mbti;
    private int entrance;
}
