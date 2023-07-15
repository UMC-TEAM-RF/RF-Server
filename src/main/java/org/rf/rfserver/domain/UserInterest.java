package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterest {
    @Id @GeneratedValue
    @Column(name = "user_interest_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;
    @ManyToOne(fetch = LAZY)
    private Interest interest;
}
