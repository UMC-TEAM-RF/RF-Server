package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;
    private LocalDateTime date;
    private String location;
    private int participantCount;
    private String scheduleName;

    @ManyToOne(fetch = LAZY)
    private Group group;
}
