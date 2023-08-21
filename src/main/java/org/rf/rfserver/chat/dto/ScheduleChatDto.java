package org.rf.rfserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.domain.Schedule;

import java.time.format.DateTimeFormatter;

@Setter
@Getter
@AllArgsConstructor
public class ScheduleChatDto {
    private Long scheduleId = null;
    private String scheduleName;
    private String dateTime;
    private String location;
    private Long participantCount;
    private Long alert;
    public ScheduleChatDto(Schedule schedule) {
        DateTimeFormatter scheduleFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.scheduleId = schedule.getId();
        this.scheduleName = schedule.getScheduleName();
        this.dateTime = schedule.getDatetime().format(scheduleFormatter);
        this.location = schedule.getLocation();
        this.participantCount = schedule.getParticipantCount();
        this.alert = schedule.getAlert();
    }
}
