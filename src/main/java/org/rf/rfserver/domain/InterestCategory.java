package org.rf.rfserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestCategory {
    @Id @GeneratedValue
    private Long id;
    private String categoryName;
    @OneToMany
    private List<Interest> interests;
}
