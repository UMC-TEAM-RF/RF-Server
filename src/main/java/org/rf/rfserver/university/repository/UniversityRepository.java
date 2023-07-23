package org.rf.rfserver.university.repository;

import org.rf.rfserver.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findByName(String name);

    Boolean existsByName(String name);
}
