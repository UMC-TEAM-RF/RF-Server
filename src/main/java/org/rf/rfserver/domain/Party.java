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
public class Party {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    private String guideLink;
    private String location;
    private String language;
    private String imageFilePath;
    private String preferAges;
    private LocalDateTime createdDate;
    private int memberCount;
    private int nativeCount;
    private int ownerId;

    @OneToMany
    @JoinColumn(name = "PartyRule")
    private List<Rule> rule;
    @OneToMany
    @JoinTable(name = "PartyInterest"
            , joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "party"))
    private List<PartyPartyInterest> partyPartyInterests;
    @OneToMany
    @JoinTable(name = "Tag"
            , joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "party"))
    private List<PartyTag> tags;
    @OneToMany
    @JoinTable(name = "Schedule"
            , joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "party"))
    private List<Schedule> schedules;
}
