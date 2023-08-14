package org.rf.rfserver.party.dto.party;

import lombok.Builder;
import lombok.Getter;
import org.rf.rfserver.constant.*;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.user.dto.GetUserProfileRes;
import org.rf.rfserver.user.dto.GetUserRes;

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
    private Long ownerId;
    private List<Rule> rules;
    private List<Interest> interests;
    private List<Schedule> schedules;
    private List<GetUserProfileRes> userProfiles;


    @Builder
    public GetPartyRes(Long id, String name, String content, String location, Language language, String imageFilePath, PreferAges preferAges,
                       LocalDateTime createdDate, int memberCount, int nativeCount, Long ownerId, List<Rule> rules, List<Schedule> schedules,
                       List<GetUserProfileRes> userProfiles, List<Interest> interests) {
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
        this.userProfiles = userProfiles;
    }
}
