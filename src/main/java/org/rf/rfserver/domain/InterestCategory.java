package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestCategory {
    @Id @GeneratedValue
    @Column(name = "interest_category_id")
    private Long id;
    private String categoryName;
}
