package org.rf.rfserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ChattingRoom {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private LocalDateTime createdDate;
}
