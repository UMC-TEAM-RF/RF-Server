package org.rf.rfserver.schedule.repository;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    //파티 목록에서 일정을 조회하는 쿼리
    @Query("SELECT s FROM Schedule s WHERE s.party IN(:parties) AND s.datetime >(:startDate) AND s.datetime<(:endDate)")
    List<Schedule> findByParties(@Param("parties") List<Party> parties, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
