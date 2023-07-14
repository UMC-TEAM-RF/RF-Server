package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMembers {
    @Id @GeneratedValue
    Long id;

    @ManyToOne(fetch = LAZY)
    private Group group;
    @ManyToOne(fetch = LAZY)
    private User user;
}
