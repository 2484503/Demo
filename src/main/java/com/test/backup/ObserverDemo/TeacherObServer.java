package com.test.backup.ObserverDemo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Observable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherObServer extends Observable {

    public void setHomeWork(String work) {

        setChanged();

        notifyObservers(work);

        System.out.println("进入了sethomework");
    }

}
