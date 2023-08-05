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
    private String languageName;
    private String imageFilePath;
    private String preferAgesName;
    private LocalDateTime createdDate;
    private int memberCount;
    private int nativeCount;
    private int ownerId;
    private List<String> ruleNames;
    private List<String> interestNames;
    private List<Schedule> schedules;
    private List<UserParty> users;


    @Builder
    public GetPartyRes(Long id, String name, String content, String location, String languageName, String imageFilePath, String preferAgesName,
                       LocalDateTime createdDate, int memberCount, int nativeCount, int ownerId, List<String> ruleNames, List<Schedule> schedules,
                       List<UserParty> users, List<String> interestNames) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.location = location;
        this.languageName = languageName;
        this.imageFilePath = imageFilePath;
        this.preferAgesName = preferAgesName;
        this.createdDate = createdDate;
        this.memberCount = memberCount;
        this.nativeCount = nativeCount;
        this.ownerId = ownerId;
        this.ruleNames = ruleNames;
        this.interestNames = interestNames;
        this.schedules = schedules;
        this.users = users;
    }
}
