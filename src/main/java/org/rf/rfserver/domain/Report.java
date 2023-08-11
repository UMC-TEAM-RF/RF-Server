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
    private User actor;

    @ManyToOne(fetch = LAZY)
    private Party actorParty;

    public Report(User reporter, User actor, String content, ReportType type) {
        this.reporter = reporter;
        this.actor = actor;
        this.actorParty = null;
        this.content = content;
        this.type = type;
    }

    public Report(User reporter, Party actorParty, String content, ReportType type) {
        this.reporter = reporter;
        this.actor = null;
        this.actorParty = actorParty;
        this.content = content;
        this.type = type;
    }
}
