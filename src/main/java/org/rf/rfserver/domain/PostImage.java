package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {
    @Id @GeneratedValue
    @Column(name = "post_image_id")
    private Long id;
    private String imageFilePath;

}
