package org.rf.rfserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.Interest;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyInterest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Interest partyInterest;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    private Party party;

    public PartyInterest(Interest partyInterest, Party party) {
        this.partyInterest = partyInterest;
        this.party = party;
    }
}
