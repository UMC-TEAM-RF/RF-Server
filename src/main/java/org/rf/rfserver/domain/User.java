package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.constant.Country;
import org.rf.rfserver.constant.Language;
import org.rf.rfserver.user.enums.Mbti;
import org.rf.rfserver.user.enums.University;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String password;
    private String nickName;
    private University university;
    @Enumerated(EnumType.STRING)
//    @ElementCollection(fetch = FetchType.LAZY) -> 해당 필드의 테이블 생성하여 관리함
    private List<Language> interestingLanguages;
    private String introduce;
    @Enumerated(EnumType.STRING)
    private Country country;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    private int entrance;
    private int love;
    private int hate;
    private LocalDateTime createdDate;
    private String email;
    private Boolean isEmailVerified;

    @Enumerated(EnumType.STRING)
//    @ElementCollection(fetch = FetchType.LAZY)
    private List<Country> interestCountries;
    @Enumerated(EnumType.STRING)
//    @ElementCollection(fetch = FetchType.LAZY)
    private List<Interest> userInterests;

    @Builder
    public User(String loginId, String password, int entrance, University university, String nickName
            , Country country, List<Language> interestingLanguages, String introduce, Mbti mbti
            , String email, List<Country> interestCountries, List<Interest> userInterests) {
        this.loginId= loginId;
        this.password = password;
        this.entrance = entrance;
        this.university = university;
        this.nickName = nickName;
        this.country = country;
        this.interestingLanguages = interestingLanguages;
        this.introduce = introduce;
        this.mbti = mbti;
        this.love = 0;
        this.hate = 0;
        this.createdDate = LocalDateTime.now();
        this.email = email;
        this.interestCountries = interestCountries;
        this.userInterests = userInterests;
    }

    public User updateUser(String nickName, String password, List<Language> interestingLanguages, String introduce, Mbti mbti) {
        this.nickName = nickName == null ? this.nickName : nickName;
        this.password = password == null ? this.password : password;
        this.interestingLanguages = interestingLanguages == null ? this.interestingLanguages : interestingLanguages;
        this.introduce = introduce == null ? this.introduce : introduce;
        this.mbti = mbti == null ? this.mbti : mbti;
        return this;
    }
}
