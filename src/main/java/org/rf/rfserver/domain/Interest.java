package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id @GeneratedValue
    @Column(name = "interest_id")
    private Long id;
    private String interestName;

    @ManyToOne(fetch = LAZY)
    private InterestCategory interestCategory;
}
