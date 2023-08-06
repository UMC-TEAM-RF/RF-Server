package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetReportActorRes {
    private Long reportId;
    private Long reporterId;
    private String reporterName;
    private String content;
    private LocalDateTime createdAt;
}
