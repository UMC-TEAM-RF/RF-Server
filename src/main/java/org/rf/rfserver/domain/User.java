package org.rf.rfserver.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.rf.rfserver.constant.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.rf.rfserver.constant.RfRule.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {

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
    private String imageUrl;
    private Boolean isEmailVerified;
    private String imageFilePath;
    private String deviceToken;
    private Major major;

    @Enumerated(EnumType.STRING)
    private List<Country> interestCountries;
    @Enumerated(EnumType.STRING)
    private List<Interest> userInterests;
    @Enumerated(EnumType.STRING)
    private LifeStyle lifeStyle;
    @OneToMany(mappedBy = "user")
    private List<UserParty> userParties;
    @OneToMany(mappedBy = "user")
    private List<FavoriteParty> favoriteParties;

    @OneToMany(mappedBy = "blockerUser")
    @JsonManagedReference // 이 엔티티를 직렬화 할 때 관련된 BlockParty 엔티티를 포함
    private List<BlockParty> blockedParties;



    @Builder
    public User(String loginId, String password, int entrance, University university, String nickName
            , Country country, List<Language> interestingLanguages, String introduce, Mbti mbti
            , String email, List<Country> interestCountries, List<Interest> userInterests, LifeStyle lifeStyle, Major major) {
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

    public User updateUser(String nickName, String password, String imageFilePath, List<Language> interestingLanguages, String introduce, Mbti mbti,
                           LifeStyle lifeStyle, Major major) {
        this.nickName = nickName == null ? this.nickName : nickName;
        this.password = password == null ? this.password : password;
        this.imageFilePath = imageFilePath == null ? this.imageFilePath : imageFilePath;
        this.interestingLanguages = interestingLanguages == null ? this.interestingLanguages : interestingLanguages;
        this.introduce = introduce == null ? this.introduce : introduce;
        this.mbti = mbti == null ? this.mbti : mbti;
        this.lifeStyle = lifeStyle;
        this.major = major;
        return this;
    }

    public void updateImageUrl(String imageFilePath){
        this.imageFilePath = imageFilePath;
    }

    public boolean isMoreThanLimitedPartyNumber() {
        if (userParties.size() > MAX_PARTY_NUMBER) {
            return true;
        }
        return false;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


    public void addUserParty(UserParty userParty) {
        this.userParties.add(userParty);
        userParty.setUser(this);
    }

    public void removeUserParty(UserParty userParty) {
        this.userParties.remove(userParty);
        userParty.setUser(null);
    }

    @Override //사용자가 가지고 있는 권한의 목록 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override //사용자를 식별할 수 있는 사용자 이름을 반환
    public String getUsername() {
        return loginId;
    }

    @Override // 사용자 비밀번호 반환
    public String getPassword() {
        return password;
    }

    @Override // 계정이 만료되었는지
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //계정이 잠금 되었는지
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //비밀번호가 만료 되었는지
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 사용 가능한지
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void increaseLike() {
        this.love++;
    }

    public void decreaseLike() {
        if(this.love > 0) {
            this.love--;
        }
    }

    public void increaseHate() {
        this.hate++;
    }

    public void decreaseHate() {
        if(this.hate > 0) {
            this.hate--;
        }
    }
}

