package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.*;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetUserRes {
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
    private String imageFilePath;
}
