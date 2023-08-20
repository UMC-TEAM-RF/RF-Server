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

    private Long reportedUserId;
    private String reportedUserName;

    private Long reportedPartyId;
    private String reportedPartyName;

    private Long reportedChatId;
    private String reportedChatContent;

    private String content;
    private ReportType reportType;
    private LocalDateTime createdAt;
    public GetReportRes(Report report) {
        if(report.getType() == USER) userReportConstructor(report);
        if(report.getType() == PARTY) partyReportConstructor(report);
        if(report.getType() == CHAT) chatReportConstructor(report);
    }

    /**
     * USER 타입 생성자
     * @param report
     */
    private void userReportConstructor(Report report) {
        this.reportId = report.getId();
        this.reporterId = report.getReporter().getId();
        this.reporterName = report.getReporter().getNickName();
        this.reportedUserId = report.getReportedUser().getId();
        this.reportedUserName = report.getReportedUser().getNickName();
        this.content = report.getContent();
        this.reportType = report.getType();
        this.createdAt = report.getCreatedAt();
    }

    /**
     * PARTY 타입 생성자
     * @param report
     */
    private void partyReportConstructor(Report report) {
        this.reportId = report.getId();
        this.reporterId = report.getReporter().getId();
        this.reporterName = report.getReporter().getNickName();
        this.reportedPartyId = report.getReportedParty().getId();
        this.reportedPartyName = report.getReportedParty().getName();
        this.content = report.getContent();
        this.reportType = report.getType();
        this.createdAt = report.getCreatedAt();
    }
    private void chatReportConstructor(Report report) {
        this.reportId = report.getId();
        this.reporterId = report.getReporter().getId();
        this.reporterName = report.getReporter().getNickName();
        this.reportedUserId = report.getReportedUser().getId();
        this.reportedUserName = report.getReportedUser().getNickName();
        this.reportedPartyId = report.getReportedParty().getId();
        this.reportedPartyName = report.getReportedParty().getName();
        this.reportedChatId = report.getReportedChat().getId();
        this.reportedChatContent = report.getReportedChat().getContent();
        this.content = report.getContent();
        this.reportType = report.getType();
        this.createdAt = report.getCreatedAt();
    }
}
