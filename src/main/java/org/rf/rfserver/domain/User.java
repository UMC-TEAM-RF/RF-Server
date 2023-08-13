package org.rf.rfserver.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.rf.rfserver.constant.*;

import java.util.ArrayList;
import java.util.List;

import static org.rf.rfserver.constant.RfRule.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String password;
    private String nickName;
    @Enumerated(EnumType.STRING)
    private University university;
    @Enumerated(EnumType.STRING)
    private List<Language> interestingLanguages;
    private String introduce;
    @Enumerated(EnumType.STRING)
    private Country country;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    private int entrance;
    private int love;
    private int hate;
    private String email;
    private Boolean isEmailVerified;
    private String imageFilePath;

    @Enumerated(EnumType.STRING)
    private List<Country> interestCountries;
    @Enumerated(EnumType.STRING)
    private List<Interest> userInterests;
    @Enumerated(EnumType.STRING)
    private LifeStyle lifeStyle;
    @OneToMany(mappedBy = "user")
    private List<UserParty> userParties;

    @OneToMany(mappedBy = "blockerUser")
    @JsonManagedReference // 이 엔티티를 직렬화 할 때 관련된 BlockParty 엔티티를 포함
    private List<BlockParty> blockedParties;


    @Builder
    public User(String loginId, String password, int entrance, University university, String nickName
            , Country country, List<Language> interestingLanguages, String introduce, Mbti mbti
            , String email, List<Country> interestCountries, List<Interest> userInterests, LifeStyle lifeStyle) {
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
        this.email = email;
        this.imageFilePath = "default";
        this.interestCountries = interestCountries;
        this.userInterests = userInterests;
        this.lifeStyle = lifeStyle;
        this.userParties = new ArrayList<>();
    }

    public User updateUser(String nickName, String password, String imageFilePath, List<Language> interestingLanguages, String introduce, Mbti mbti, LifeStyle lifeStyle) {
        this.nickName = nickName == null ? this.nickName : nickName;
        this.password = password == null ? this.password : password;
        this.imageFilePath = imageFilePath == null ? this.imageFilePath : imageFilePath;
        this.interestingLanguages = interestingLanguages == null ? this.interestingLanguages : interestingLanguages;
        this.introduce = introduce == null ? this.introduce : introduce;
        this.mbti = mbti == null ? this.mbti : mbti;
        this.lifeStyle = lifeStyle;
        return this;
    }

    public boolean isMoreThanFiveParties() {
        if (userParties.size() > maxPartyNumber) {
            return true;
        }
        return false;
    }

    public void addUserParty(UserParty userParty) {
        this.userParties.add(userParty);
        userParty.setUser(this);
    }

    public void removeUserParty(UserParty userParty) {
        this.userParties.remove(userParty);
        userParty.setUser(null);
    }
}

