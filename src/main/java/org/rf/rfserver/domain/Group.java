package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
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
    private String filePath;
    private String preferAges;
    private LocalDateTime createdDate;
    private int memberCount;
    private int nativeNumber;
    private int ownerId;

    @OneToMany(mappedBy = "group")
    private List<Rule> rule;
    @OneToMany(mappedBy = "group")
    private List<GroupInterestAnchor> groupInterestAnchor;
    @OneToMany(mappedBy = "group")
    private List<GroupTag> tags;
    @OneToMany(mappedBy = "group")
    private List<Schedule> schedules;
}
