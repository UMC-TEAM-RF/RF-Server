package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupInterest {
    @Id @GeneratedValue
    @Column(name = "group_interest_id")
    private Long id;
    private String interestName;
}
