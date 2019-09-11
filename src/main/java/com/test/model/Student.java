package com.test.model;

import lombok.Data;

import java.util.List;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/5/30 15:53
 */
@Data
public class Student {
    private int id;

    private String name;

    private List<Person> personList;

}
