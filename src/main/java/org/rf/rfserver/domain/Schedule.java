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
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String scheduleName;
    private LocalDateTime datetime;
    private String location;
    private int participantCount;
    private int alert;

    @ManyToOne(fetch = LAZY)
    private Party party;

    @Builder
    public Schedule(Long id, String scheduleName, LocalDateTime datetime, String location, int participantCount, int alert, Party party){
        this.id = id;
        this.scheduleName = scheduleName;
        this.datetime = datetime;
        this.location = location;
        this.participantCount = participantCount;
        this.alert = alert;
        this.party = party;
    }
}
