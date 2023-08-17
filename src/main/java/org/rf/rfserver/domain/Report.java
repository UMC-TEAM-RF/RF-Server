package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.ReportType;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Enumerated(EnumType.STRING)
    private ReportType type;

    @ManyToOne(fetch = LAZY)
    private User reporter;

    @ManyToOne(fetch = LAZY)
    private User reportedUser;

    @ManyToOne(fetch = LAZY)
    private Party reportedParty;

    @ManyToOne(fetch = LAZY)
    private Chat reportedChat;

    public Report(User reporter, User reportedUser, String content, ReportType type) {
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.reportedParty = null;
        this.content = content;
        this.type = type;
    }

    public Report(User reporter, Party reportedParty, String content, ReportType type) {
        this.reporter = reporter;
        this.reportedUser = null;
        this.reportedParty = reportedParty;
        this.content = content;
        this.type = type;
    }

    public Report(User reporter, User reportedUser, Party reportedParty, Chat reportedChat, String content, ReportType type) {
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.reportedParty = reportedParty;
        this.reportedChat = reportedChat;
        this.content = content;
        this.type = type;
    }
}
