package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
    @Id @GeneratedValue
    @Column(name = "group_id")
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
    @JoinColumn(name = "GroupRule")
    private List<Rule> rule;
    @OneToMany(mappedBy = "group")
    private List<GroupGroupInterest> groupGroupInterests;
    @OneToMany()
    private List<GroupTag> tags;
    @OneToMany()
    private List<Schedule> schedules;
}
