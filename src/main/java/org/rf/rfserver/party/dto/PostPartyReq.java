package org.rf.rfserver.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.constant.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostPartyReq {
    private String name;
    private String content;
    private String location;
    private Language language;
    private String imageFilePath;
    private PreferAges preferAges;
    private int memberCount;
    private int nativeCount;
    private int ownerId;
    private List<Rule> rules;
    private List<Interest> interests;
}
