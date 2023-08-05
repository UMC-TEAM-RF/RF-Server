package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.Country;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.constant.Language;
import org.rf.rfserver.constant.Mbti;
import org.rf.rfserver.constant.University;

import java.util.List;
@Getter
@AllArgsConstructor
public class PostUserReq {
    private String loginId;
    private String password;
    private int entrance;
    private University university;
    private String nickName;
    private Long countryCode;
    private List<Language> interestingLanguages;
    private String introduce;
    private Mbti mbti;
    private String email;
    private List<Country> interestCountries;
    private List<Interest> interests;
}
