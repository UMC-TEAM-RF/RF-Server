package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.*;

import java.util.List;
@Getter
@AllArgsConstructor
public class PostUserReq {
    private String loginId;
    private String password;
    private String profileImage;
    private int entrance;
    private University university;
    private String nickName;
    private Country country;
    private List<Language> interestingLanguages;
    private String introduce;
    private Mbti mbti;
    private String email;
    private List<Country> interestCountries;
    private List<Interest> interests;
    private LifeStyle lifeStyle;
}
