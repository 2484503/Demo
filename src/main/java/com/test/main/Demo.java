package com.test.main;

import com.test.model.Person;
import org.springframework.beans.BeanUtils;

public class Demo {
    public static void main(String[] args) {
        Person person = BeanUtils.instantiateClass(Person.class);
        person.setName("zhangsna");
        System.out.println(person);
    }
}
