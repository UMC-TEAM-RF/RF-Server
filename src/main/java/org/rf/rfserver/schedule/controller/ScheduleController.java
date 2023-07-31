package org.rf.rfserver.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.schedule.dto.PostScheduleReq;
import org.rf.rfserver.schedule.dto.PostScheduleRes;
import org.rf.rfserver.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public BaseResponse<PostScheduleRes> createSchedule(@RequestBody PostScheduleReq postScheduleReq){
        try{
            return new BaseResponse<>(scheduleService.createSchedule(postScheduleReq));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
