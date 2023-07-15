package org.rf.rfserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;
    private String content;
}
