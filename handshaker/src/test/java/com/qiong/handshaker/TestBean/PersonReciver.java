package com.qiong.handshaker.TestBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonReciver {
    private Long id;
    private String name;
    private Boolean is18;
    private Integer status;
    private Hobby hobby;
    private Hobby hobbyBean;
}
