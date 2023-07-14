package org.rf.rfserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String userId;
    private String nickName;
    private String password;
    private String university;
    private String phoneNumber;
    private String interestingLanguage;
    private String introduce;
    private String useLanguage;
    private String mbti;
    private int entrance; // -> int? String? LocalDate?
    private int monthlyMeetNumber;
    private int like;
    private int dislike;
    private LocalDateTime createdDate;

    @OneToMany
    private List<InterestingNation> interestingNations;
    @OneToMany
    private List<UserInterest> userInterest;
}
