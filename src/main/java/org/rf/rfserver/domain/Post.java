package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifyDate;
    private boolean open;

    @ManyToOne(fetch = LAZY)
    private User user;
    @OneToMany
    private List<PostImage> images;
}
