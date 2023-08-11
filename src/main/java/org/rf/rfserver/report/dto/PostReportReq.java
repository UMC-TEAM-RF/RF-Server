package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.ReportType;

@Getter
@AllArgsConstructor
public class PostReportReq {
    private Long reporterId;
    private Long actorId;
    private Long actorPartyId;
    private String content;
    private ReportType reportType;
}
