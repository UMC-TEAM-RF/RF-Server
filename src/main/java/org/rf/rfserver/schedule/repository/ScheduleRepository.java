package org.rf.rfserver.schedule.repository;

import org.rf.rfserver.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
