package org.rf.rfserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
    @Id @GeneratedValue
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

    @OneToMany
    private List<Rule> rule;
    @OneToMany
    private List<GroupInterestAnchor> groupInterestAnchor; // 객체
    @OneToMany
    private List<GroupTag> tags;
}
