package org.rf.rfserver.university.repository;

import org.rf.rfserver.domain.University;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository {
    Optional<University> findByName(String name);

    Boolean existsByName(String name);
}
