package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.domain.University;

@AllArgsConstructor
@Getter
public class GetUserRes {
    private String nickName;
    private University university;
    private String interestingLanguage;
    private String introduce;
    private String country;
    private String mbti;
    private int entrance;
}
