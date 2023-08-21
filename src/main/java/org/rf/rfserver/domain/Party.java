package org.rf.rfserver.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.*;
import org.rf.rfserver.party.dto.party.PatchPartyReq;


import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    private String location;
    @Enumerated(EnumType.STRING)
    private Language language;
    private String imageFilePath;
    @Enumerated(EnumType.STRING)
    private PreferAges preferAges;
    private Integer memberCount;
    private Integer nativeCount;
    private Integer currentNativeCount;
    private Long ownerId;
    @Enumerated(EnumType.STRING)
    private List<Rule> rules;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass=Interest.class)
    @CollectionTable(name="party_interest")
    private List<Interest> interests;
    @OneToMany(mappedBy = "party")
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "party")
    @JsonBackReference
    private List<UserParty> users;

    @Builder
    public Party(String name, String content,String location, Language language, PreferAges preferAges,
                 int memberCount, int nativeCount, Long ownerId, List<Rule> rules, List<Interest> interests) {
        this.name = name;
        this.content = content;
        this.location = location;
        this.language = language;
        this.preferAges = preferAges;
        this.memberCount = memberCount;
        this.nativeCount = nativeCount;
        this.ownerId = ownerId;
        this.rules = rules;
        this.currentNativeCount = 0;
        this.interests = interests;
        this.schedules = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void updateParty(PatchPartyReq patchPartyReq) {
        this.location = patchPartyReq.getLocation() == null ? this.location : patchPartyReq.getLocation();
        this.content = patchPartyReq.getContent() == null ? this.content : patchPartyReq.getContent();
        this.language = patchPartyReq.getLanguage() == null ? this.language : patchPartyReq.getLanguage();
        this.imageFilePath = patchPartyReq.getImageFilePath() == null ? this.imageFilePath : patchPartyReq.getImageFilePath();
        this.preferAges = patchPartyReq.getPreferAges() == null ? this.preferAges : patchPartyReq.getPreferAges();
        this.memberCount = patchPartyReq.getMemberCount() == null ? this.memberCount : patchPartyReq.getMemberCount();
        this.nativeCount = patchPartyReq.getNativeCount() == null ? this.nativeCount : patchPartyReq.getNativeCount();
        this.rules = patchPartyReq.getRules() == null ? this.rules : patchPartyReq.getRules();
        this.interests = patchPartyReq.getInterests() == null ? this.interests : patchPartyReq.getInterests();
    }

    public void updateImageUrl(String imageFilePath){
        this.imageFilePath = imageFilePath;
    }

    public void plusCurrentNativeCount() {
        this.currentNativeCount++;
    }

    public void minusCurrentNativeCount() {
        this.currentNativeCount--;
    }

    public void addUserParty(UserParty userParty) {
        this.users.add(userParty);
        userParty.setParty(this);
    }

    public void removeUserParty(UserParty userParty) {
        this.users.remove(userParty);
        userParty.setParty(null);
    }
}
