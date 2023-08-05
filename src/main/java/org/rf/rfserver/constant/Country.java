package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Country {
    UNITED_STATES(1L,"united states", "미국")
    ,JAPAN(81L, "japan", "일본")
    ,KOREA(82L,"korea","한국")
    ,CHINA(86L, "china", "중국")
    ;
    private final Long code;
    private final String englishname;
    private final String koreanname;
    private static final Map<Long, Country> BY_CODE = Stream.of(values()).collect(Collectors.toMap(Country::getCode, e->e));
    public static Country getCountryByCode(Long code) {
        return BY_CODE.get(code);
    }
}
