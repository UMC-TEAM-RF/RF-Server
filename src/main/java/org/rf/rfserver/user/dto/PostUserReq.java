package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.domain.University;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostUserReq {
    private String loginId;
    private String password;
    private int entrance;
    private University university;
    private String nickName;
    private String country;
    private String interestingLanguage;
    private String introduce;
    private String mbti;
}
