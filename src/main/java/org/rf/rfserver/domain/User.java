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

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String userId;
    private String nickName;
    private String password;
    private String university;
    private String phoneNumber;
    private String interestingLanguage;
    private String introduce;
    private String country;
    private String mbti;
    private int entrance;
    private int like;
    private int dislike;
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "user")
    private List<InterestCountry> interestCountries;
    @OneToMany(mappedBy = "user")
    private List<UserInterest> userInterest;

    public User(String userId, String password, int entrance, String university, String nickName
            , String country, String interestingLanguage, String introduce, String mbti) {
        this.userId= userId;
        this.password = password;
        this.entrance = entrance;
        this.university = university;
        this.nickName = nickName;
        this.country = country;
        this.interestingLanguage = interestingLanguage;
        this.introduce = introduce;
        this.mbti = mbti;
        this.like = 0;
        this.dislike = 0;
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
