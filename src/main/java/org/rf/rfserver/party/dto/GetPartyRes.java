package org.rf.rfserver.party.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.rf.rfserver.domain.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetPartyRes {
    private Long id;
    private String name;
    private String content;
    private String location;
    private String language;
    private String imageFilePath;
    private String preferAges;
    private LocalDateTime createdDate;
    private int memberCount;
    private int nativeCount;
    private int ownerId;
    private List<Schedule> schedules;
    private List<UserParty> users;

    //    private List<PartyRule> rule;
//    private List<PartyPartyInterest> groupGroupInterests;
//    private List<PartyTag> tags;

    @Builder
    public GetPartyRes(Long id, String name, String content, String location, String language, String imageFilePath, String preferAges,
                       LocalDateTime createdDate, int memberCount, int nativeCount, int ownerId, List<Schedule> schedules, List<UserParty> users) {
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
//        this.rule = rule;
//        this.groupGroupInterests = groupGroupInterests;
//        this.tags = tags;
        this.schedules = schedules;
        this.users = users;
    }
}
