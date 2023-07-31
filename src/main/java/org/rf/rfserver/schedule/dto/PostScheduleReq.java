package org.rf.rfserver.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostScheduleReq {
    private Long id;
    private Long partyId;
    private String scheduleName;
    private String location;
    private LocalDateTime localDateTime;
    private Integer alert;
    private Integer memberCount;
}
