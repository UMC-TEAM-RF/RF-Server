package org.rf.rfserver.domain.university.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.domain.University;
import org.rf.rfserver.domain.university.repository.UniversityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    public University findUniversityByName(String name) {
        return universityRepository.findByName(name).orElseThrow(RuntimeException::new);
    }
}
