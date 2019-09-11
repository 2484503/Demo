package com.test.backup.ObserverDemo;

/**
 * 观察者模式
 */
public class Demo {
    public static void main(String[] args) {
        Teacher t = new Teacher();

        Student s1 = new Student(t);

        t.setHomeWork("作业1");
    }
}
