package org.rf.rfserver.report.dto;

import lombok.Getter;
import org.rf.rfserver.constant.ReportType;
import org.rf.rfserver.domain.Report;

import static org.rf.rfserver.constant.ReportType.*;

@Getter
public class PostReportRes {
    private Long reportId;
    private String reportedUserName;
    private String reportedPartyName;
    private String reportedChatContent;
    private String content;
    private ReportType reportType;
    public PostReportRes(Report report) {
        if(report.getType() == USER) userPostReportResConstructor(report);
        if(report.getType() == PARTY) partyPostReportResConstructor(report);
        if(report.getType() == CHAT) chatPostReportResConstructor(report);
    }

    private void userPostReportResConstructor(Report report) {
        this.reportId = report.getId();
        this.reportedUserName = report.getReportedUser().getNickName();
        this.content = report.getContent();
        this.reportType = report.getType();
    }

    private void partyPostReportResConstructor(Report report) {
        this.reportId = report.getId();
        this.reportedPartyName = report.getReportedParty().getName();
        this.content = report.getContent();
        this.reportType = report.getType();
    }
    private void chatPostReportResConstructor(Report report) {
        this.reportId = report.getId();
        this.reportedUserName = report.getReportedUser().getNickName();
        this.reportedPartyName = report.getReportedParty().getName();
        this.reportedChatContent = report.getContent();
        this.content = report.getContent();
        this.reportType = report.getType();
    }
}
