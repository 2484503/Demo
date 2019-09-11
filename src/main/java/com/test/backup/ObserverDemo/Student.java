package com.test.backup.ObserverDemo;

import java.util.Observable;
import java.util.Observer;

public class Student implements Observer {

    private Observable ob;

    public Student(Observable ob) {
        this.ob = ob;
        ob.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.println("同学得到了 = = = = =" + arg);

    }

}
