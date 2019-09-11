package com.test.main;

import com.test.model.Person;
import com.test.model.Student;
import com.test.utils.CommonUtils;

import java.math.BigDecimal;

public class Demo {
    public static void main(String[] args) throws IllegalAccessException {

        Person person = new Person();
        person.setName("123");
        person.setWeight(BigDecimal.ONE);

        Student student = new Student();

        CommonUtils.copyValueToStr(person, student, BigDecimal.class);

        System.out.println(student);

    }
}
