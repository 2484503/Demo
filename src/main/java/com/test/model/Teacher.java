package com.test.model;

import com.test.service.VersionInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/5/30 15:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements VersionInterface {

    private int num;

    private String name;

    private List<Person> personList;

    private Person person;

    private Double grade;

    private Double weight;

    private int version;

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Teacher setVersion(int version) {
        this.version = version;
        return this;
    }
}
