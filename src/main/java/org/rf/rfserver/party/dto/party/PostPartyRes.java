package org.rf.rfserver.party.dto.party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.constant.Language;
import org.rf.rfserver.constant.PreferAges;
import org.rf.rfserver.constant.Rule;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostPartyRes {
    private Long partyId;
    private String name;
    private String content;
    private String location;
    private Language language;
    private String imageFilePath;
    private PreferAges preferAges;
    private int memberCount;
    private int nativeCount;
    private Long ownerId;
    private List<Rule> rules;
    private List<Interest> interests;

    public PostPartyRes(Long partyId, PostPartyReq postPartyReq) {
        this.partyId = partyId;
        this.name = postPartyReq.getName();
        this.content = postPartyReq.getContent();
        this.location = postPartyReq.getLocation();
        this.language = postPartyReq.getLanguage();
        this.imageFilePath = postPartyReq.getImageFilePath();
        this.preferAges = postPartyReq.getPreferAges();
        this.memberCount = postPartyReq.getMemberCount();
        this.nativeCount = postPartyReq.getNativeCount();
        this.ownerId = postPartyReq.getOwnerId();
        this.rules = postPartyReq.getRules();
        this.interests = postPartyReq.getInterests();
    }
}
