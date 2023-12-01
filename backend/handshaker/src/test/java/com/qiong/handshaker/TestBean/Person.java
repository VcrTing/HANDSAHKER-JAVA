package com.qiong.handshaker.TestBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Long id;
    private String name;
    private Boolean is18;
    private Integer status;
    private String hobby;
    private Hobby hobbyBean;
}
