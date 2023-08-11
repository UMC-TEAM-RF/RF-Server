package org.rf.rfserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.ReportType;
import org.rf.rfserver.domain.Report;

import static org.rf.rfserver.constant.ReportType.*;

@Getter
public class PostReportRes {
    private Long reportId;
    private String actorName;
    private String actorPartyName;
    private String content;
    private ReportType reportType;
    public PostReportRes(Report report) {
        if(report.getType() == USER) userPostReportResConstructor(report);
        if(report.getType() == PARTY) partyPostReportResConstructor(report);
    }

    private void userPostReportResConstructor(Report report) {
        this.reportId = report.getId();
        this.actorName = report.getActor().getNickName();
        this.content = report.getContent();
        this.reportType = report.getType();
    }

    private void partyPostReportResConstructor(Report report) {
        this.reportId = report.getId();
        this.actorPartyName = report.getActorParty().getName();
        this.content = report.getContent();
        this.reportType = report.getType();
    }
}
