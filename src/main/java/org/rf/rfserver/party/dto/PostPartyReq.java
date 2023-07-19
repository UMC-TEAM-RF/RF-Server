package org.rf.rfserver.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.domain.PartyPartyInterest;
import org.rf.rfserver.domain.PartyRule;
import org.rf.rfserver.domain.PartyTag;
import org.rf.rfserver.domain.UserParty;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostPartyReq {
    private String name;
    private String content;
    private String location;
    private String language;
    private String imageFilePath;
    private String preferAges;
    private int memberCount;
    private int nativeCount;
    private int ownerId;

//    private List<PartyTag> tags;
//    private List<PartyRule> rules;
//    private List<PartyPartyInterest> groupGroupInterests;
}
