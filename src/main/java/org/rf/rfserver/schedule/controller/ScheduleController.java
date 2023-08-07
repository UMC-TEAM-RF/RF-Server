package org.rf.rfserver.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.schedule.dto.*;
import org.rf.rfserver.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //해당 모임의 일정 조회
    @GetMapping("/party/{partyId}")
    public BaseResponse<List<GetScheduleRes>> getScheduleByParty(@PathVariable ("partyId") Long partyId){
        try{
            return new BaseResponse<>(scheduleService.getScheduleByParty(partyId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //유저 일정 조회
    @GetMapping("/user/{userId}")
    public BaseResponse<List<GetScheduleRes>> getScheduleByUser(@PathVariable ("userId") Long userId){
        try{
            return new BaseResponse<>(scheduleService.getScheduleByUser(userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/{scheduleId}")
    public BaseResponse<PatchScheduleRes> updateSchedule(@PathVariable ("scheduleId") Long scheduleId, @RequestBody PatchScheduleReq patchScheduleReq){
        try{
            return new BaseResponse<>(scheduleService.updateSchedule(scheduleId,patchScheduleReq));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/{scheduleId}")
    public BaseResponse<DeleteScheduleRes> deleteSchedule(@PathVariable ("scheduleId") Long scheduleId){
        try{
            return new BaseResponse<>(scheduleService.deleteSchedule(scheduleId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
