package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String scheduleName;
    private LocalDateTime datetime;
    private String location;
    private Long participantCount;
    private Long alert;

    @ManyToOne(fetch = LAZY)
    private Party party;

    @Builder
    public Schedule(String scheduleName, LocalDateTime datetime, String location, Long participantCount, Long alert, Party party) {
        this.scheduleName = scheduleName;
        this.datetime = datetime;
        this.location = location;
        this.participantCount = participantCount;
        this.alert = alert;
        this.party = party;
    }
}
