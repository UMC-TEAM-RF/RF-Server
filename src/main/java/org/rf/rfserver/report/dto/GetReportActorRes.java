package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetReportActorRes {
    private Long reporterId;
    private String reporterName;
}
