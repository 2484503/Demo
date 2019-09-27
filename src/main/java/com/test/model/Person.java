package com.test.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Person implements Serializable {

    private int age;

    private String name;

    private String address;

    private int sex;

    private BigDecimal weight;

    private List<String> list;

    transient private LocalDateTime birthday;
}
