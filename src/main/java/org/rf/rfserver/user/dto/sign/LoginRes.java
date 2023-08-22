package org.rf.rfserver.user.dto.sign;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.constant.*;

import java.util.List;

@Getter
@Setter
public class LoginRes {
    private String accessToken;
    private String refreshToken;
    private UserDemo userDemo;

    public LoginRes(String accessToken, String refreshToken, String nickName, University university, List<Language> interestingLanguages, String introduce,
                    Country country, Mbti mbti, int entrance, String email, List<Country> interestCountries, List<Interest> interests, LifeStyle lifeStyle,
                    String imageFilePath, Long userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        userDemo = new UserDemo(nickName, university, interestingLanguages, introduce, country, mbti, entrance,
                email, interestCountries, interests, lifeStyle, imageFilePath, userId);
    }
}