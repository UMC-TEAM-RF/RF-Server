package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupRule {
    @Id @GeneratedValue
    @Column(name = "group_rule_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Group group;
    @ManyToOne(fetch = LAZY)
    private Rule rule;
}
