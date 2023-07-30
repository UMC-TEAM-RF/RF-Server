package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteChat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = LAZY)
    private User user;
}
