package org.rf.rfserver.mail.exception;

import org.rf.rfserver.config.BaseResponseStatus;

public class InvalidMailException extends RuntimeException {
    private final BaseResponseStatus status;

    public InvalidMailException(BaseResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    public BaseResponseStatus getStatus() {
        return status;
    }
}
