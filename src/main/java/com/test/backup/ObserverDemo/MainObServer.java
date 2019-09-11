package com.test.backup.ObserverDemo;

/**
 * 观察者模式
 */
public class MainObServer {
    public static void main(String[] args) {
        TeacherObServer t = new TeacherObServer();

        StudentObServer s1 = new StudentObServer(t);

        t.setHomeWork("作业1");
    }
}
