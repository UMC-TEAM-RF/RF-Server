package org.rf.rfserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ChattingRoom {
    @Id @GeneratedValue
    @Column(name = "catting_room_id")
    private Long id;
    private String name;
    private LocalDateTime createdDate;
}
