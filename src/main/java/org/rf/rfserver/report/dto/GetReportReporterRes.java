package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetReportReporterRes {
    private Long actorId;
    private String actorName;
    private String reportType;
}
