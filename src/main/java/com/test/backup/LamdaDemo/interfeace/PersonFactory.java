package com.test.backup.LamdaDemo.interfeace;

import com.test.model.Person;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/9/26 15:05
 */
public interface PersonFactory<p extends Person> {

    p create(int age, String name, String address, int sex, BigDecimal weight, List<String> list, LocalDateTime birthday);

}
