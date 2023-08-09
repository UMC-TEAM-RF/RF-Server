package org.rf.rfserver.schedule.dto;

import lombok.*;
import org.rf.rfserver.domain.Schedule;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetScheduleRes {
    private Long id;
    private Long partyId;
    private String partyName;
    private String scheduleName;
    private LocalDateTime localDateTime;
    private String location;
    private Long participantCount;

    public GetScheduleRes(Schedule schedule){
        this.id = schedule.getId();
        this.partyId = schedule.getParty().getId();
        this.partyName = schedule.getParty().getName();
        this.scheduleName = schedule.getScheduleName();
        this.localDateTime = schedule.getDatetime();
        this.location = schedule.getLocation();
        this.participantCount = schedule.getParticipantCount();
    }
}
