package org.rf.rfserver.constant.Controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.constant.dto.EnumValue;
import org.rf.rfserver.constant.service.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enums")
public class ApiController {
    private final ApiService apiService;
    @GetMapping()
    public Map<String, List<EnumValue>> getEnum() {
        return apiService.getEnumList();
    }
}
