package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String university;
    private String phoneNumber;
    private String interestingLanguage;
    private String introduce;
    private String country;
    private String mbti;
    private int entrance;
    private int love;
    private int hate;
    private LocalDateTime createdDate;
    private String email;
    private Boolean isEmailVerified;

    @OneToMany(mappedBy = "user")
    private List<InterestCountry> interestCountries;
    @OneToMany(mappedBy = "user")
    private List<UserInterest> userInterest;
    @OneToMany(mappedBy = "user")
    private List<UserParty> userParties;

    public User(String loginId, String password, int entrance, String university, String nickName
            , String country, String interestingLanguage, String introduce, String mbti) {
        this.loginId= loginId;
        this.password = password;
        this.entrance = entrance;
        this.university = university;
        this.nickName = nickName;
        this.country = country;
        this.interestingLanguage = interestingLanguage;
        this.introduce = introduce;
        this.mbti = mbti;
        this.love = 0;
        this.hate = 0;
        this.createdDate = LocalDateTime.now();
    }
    public User updateUser(String nickName, String password, String interestingLanguage, String introduce, String mbti) {
        this.nickName = nickName == null ? this.nickName : nickName;
        this.password = password == null ? this.password : password;
        this.interestingLanguage = interestingLanguage == null ? this.interestingLanguage : interestingLanguage;
        this.introduce = introduce == null ? this.introduce : introduce;
        this.mbti = mbti == null ? this.mbti : mbti;
        return this;
    }
}
