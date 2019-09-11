package com.test.backup.ObserverDemo;

import java.util.Observable;

public class Teacher extends Observable {

    public void setHomeWork(String work) {

        setChanged();

        notifyObservers(work);

        System.out.println("进入了sethomework");
    }

}
