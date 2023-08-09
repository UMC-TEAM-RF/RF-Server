package org.rf.rfserver.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatchScheduleReq {
    private Long ownerId;
    private String scheduleName = null;
    private String location = null;
    private LocalDateTime localDateTime = null;
    private Long alert = null;
}
