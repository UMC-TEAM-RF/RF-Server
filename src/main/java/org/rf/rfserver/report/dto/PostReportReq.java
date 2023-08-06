package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReportReq {
    private Long reporterId;
    private Long repotedUserId;
    private Long reportedPartyId;
    private String content;
}
