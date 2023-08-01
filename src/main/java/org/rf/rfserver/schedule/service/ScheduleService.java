package org.rf.rfserver.schedule.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Schedule;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.schedule.dto.*;
import org.rf.rfserver.schedule.repository.ScheduleRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static org.rf.rfserver.config.BaseResponseStatus.DATABASE_ERROR;
import static org.rf.rfserver.config.BaseResponseStatus.REQUEST_ERROR;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PartyRepository partyRepository;

    private final UserRepository userRepository;

    //일정 생성
    public PostScheduleRes createSchedule(PostScheduleReq postScheduleReq) throws BaseException {
        try {
            //모임이 존재하지 않으면 일정 생성 불가
            Party party = partyRepository.findById(postScheduleReq.getPartyId())
                  .orElseThrow(() -> new BaseException(REQUEST_ERROR));

            //Schedule 객체 생성
            Schedule schedule = scheduleRepository.save(Schedule.builder()
                    .scheduleName(postScheduleReq.getScheduleName())
                    .datetime(postScheduleReq.getLocalDateTime())
                    .location(postScheduleReq.getLocation())
                    .participantCount(party.getMemberCount())
                    .alert(postScheduleReq.getAlert())
                    .party(party)
                    .build());

            return new PostScheduleRes(schedule.getId());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetScheduleRes> getScheduleByParty(Long partyId) throws BaseException{

        //해당 모임이 존재하는지 확인
        Party party = partyRepository.findById(partyId)
                .orElseThrow(()-> new BaseException(REQUEST_ERROR));

        //해당 모임의 일정을 찾아옴
        List<Schedule> schedules = scheduleRepository.findByParty(party);

        return schedules.stream()
                .map(GetScheduleRes::new)
                .collect(Collectors.toList());
    }

//    public List<GetScheduleRes> getScheduleByUser(Long userId) throws BaseException{
//        //해당 유저가 존재하는지 확인
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
//
//
//    }
    //일정 수정
    @Transactional
    public PatchScheduleRes updateSchedule(Long scheduleId, PatchScheduleReq patchScheduleReq) throws BaseException{
        try{
            Schedule schedule = scheduleRepository.getReferenceById(scheduleId);
            schedule.updateSchedule(patchScheduleReq.getScheduleName(), patchScheduleReq.getLocalDateTime(), patchScheduleReq.getLocation(),
                    patchScheduleReq.getAlert());
            return new PatchScheduleRes(true);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //일정 삭제
    public DeleteScheduleRes deleteSchedule(Long scheduleId) throws BaseException{
        try {
            scheduleRepository.deleteById(scheduleId);
            return new DeleteScheduleRes(true);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
