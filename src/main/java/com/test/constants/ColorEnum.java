package com.test.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/8/12 17:41
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ColorEnum {

    RED(1, "红色"),

    GREED(2, "绿色"),

    BLUE(3, "蓝色"),
    ;

    private int code;

    private String description;

}
