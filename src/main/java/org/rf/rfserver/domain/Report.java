package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = LAZY)
    private User reporter;

    @ManyToOne(fetch = LAZY)
    private User reportedUser;

    @ManyToOne(fetch = LAZY)
    private Party reportedParty;

    public Report(User reporter, User reportedUser, String content) {
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.content = content;
    }

    public Report(User reporter, Party reportedParty, String content) {
        this.reporter = reporter;
        this.reportedParty = reportedParty;
        this.content = content;
    }
}
