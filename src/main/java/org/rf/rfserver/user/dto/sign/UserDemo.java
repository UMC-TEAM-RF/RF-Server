package org.rf.rfserver.user.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.constant.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class UserDemo {
    private String nickName;
    private University university;
    private List<Language> interestingLanguages;
    private String introduce;
    private Country country;
    private Mbti mbti;
    private Integer entrance;
    private String email;
    private List<Country> interestCountries;
    private List<Interest> interests;
    private LifeStyle lifeStyle;
    private String imageFilePath;
    private Long userId;
}
