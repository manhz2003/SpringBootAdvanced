package org.manhdev.testcrudspringboot.controller;

import java.text.ParseException;

import org.manhdev.testcrudspringboot.dto.request.*;
import org.manhdev.testcrudspringboot.dto.response.AuthenticationResponse;
import org.manhdev.testcrudspringboot.dto.response.IntrospectResponse;
import org.manhdev.testcrudspringboot.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    //    comment nào mà đặt spotless:off thì thư viện format code sẽ bỏ qua đoạn code đấy
    //    và không tự động format, spotless:off là phần bắt đầu spotless:on là phần kết thúc

    //    spotless:off
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }
    //    spotless:on

    // làm mới token
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    //    Đăng xuất giúp vô hiệu hóa token hoặc session đang hoạt động, ngăn chặn việc người khác lợi dụng
    //    thông tin đăng nhập cũ để truy cập trái phép vào hệ thống.
    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
