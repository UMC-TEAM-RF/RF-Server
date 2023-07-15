package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public User createUser(String userId, String nickName, String password, String university, String phoneNumber
            , String interestingLanguage, String introduce, String country, String mbti, int entrance) {
        this.userId= userId;
        this.nickName = nickName;
        this.password = password;
        this.university = university;
        this.phoneNumber = phoneNumber;
        this.interestingLanguage = interestingLanguage;
        this.introduce = introduce;
        this.country = country;
        this.mbti = mbti;
        this.entrance = entrance;
        this.like = 0;
        this.dislike = 0;
        this.createdDate = LocalDateTime.now();
        return this;
    }
}
