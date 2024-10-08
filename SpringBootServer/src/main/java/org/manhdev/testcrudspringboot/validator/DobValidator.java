package org.manhdev.testcrudspringboot.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// class DobValidator để thực hiện validate cho annotation DobConstraint
// nó triển khai từ interface ConstraintValidator, nhận 2 tham số DobConstraint và LocalDate là kiểu dữ liệu muốn
// validate
public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {

    //    Biến min lưu trữ giá trị tuổi tối thiểu
    private int min;

    //    ghi đè isValid để xử l logic ngày sinh hợp lệ
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;

        //    tính khoảng cách thời gian (tính bằng năm) giữa ngày sinh (value) và ngày hiện tại (LocalDate.now()).
        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= min;
    }

    //    khi khởi tạo sẽ lấy được thông số của annotation đó
    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        //    gọi phương thức khởi tạo mặc định của lớp cha (ConstraintValidator),
        //    đảm bảo rằng bất kỳ logic khởi tạo nào từ lớp cơ sở cũng được thực thi.
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }
}
