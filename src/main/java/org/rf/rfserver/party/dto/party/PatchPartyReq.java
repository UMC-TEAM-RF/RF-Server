package org.rf.rfserver.party.dto.party;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.constant.Language;
import org.rf.rfserver.constant.PreferAges;
import org.rf.rfserver.constant.Rule;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchPartyReq {
    private String content = null;
    private String location = null;
    private Language language = null;
    private String imageFilePath = null;
    private PreferAges preferAges = null;
    private Integer memberCount = null;
    private Integer nativeCount = null;
    private List<Rule> rules = null;
    private List<Interest> interests = null;
}
