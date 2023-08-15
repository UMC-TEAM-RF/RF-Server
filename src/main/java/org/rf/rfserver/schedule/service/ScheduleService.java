package org.rf.rfserver.schedule.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Schedule;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.party.UserPartyRepository;
import org.rf.rfserver.schedule.dto.*;
import org.rf.rfserver.schedule.repository.ScheduleRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PartyRepository partyRepository;

    private final UserRepository userRepository;
    private final UserPartyRepository userPartyRepository;

    /**
     * 일정 생성
     * @param postScheduleReq
     * @return PostScheduleRes
     * @throws BaseException
     */
    public PostScheduleRes createSchedule(PostScheduleReq postScheduleReq) throws BaseException {
            //모임이 존재하지 않으면 일정 생성 불가
            Party party = partyRepository.findById(postScheduleReq.getPartyId())
                  .orElseThrow(() -> new BaseException(PARTY_NOT_FOUND));
        try{
            //Schedule 객체 생성
            Schedule schedule = scheduleRepository.save(Schedule.builder()
                    .scheduleName(postScheduleReq.getScheduleName())
                    .datetime(postScheduleReq.getLocalDateTime())
                    .location(postScheduleReq.getLocation())
                    .participantCount(Long.valueOf(party.getMemberCount()))
                    .alert(postScheduleReq.getAlert())
                    .party(party)
                    .build());

            return new PostScheduleRes(schedule.getId());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**

    //모임별 일정 조회
    public List<GetScheduleRes> getScheduleByParty(Long partyId) throws BaseException{

        //해당 모임이 존재하는지 확인
        Party party = partyRepository.findById(partyId)
                .orElseThrow(()-> new BaseException(PARTY_NOT_FOUND));

        //해당 모임의 일정을 찾아옴
        List<Schedule> schedules = scheduleRepository.findByParty(party);

        return schedules.stream()
                .map(GetScheduleRes::new)
                .collect(Collectors.toList());
    }
    **/

    /**
     * 해당 유저의 일정 조회(월별 조회)
     * @param userId
     * @param getScheduleReq
     * @return List[GetScheduleRes]
     * @throws BaseException
     */
    public List<GetScheduleRes> getScheduleByUser(Long userId, GetScheduleReq getScheduleReq) throws BaseException{
        //해당 유저가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        //사용자가 조회하고자 하는 연도와 달의 정보를 가져옴
        int year = getScheduleReq.getYear();
        int month = getScheduleReq.getMonth();

        //월별 조회를 위한 LocalDateTime 변수 생성
        LocalDateTime startDate = LocalDateTime.of(year, month, 1,0,0,0);
        LocalDateTime endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        //유저가 가입한 모임 목록을 가져옴
        List<UserParty> userParties = userPartyRepository.findByUser(user);

        //userParties에서 모임 정보만 리스트로 가져옴
        List<Party> parties = userParties.stream()
                .map(UserParty::getParty)
                .collect(Collectors.toList());

        //모임별 스케줄을 불러옴
        List<Schedule> schedules = scheduleRepository.findByParties(parties, startDate, endDate);

        return schedules.stream()
                .map(GetScheduleRes::new)
                .collect(Collectors.toList());
    }

    /**
     * 일정 수정
     * @param scheduleId
     * @param patchScheduleReq
     * @return PatchScheduleRes
     * @throws BaseException
     */
    @Transactional
    public PatchScheduleRes updateSchedule(Long scheduleId, PatchScheduleReq patchScheduleReq) throws BaseException{
        //해당 일정이 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new BaseException(SCHEDULE_NOT_FOUND));
        //해당 모임의 모임장이 맞는지 확인
        if(patchScheduleReq.getOwnerId() != schedule.getParty().getOwnerId())
            throw new BaseException(NOT_PARTY_OWNER);
        try {
            schedule.updateSchedule(patchScheduleReq.getScheduleName(), patchScheduleReq.getLocalDateTime(), patchScheduleReq.getLocation(),
                    patchScheduleReq.getAlert());
            return new PatchScheduleRes(true);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     *
     * @param scheduleId
     * @param deleteScheduleReq
     * @return DeleteScheduleRes
     * @throws BaseException
     */
    @Transactional
    public DeleteScheduleRes deleteSchedule(Long scheduleId, DeleteScheduleReq deleteScheduleReq) throws BaseException{
        //일정이 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new BaseException(SCHEDULE_NOT_FOUND));
        //모임장이 맞는지 확인
        if(deleteScheduleReq.getOwnerId() != schedule.getParty().getOwnerId())
            throw new BaseException(NOT_PARTY_OWNER);
        try {
            scheduleRepository.deleteById(scheduleId);
            return new DeleteScheduleRes(true);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
