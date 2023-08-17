package org.rf.rfserver.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"currentPage", "totalPage", "content"})
public class PageDto<T> {
    private final int currentPage;
    private final int totalPage;
    private final T content;
}
