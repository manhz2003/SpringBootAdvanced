package org.manhdev.testcrudspringboot.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
// bảng này dùng để lưu id và thời gian hết hạn của token, khi logout thì lưu
public class InvalidatedToken {
    @Id
    String id;

    //    thời gian hết hạn
    Date expiryTime;
}
