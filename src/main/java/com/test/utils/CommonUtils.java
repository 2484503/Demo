package com.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * 在此维护一些自己写的通用的工具类
 *
 * @author lijn
 * @version 1.0
 * @date 2019/8/2 10:52
 */
public class CommonUtils {

    /**
     * 给entity的clazz类型字段赋值默认值defaultValue
     *
     * @param entity       赋值的实体类
     * @param clazz        设置Class类型的字段默认值
     * @param defaultValue 默认值
     */
    public static <T> void setEntityFiledDefault(T entity, Class clazz, Object defaultValue) throws IllegalAccessException {
        if (entity == null) {
            return;
        }

        Class dataClazz = entity.getClass();
        Field[] fields = dataClazz.getDeclaredFields();
        int count = fields.length;

        for (int index = 0; index < count; index++) {
            Field field = fields[index];
            if (field.getType() != clazz) {
                continue;
            }
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            if (field.get(entity) == null) {
                field.set(entity, defaultValue);
            }
        }
    }

    /**
     * 将dataSource的clazz类型的属性值，copy到dataTarget的String同名字段中
     *
     * @param dataSource
     * @param dataTarget
     * @param clazz
     */
    public static <T> void copyValueToStr(T dataSource, T dataTarget, Class clazz) throws IllegalAccessException {
        Assert.notNull(dataSource, "dataSource must not be null");
        Assert.notNull(dataTarget, "dataTarget must not be null");

        Field[] fieldsSource = dataSource.getClass().getDeclaredFields();
        Field[] fieldsTarget = dataTarget.getClass().getDeclaredFields();

        int count = fieldsSource.length;

        for (int index = 0; index < count; index++) {
            Field fieldSource = fieldsSource[index];
            if (fieldSource.getType() != clazz) {
                continue;
            }
            int matchIndex = isContainFiled(fieldsTarget, fieldSource.getName());
            if (matchIndex < 0) {
                continue;
            }
            Field fieldTarget = fieldsTarget[matchIndex];
            if (!fieldTarget.isAccessible()) {
                fieldTarget.setAccessible(true);
            }
            if (!fieldSource.isAccessible()) {
                fieldSource.setAccessible(true);
            }
            Object object = fieldSource.get(dataSource);
            if (object == null) {
                continue;
            }
            fieldTarget.set(dataTarget, object.toString());
        }
    }

    /**
     * 根据属性名匹配字段，返回index
     */
    private static int isContainFiled(Field[] fields, String propertyName) {
        int count = fields.length;
        for (int index = 0; index < count; index++) {
            if (fields[index].getName().equals(propertyName)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 0x代表是16进制，C0表示16进制的数值
     * <p>
     * 0xC0对应的是11000000，0x80对应的是10000000
     * <p>
     * 关于编码的资料参考https://www.cnblogs.com/lslk89/p/6898526.html，在此感谢郭海东同志，致敬龙神
     * <p>
     * UTF-8 有以下编码规则：
     * 如果一个字节，最高位（第 8 位）为 0，表示这是一个 ASCII 字符（00 - 7F）。可见，所有 ASCII 编码已经是 UTF-8 了。
     * 如果一个字节，以 11 开头，连续的 1 的个数暗示这个字符的字节数，例如：110xxxxx 代表它是双字节 UTF-8 字符的首字节。
     * 如果一个字节，以 10 开始，表示它不是首字节，需要向前查找才能得到当前字符的首字节
     * <p>
     * 在while中，与操作，意在：如maxBytes位置，不是首字节，那么一直追溯到首字节，这样在new String的时候，就可以保证了不会截取半个汉字。
     * <p>
     * 根据字节截取字符串
     *
     * @param str      截取的字符串
     * @param maxBytes 截取的位数
     * @return 截取后的字符换
     */
    public static String truncateUTF8(String str, int maxBytes) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        byte[] bs = str.getBytes(StandardCharsets.UTF_8);

        if (bs.length < maxBytes) {
            return str;
        }
        int endIndex = maxBytes;
        while (endIndex >= 0 && (bs[endIndex] & 0xC0) == 0x80) {
            endIndex--;
        }
        return new String(bs, 0, endIndex, StandardCharsets.UTF_8);
    }
}
