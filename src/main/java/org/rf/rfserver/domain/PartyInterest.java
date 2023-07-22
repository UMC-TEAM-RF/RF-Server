package org.rf.rfserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.Constant.Interest;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyInterest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public PartyInterest(Party party, Interest partyInterest) {
        this.party = party;
        this.partyInterest = partyInterest;
    }

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    private Party party;
    private Interest partyInterest;

    @Override
    public String toString() {
        return "PartyPartyInterest{" +
                "partyInterest=" + partyInterest +
                '}';
    }
}
