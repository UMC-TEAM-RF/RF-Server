package org.rf.rfserver.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.Constant.Interest;

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
    private List<Interest> interests;

//    private List<PartyTag> tags;
//    private List<PartyRule> rules;
}
