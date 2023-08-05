package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party {
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
    private LocalDateTime createdDate;
    private int memberCount;
    private int nativeCount;
    private int ownerId;
    @Enumerated(EnumType.STRING)
    private List<Rule> rules;
    @Enumerated(EnumType.STRING)
    private List<Interest> interests;
    @OneToMany(mappedBy = "party")
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "party")
    private List<UserParty> users;

    @Builder
    public Party(String name, String content,String location, Language language, String imageFilePath, PreferAges preferAges,
                 LocalDateTime createdDate, int memberCount, int nativeCount, int ownerId, List<Rule> rules, List<Interest> interests) {
        this.name = name;
        this.content = content;
        this.location = location;
        this.language = language;
        this.imageFilePath = imageFilePath;
        this.preferAges = preferAges;
        this.createdDate = createdDate;
        this.memberCount = memberCount;
        this.nativeCount = nativeCount;
        this.ownerId = ownerId;
        this.rules = rules;
        this.interests = interests;
        this.schedules = new ArrayList<>();
        this.users = new ArrayList<>();
    }
}
