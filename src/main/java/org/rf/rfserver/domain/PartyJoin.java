package org.rf.rfserver.domain;

import jakarta.persistence.*;

@Entity
public class PartyJoin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
    @OneToOne
    private Party party;
}
