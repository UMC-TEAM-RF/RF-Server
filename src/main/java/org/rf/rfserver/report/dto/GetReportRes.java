package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetReportRes {
    private Long reportId;
    private Long reporterId;
    private String reporterName;
    private Long actorId;
    private String actorName;
    private String content;
    private String reportType;
    private LocalDateTime createdAt;
}
