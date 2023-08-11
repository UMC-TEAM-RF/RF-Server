package org.rf.rfserver.report.dto;

import lombok.Getter;
import org.rf.rfserver.constant.ReportType;
import org.rf.rfserver.domain.Report;

import java.time.LocalDateTime;

import static org.rf.rfserver.constant.ReportType.*;

@Getter
public class GetReportRes {
    private Long reportId;

    private Long reporterId;
    private String reporterName;

    private Long actorId;
    private String actorName;

    private Long actorPartyId;
    private String actorPartyName;

    private String content;
    private ReportType reportType;
    private LocalDateTime createdAt;
    public GetReportRes(Report report) {
        if(report.getType() == USER) userReportConstructor(report);
        if(report.getType() == PARTY) partyReportConstructor(report);
    }

    /**
     * USER 타입 생성자
     * @param report
     */
    private void userReportConstructor(Report report) {
        this.reportId = report.getId();
        this.reporterId = report.getReporter().getId();
        this.reporterName = report.getReporter().getNickName();
        this.actorId = report.getActor().getId();
        this.actorName = report.getActor().getNickName();
        this.content = report.getContent();
        this.reportType = report.getType();
        this.createdAt = report.getCreatedAt();
    }

    /**
     * PARTY 타입 생성자
     * @param report
     */
    public void partyReportConstructor(Report report) {
        this.reportId = report.getId();
        this.reporterId = report.getReporter().getId();
        this.reporterName = report.getReporter().getNickName();
        this.actorPartyId = report.getActorParty().getId();
        this.actorPartyName = report.getActorParty().getName();
        this.content = report.getContent();
        this.reportType = report.getType();
        this.createdAt = report.getCreatedAt();
    }
}
