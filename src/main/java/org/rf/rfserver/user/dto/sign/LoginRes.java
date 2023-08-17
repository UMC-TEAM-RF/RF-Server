package org.rf.rfserver.user.dto.sign;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.constant.*;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginRes {
    String accessToken;
    String refreshToken;
    private String nickName;
    private University university;
    private List<Language> interestingLanguages;
    private String introduce;
    private Country country;
    private Mbti mbti;
    private int entrance;
    private String email;
    private List<Country> interestCountries;
    private List<Interest> interests;
    private LifeStyle lifeStyle;

    public LoginRes(String accessToken, String refreshToken, String nickName, University university, List<Language> interestingLanguages, String introduce,
                    Country country, Mbti mbti, int entrance, String email, List<Country> interestCountries, List<Interest> interests, LifeStyle lifeStyle) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.nickName = nickName;
        this.university = university;
        this.interestingLanguages = interestingLanguages;
        this.introduce = introduce;
        this.country = country;
        this.mbti = mbti;
        this.entrance = entrance;
        this.email = email;
        this.interestCountries = interestCountries;
        this.interests = interests;
        this.lifeStyle = lifeStyle;
    }
}