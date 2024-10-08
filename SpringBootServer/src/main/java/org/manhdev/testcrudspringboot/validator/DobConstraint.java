package org.manhdev.testcrudspringboot.validator;

// định nghĩa một annotation tùy chỉnh để kiểm tra tính hợp lệ của một trường
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Target (FIELD) để annotation tùy chỉnh này chỉ có thể dùng trên các biến hoặc thuộc tính của đối tượng.
@Target({FIELD})

// thời gian tồn tại của annotation. annotation này sẽ tồn tại trong thời gian chạy của chương trình.
@Retention(RUNTIME)

// Liên kết annotation này với một class xác thực, Class này chứa logic xác thực cụ thể cho trường ngày sinh.
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {

    //    thông báo lỗi mặc định khi trường không hợp lệ.
    String message() default "Invalid date of birth";

    //    Thuộc tính này yêu cầu người dùng cung cấp một giá trị số tối thiểu
    int min();

    //    cho phép nhóm các ràng buộc xác thực
    Class<?>[] groups() default {};

    //    cung cấp thông tin bổ sung về lỗi xác thực
    Class<? extends Payload>[] payload() default {};
}
