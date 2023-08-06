package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
    private String language;
    private String imageFilePath;
    private String preferAges;
    private int memberCount;
    private int nativeCount;
    private int ownerId;
//    @OneToMany
//    @JoinColumn(name = "PartyRule")
//    private List<PartyRule> rule;
//    @OneToMany
//    @JoinTable(name = "Tag"
//            , joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "party"))
//    private List<PartyTag> tags;
    @OneToMany(mappedBy = "party")
    private List<PartyInterest> interests;
    @OneToMany(mappedBy = "party")
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "party")
    private List<UserParty> users;

    @Builder
    public Party(Long id, String name, String content,String location, String language, String imageFilePath, String preferAges,
                 int memberCount, int nativeCount, int ownerId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.location = location;
        this.language = language;
        this.imageFilePath = imageFilePath;
        this.preferAges = preferAges;
        this.memberCount = memberCount;
        this.nativeCount = nativeCount;
        this.ownerId = ownerId;
        this.interests = new ArrayList<>();
        this.schedules = new ArrayList<>();
        this.users = new ArrayList<>();
        //this.rule = rule;
        //this.tags = tags;
    }

    public void updateImageUrl(String imageFilePath){
        this.imageFilePath = imageFilePath;
    }
}
