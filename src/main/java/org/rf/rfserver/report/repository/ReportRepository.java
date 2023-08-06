package org.rf.rfserver.report.repository;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Report;
import org.rf.rfserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findReportsByReporter(User reporter);
    List<Report> findReportsByActor(User actor);
    List<Report> findReportsByActorParty(Party actorParty);
}
