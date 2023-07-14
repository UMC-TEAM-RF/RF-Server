package org.rf.rfserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String content;
    private String interest; // 객체
    private String tag; // 객체
    private String guideLink;
    private String location;
    private String language;
    private String filePath;
    private String preferAges;

    private LocalDateTime createdDate;

    private int memberCount;
    private int nativeNumber;
    private int ownerId;


    private Rule rule;

}
