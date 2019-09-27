package com.test.backup.LamdaDemo;

import com.google.common.collect.Lists;
import com.test.backup.LamdaDemo.interfeace.PersonFactory;
import com.test.model.Person;
import com.test.model.Student;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/9/23 09:08
 */
public class Demo {
    public static void main(String[] args) {
        StringToIntegerTest();
    }

    /**
     * 其中replaceAll中的function返回的必须是List中的对象类型的
     */
    private static void listReplaceAllTest() {
        List<Student> students = Lists.newArrayList(Student.builder().id(1).name("zhangsan").build());
        students.replaceAll(student -> student.setVersion(12));
        System.out.println(students);
    }

    /**
     * 这个没想到什么场景下会使用这个，感觉不如直接写直观
     */
    private static void StringToIntegerTest() {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);
    }

    private static void PersonFactoryTest() {
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create(1, "Peter", "Parker", 12, BigDecimal.ONE, null, null);
    }
}
