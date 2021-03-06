package com.test.model;

import com.test.service.VersionInterface;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/5/30 15:53
 */
@Data
@Builder
public class Student implements VersionInterface {
    private int id;

    private int num;

    private String name;

    private List<Person> personList;

    private String weight;

    private int version;

    private LocalDateTime date;

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Student setVersion(int version) {
        this.version = version;
        return this;
    }
}
