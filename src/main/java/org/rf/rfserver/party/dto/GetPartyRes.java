package org.rf.rfserver.party.dto;

import lombok.Builder;
import lombok.Getter;
import org.rf.rfserver.constant.*;
import org.rf.rfserver.domain.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetPartyRes {
    private Long id;
    private String name;
    private String content;
    private String location;
    private Language language;
    private String imageFilePath;
    private PreferAges preferAges;
    private LocalDateTime createdDate;
    private int memberCount;
    private int nativeCount;
    private int ownerId;
    private List<Rule> rules;
    private List<Interest> interests;

    private List<Schedule> schedules;
    private List<GetPartyUserRes> users;


    @Builder
    public GetPartyRes(Long id, String name, String content, String location, Language language, String imageFilePath, PreferAges preferAges,
                       LocalDateTime createdDate, int memberCount, int nativeCount, int ownerId, List<Rule> rules, List<Schedule> schedules,
                       List<GetPartyUserRes> users, List<Interest> interests) {
        this.id = id;
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
        this.schedules = schedules;
        this.users = users;
    }

}
