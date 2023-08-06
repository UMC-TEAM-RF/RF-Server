package org.rf.rfserver.party.dto.party;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rf.rfserver.constant.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostPartyReq {
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
}
