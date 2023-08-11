package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.ReportType;

@Getter
@AllArgsConstructor
public class PostReportReq {
    private Long reporterId;
    private Long reportedUserId;
    private Long reportedPartyId;
    private Long reportedChatId;
    private String content;
    private ReportType reportType;
}
