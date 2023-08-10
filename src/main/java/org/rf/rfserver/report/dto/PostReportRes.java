package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReportRes {
    private Long reportId;
    private String actorName;
    private String content;
    private String reportType;
}
