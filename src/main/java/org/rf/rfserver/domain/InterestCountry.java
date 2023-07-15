package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestCountry {
    @Id @GeneratedValue
    @Column(name = "interest_country_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;
    @ManyToOne(fetch = LAZY)
    private Country interestingNation;
}
