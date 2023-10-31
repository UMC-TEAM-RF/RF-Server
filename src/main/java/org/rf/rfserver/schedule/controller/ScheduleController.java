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

    /**
     * 일정 생성
     * @param postScheduleReq
     * @return PostScheduleRes
     */
    @PostMapping
    public BaseResponse<PostScheduleRes> createSchedule(@RequestBody PostScheduleReq postScheduleReq){
        try{
            return new BaseResponse<>(scheduleService.createSchedule(postScheduleReq));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    //해당 모임의 일정 조회
    @GetMapping("/party/{partyId}")
    public BaseResponse<List<GetScheduleRes>> getScheduleByParty(@PathVariable ("partyId") Long partyId){
        try{
            return new BaseResponse<>(scheduleService.getScheduleByParty(partyId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    */

    /**
     * 유저 일정 조회
     * @param userId
     * @param year, month
     * @return List[GetScheduleRes]
     **/
    @GetMapping("/user/{userId}")
    public BaseResponse<List<GetScheduleRes>> getScheduleByUser(@PathVariable ("userId") Long userId, @RequestParam ("year") int year, @RequestParam ("month") int month){
        try{
            return new BaseResponse<>(scheduleService.getScheduleByUser(userId, year, month));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 일정 수정
     * @param scheduleId
     * @param patchScheduleReq
     * @return PatchScheduleRes
     */
    @PatchMapping("/{scheduleId}")
    public BaseResponse<PatchScheduleRes> updateSchedule(@PathVariable ("scheduleId") Long scheduleId, @RequestBody PatchScheduleReq patchScheduleReq){
        try{
            return new BaseResponse<>(scheduleService.updateSchedule(scheduleId,patchScheduleReq));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 일정 삭제
     * @param scheduleId
     * @return DeleteScheduleRes
     */
    @DeleteMapping("/{scheduleId}")
    public BaseResponse<DeleteScheduleRes> deleteSchedule(@PathVariable ("scheduleId") Long scheduleId, @RequestBody DeleteScheduleReq deleteScheduleReq){
        try{
            return new BaseResponse<>(scheduleService.deleteSchedule(scheduleId,deleteScheduleReq));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
