package org.rf.rfserver.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.rf.rfserver.config.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;
    private final Object data;

    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
        this.data = null;
    }

    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
        this.data = null;
    }

    public static BaseResponse of(BaseResponseStatus successCode, Object data) {
        return new BaseResponse(successCode, data);
    }

    public static BaseResponse of(BaseResponseStatus successCode) {
        return new BaseResponse(successCode, "");
    }

    public BaseResponse(BaseResponseStatus successCode, Object data) {
        this.isSuccess = true;
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
        this.data = data;
    }

}
