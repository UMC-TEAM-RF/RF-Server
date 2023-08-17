package org.rf.rfserver.report.repository;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Report;
import org.rf.rfserver.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findReportsByReporter(User reporter, Pageable pageable);
    Page<Report> findReportsByReportedUser(User reportedUser, Pageable pageable);
    Page<Report> findReportsByReportedParty(Party reportedParty, Pageable pageable);
    Page<Report> findAll(Pageable pageable);
}
