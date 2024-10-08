package org.manhdev.testcrudspringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "ngoại lệ chưa được phân loại", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(409, "username đã tồn tại", HttpStatus.CONFLICT),
    USER_ID_EXISTED(404, "user không tồn tại", HttpStatus.BAD_REQUEST),
    USER_ID_EMPTY(400, "chua truyen id", HttpStatus.BAD_REQUEST),
    INVALID_KEY(400, "key không hợp lệ", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_ALLOWED(405, "Không được phép truy cập", HttpStatus.NOT_FOUND),
    ENDPOINT_NOT_FOUND(404, "endpoint không hợp lệ", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(400, "username phai du {min} ky tu tro len", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(400, "password phai du {min} ky tu tro len", HttpStatus.BAD_REQUEST),
    PASSWORD_NULL_OR_EMPTY(400, "chưa nhập id", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(401, "token không hợp lệ", HttpStatus.UNAUTHORIZED),
    INVALID_DOB(400, "Tuổi của bạn phải ít nhất là {min}", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1008, "user không có quyền truy cập", HttpStatus.FORBIDDEN),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
