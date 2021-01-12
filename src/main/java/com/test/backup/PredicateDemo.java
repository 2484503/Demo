package com.test.backup;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/9/27 09:55
 */
public class PredicateDemo {

    public static void main(String[] args) {
        Collection<String> c = new HashSet<String>();
        c.add("!");
        c.add("java");
        c.add("hao");
        c.add("ni");
        c.add("zhe");
        System.out.println(findSet(c, obj -> System.out.println(obj), obj -> ((String) obj).length() < 3));

        //使用Lambda表达式（目标类型是Predicate）过滤集合
        c.removeIf(ele -> ((String) ele).length() < 10);
        System.out.println(c);
    }

    public static int findSet(Collection collection, Consumer consumer,
                              Predicate predicate) {
        int n = 0;
        for (Object obj : collection) {
            if (predicate.test(obj)) {
                n++;
                consumer.accept(obj);
            }
        }
        return n;
    }
}
