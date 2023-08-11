package org.rf.rfserver.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreatePartyReq {
    private Long userId;
    private String name;
    private String content;
    private String location;
    private String language;
    private String imageFilePath;
    private String preferAges;
    private int memberCount;
    private int nativeCount;
    private int ownerId;
}


