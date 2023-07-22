package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private String name;
    private String loginId;
    private String password;
    private String nickName;
    private String phoneNumber;
    private String interestingLanguage;
    private String introduce;
    private String useLanguage;
    private String mbti;
    private int entrance;
    private int love;
    private int hate;
    private LocalDateTime createdDate;
    private Boolean isEmailVerification;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = true)
    private University university;

    @OneToMany(mappedBy = "user")
    private List<InterestCountry> interestCountries;
    @OneToMany(mappedBy = "user")
    private List<UserInterest> userInterest;


    @Builder
    public void setEmailAndUniversity(String email, University university) {
        this.email = email;
        this.isEmailVerification = true;
        this.university = university;
    }
}
