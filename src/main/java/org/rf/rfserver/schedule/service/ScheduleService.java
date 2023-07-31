package org.rf.rfserver.schedule.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Schedule;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.schedule.dto.PostScheduleReq;
import org.rf.rfserver.schedule.dto.PostScheduleRes;
import org.rf.rfserver.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;


import static org.rf.rfserver.config.BaseResponseStatus.DATABASE_ERROR;
import static org.rf.rfserver.config.BaseResponseStatus.REQUEST_ERROR;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PartyRepository partyRepository;

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



}
