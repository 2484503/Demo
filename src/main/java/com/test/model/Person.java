package com.test.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/5/30 16:01
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Person implements Serializable {
    {
        System.out.println("person 构造代码块");
    }

    private Enum test;

    private Integer age;

    private String name;

    private String address;

    private Integer sex;

    private BigDecimal weight;

    private List<String> list;

    transient private LocalDateTime birthday;
}
