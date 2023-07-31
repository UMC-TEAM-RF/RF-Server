package org.rf.rfserver.schedule.repository;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByParty(Party party);
}
