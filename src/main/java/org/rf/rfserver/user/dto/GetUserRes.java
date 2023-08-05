package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.Country;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.constant.Language;
import org.rf.rfserver.user.enums.Mbti;
import org.rf.rfserver.user.enums.University;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetUserRes {
    private String nickName;
    private String universityName;
    private List<String> interestingLanguageNames;
    private String introduce;
    private String countryName;
    private String mbtiName;
    private int entrance;
    private String email;
    private List<String> interestCountrieNames;
    private List<String> interestNames;
}
