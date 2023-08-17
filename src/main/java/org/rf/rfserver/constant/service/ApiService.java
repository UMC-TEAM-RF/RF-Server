package org.rf.rfserver.constant.service;

import org.rf.rfserver.constant.*;
import org.rf.rfserver.constant.dto.EnumValue;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiService {

    public Map<String, List<EnumValue>> getEnumList() {
        Map<String, List<EnumValue>> enumValues = new LinkedHashMap<>();
        enumValues.put("Country", toEnumValues(Country.class));
        enumValues.put("Interest", toEnumValues(Interest.class));
        enumValues.put("Language", toEnumValues(Language.class));
        enumValues.put("LifeStyle", toEnumValues(LifeStyle.class));
        enumValues.put("Mbti", toEnumValues(Mbti.class));
        enumValues.put("PreferAges", toEnumValues(PreferAges.class));
        enumValues.put("Rule", toEnumValues(Rule.class));
        enumValues.put("University", toEnumValues(University.class));
        enumValues.put("ReportType", toEnumValues(ReportType.class));
        enumValues.put("PushNotificationType", toEnumValues(PushNotificationType.class));
        return enumValues;
    }
    private List<EnumValue> toEnumValues(Class<? extends EnumModel> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
    }
}
