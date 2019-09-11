package com.test.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/8/2 10:52
 */
public class CommonUtils {

    /**
     * 将data Double类型的字段值设置默认值
     *
     * @param data
     */
    public static <T> void setFiledDefault(T data) {
        Class clazz = data.getClass();
        Field[] fieldArray = clazz.getDeclaredFields();

        Arrays.stream(fieldArray)
                .filter(field -> field.getType() == Double.class)
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        if (field.get(data) == null) {
                            field.set(data, 0.0);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 0x代表是16进制，C0表示16进制的数值
     * <p>
     * 0xC0对应的是11000000，0x80对应的是10000000
     * <p>
     * 关于编码的资料参考https://www.cnblogs.com/lslk89/p/6898526.html，在此感谢郭海东同志
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
