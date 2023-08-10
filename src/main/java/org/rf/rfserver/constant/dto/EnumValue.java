package org.rf.rfserver.constant.dto;

import lombok.Getter;
import org.rf.rfserver.constant.EnumModel;

@Getter
public class EnumValue {
    private String key;
    private String value;
    public EnumValue(EnumModel enumModel) {
        key = enumModel.getKey();
        value = enumModel.getValue();
    }
}
