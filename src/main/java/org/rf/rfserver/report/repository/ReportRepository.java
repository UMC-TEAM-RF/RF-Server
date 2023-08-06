package org.rf.rfserver.report.repository;

import org.rf.rfserver.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
