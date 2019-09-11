package com.test.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符处理实用工具类；
 */
public class StringUtil {

    // 16进制字符数组
    private static final char[] hex = "0123456789ABCDEF".toCharArray();

    /**
     * 路径分隔符，不做系统区分，统一为：/；
     */
    public static final String PathSeperator = "/";  //System.getProperty("path.separator");
    /**
     * 行分隔符，与系统相关；
     */
    public static final String LineSeperator = System.getProperty("line.separator");
    /**
     * @deprecated
     */
    public static final String Line_Seperator = System.getProperty("line.separator");
    /**
     * 文件默认字符集，与系统相关；
     */
    public static final String FileEncoding = System.getProperty("file.encoding");
    /**
     * @deprecated
     */
    public static final String File_Encoding = System.getProperty("file.encoding");
    /**
     * 日期字符串样式：yyyy-MM-dd
     */
    public static final String DateStyle = "yyyy-MM-dd";
    /**
     * 时间字符串样式：HH:mm:ss
     */
    public static final String TimeStyle = "HH:mm:ss";
    /**
     * 日期时间字符串样式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DateTimeStyle = "yyyy-MM-dd HH:mm:ss";

    /**
     * 禁止实例化；
     */
    private StringUtil() {
    }

    /**
     * 判断一个对象是否为 null；
     */
    public static final boolean isNull(Object o) {
        return (o == null);
    }

    /**
     * 检测某个字符变量是否为空；<br>
     * 为空的情况，包括：null，空串或只包含可以被 trim() 的字符；
     */
    public static final boolean isEmpty(String value) {
        if (value == null || value.trim().length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个字符数组是否为空(null 或 length==0)；
     */
    public static final boolean isEmpty(String[] array) {
        if (array == null || array.length == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个整型数组是否为空(null 或 length==0)；
     */
    public static final boolean isEmpty(boolean[] array) {
        if (array == null || array.length == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个字节数组是否为空(null 或 length==0)；
     */
    public static final boolean isEmpty(byte[] array) {
        if (array == null || array.length == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个整型数组是否为空(null 或 length==0)；
     */
    public static final boolean isEmpty(int[] array) {
        if (array == null || array.length == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个 StringBuffer 对象是否为空(null 或 length==0)；
     */
    public static final boolean isEmpty(StringBuffer sb) {
        if (sb == null || sb.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个 StringBuilder 对象是否为空(null 或 length==0)；
     */
    public static final boolean isEmpty(StringBuilder sb) {
        if (sb == null || sb.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个List 接口对象是否为空(null 或 size==0)；
     */
    public static final boolean isEmpty(List list) {
        if (list == null || list.size() == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个Set 接口对象是否为空(null 或 size==0)；
     */
    public static final boolean isEmpty(Set set) {
        if (set == null || set.size() == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个Map 接口对象是否为空(null 或 size==0)；
     */
    public static final boolean isEmpty(Map map) {
        if (map == null || map.size() == 0)
            return true;
        else
            return false;
    }

    /**
     * 判断一个对象是否为空(null)；
     */
    public static final boolean isEmpty(Object o) {
        return (o == null);
    }

    /**
     * 判断一个对象数组是否为空(null 或 length==0)；
     */
    public static final boolean isEmpty(Object[] array) {
        if (array == null || array.length == 0)
            return true;
        else
            return false;
    }

    /**
     * 检测一个字符变量的值是否为一个整型数据，暂不限制范围；
     */
    public static final boolean isInt(String value) {
        return (value != null) && value.matches("[-+]?\\d{1,10}");
    }

    /**
     * 检测一个字符变量的值是否为一个整型数据串；
     */
    public static final boolean isNumber(String value) {
        return (value != null) && value.matches("[-+]?\\d+");
    }

    public static final boolean isDecimal(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    /**
     * 检测字符串是否为有效数值。
     *
     * @param s
     * @param includeZero，如为真，表示零是合法的；否则非法
     * @return
     */
    public static final boolean isPositiveDecimal(String s, boolean includeZero) {
        try {
            double v = Double.parseDouble(s);
            if (includeZero) {
                return v >= 0;
            }
            return v > 0;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
    }

    /**
     * 检测字符串是否为合法的数值
     *
     * @param s
     * @param integerLength，整数部分最长长度
     * @param fractionLength，小数部分最长长度
     * @return
     */
    public static final boolean isValidDecimal(String s, int integerLength, int fractionLength) {
        if (isEmpty(s) || integerLength < 1 || fractionLength < 1) {
            return false;
        }
        Pattern p = Pattern.compile("^[-+]?\\d{1," + integerLength + "}(\\.\\d{1," + fractionLength + "})?$");
        return p.matcher(s).find();
    }

    /**
     * 检测字符串是否为合法的数值，不含负数
     *
     * @param s
     * @param integerLength，整数部分最长长度
     * @param fractionLength，小数部分最长长度
     * @return
     */
    public static final boolean isValidPositiveDecimal(String s, int integerLength, int fractionLength) {
        if (isEmpty(s) || integerLength < 1 || fractionLength < 1) {
            return false;
        }
        Pattern p = Pattern.compile("^\\d{1," + integerLength + "}(\\.\\d{1," + fractionLength + "})?$");
        return p.matcher(s).find();
    }

    /**
     * 获取指定字符串的大小，不做 trim() 处理；
     */
    public static final int size(String text) {
        if (text == null)
            return 0;
        else
            return text.length();
    }

    /**
     * 获取指定 String 类型数组的大小；
     */
    public static final int size(String[] array) {
        if (isEmpty(array))
            return 0;
        else
            return array.length;
    }

    /**
     * 获取指定 boolean 类型数组的大小；
     */
    public static final int size(boolean[] array) {
        if (isEmpty(array))
            return 0;
        else
            return array.length;
    }

    /**
     * 获取指定 byte 整型数组的大小；
     */
    public static final int size(byte[] array) {
        if (isEmpty(array))
            return 0;
        else
            return array.length;
    }

    /**
     * 获取指定 int 整型数组的大小；
     */
    public static final int size(int[] array) {
        if (isEmpty(array))
            return 0;
        else
            return array.length;
    }

    /**
     * 获取指定 Date 类型数组的大小；
     */
    public static final int size(Date[] array) {
        if (isEmpty(array))
            return 0;
        else
            return array.length;
    }

    /**
     * 获取指定 List 集合的大小；
     */
    public static final int size(List list) {
        if (isEmpty(list))
            return 0;
        else
            return list.size();
    }

    /**
     * 获取指定 Set 集合的大小；
     */
    public static final int size(Set set) {
        if (isEmpty(set))
            return 0;
        else
            return set.size();
    }

    /**
     * 获取指定 Map 集合的大小；
     */
    public static final int size(Map map) {
        if (isEmpty(map))
            return 0;
        else
            return map.size();
    }

    /**
     * 获取指定对象数组的大小；
     */
    public static final int size(Object[] array) {
        if (isEmpty(array))
            return 0;
        else
            return array.length;
    }

    /**
     * 检测两个字符串是否相同，大小写敏感，去除边界空格；
     */
    public static final boolean isSame(String value1, String value2) {
        return isSame(value1, value2, false);
    }

    /**
     * 检测两个字符串是否相同，大小写敏感，去除边界空格；
     *
     * @see {@link StringUtil#isSame(String, String, boolean)};
     */
    public static final boolean isSameIgnoreCase(String value1, String value2) {
        return isSame(value1, value2, true);
    }

    /**
     * 检测两个字符串是否相同，去除边界空格，并指定是否大小写敏感；
     */
    public static final boolean isSame(String value1, String value2, boolean ignoreCase) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return true;
        } else if (!isEmpty(value1) && !isEmpty(value2)) {
            if (ignoreCase) {
                return (value1.trim()).equalsIgnoreCase(value2.trim());
            } else {
                return (value1.trim().equals(value2.trim()));
            }
        } else {
            return false;
        }
    }

    /**
     * 判断指定字符串中是否包含指定的字符串，大小写敏感；
     */
    public static final boolean isContain(String content, String value) {
        return isContain(content, value, true);
    }

    /**
     * 判断指定字符串中是否包含指定的字符串，大小写不敏感；
     */
    public static final boolean isContain(String content, String value, boolean ignoreCase) {
        if (isEmpty(content) || isEmpty(value))
            return false;

        if (ignoreCase) {
            return (content.toLowerCase().indexOf(value.toLowerCase()) != -1);
        } else {
            return (content.indexOf(value) != -1);
        }
    }

    /**
     * 判断指定字符数组中是否包含指定的字符串，区分大小写；
     */
    public static final boolean isContain(String[] aryContent, String value) {
        return isContain(aryContent, value, false);
    }

    /**
     * 判断指定字符数组中是否包含指定的字符串，并指定是否区分大小写；
     */
    public static final boolean isContain(String[] aryContent, String value, boolean ignoreCase) {
        int size = size(aryContent);
        if (size == 0 || isEmpty(value))
            return false;

        for (int i = 0; i < size; i++) {
            if (isContain(aryContent[i], value, ignoreCase))
                return true;
        }

        return false;
    }

    /**
     * 判断指定字符串中是否包含指定的字符数组中的某一项；
     */
    public static final boolean isContain(String content, String[] aryValue, boolean ignoreCase) {
        int size = StringUtil.size(aryValue);
        if (isEmpty(content) || size == 0)
            return false;

        for (int i = 0; i < size; i++) {
            if (isContain(content, aryValue[i], ignoreCase)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断指定字符数组中是否包含指定的字符串；
     */
    public static final boolean isContain(int[] array, int value) {
        if (isEmpty(array))
            return false;

        int size = size(array);
        for (int i = 0; i < size; i++) {
            if (array[i] == value)
                return true;
        }

        return false;
    }

    /**
     * 判断指定 List 集合中是否包含指定的对象；
     */
    public static final boolean isContain(List list, Object object) {
        if (list == null)
            return false;

        return list.contains(object);
    }

    /**
     * 判断指定 Set 结合中是否包含指定的对象；
     */
    public static final boolean isContain(Set set, Object object) {
        if (set == null)
            return false;

        return set.contains(object);
    }

    /**
     * 字符串截取，不包含起始处字符；<br>
     * begin: 范围：0 ~ 字符串的长度；
     * length: 如果截取长度超过字符串的长度则返回整个串；
     */
    public static final String middle(String text, int begin, int length) {
        if (isEmpty(text) || begin < 0 || length < 0)
            return "";

        int end = Math.min(begin + length, text.length());
        return text.substring(begin, end);
    }

    public static final String middle(String text, int begin) {
        if (isEmpty(text) || begin < 0)
            return "";

        return text.substring(begin);
    }

    /**
     * 从左边起截取指定长度的字符串；
     */
    public static final String left(String text, int length) {
        if (isEmpty(text) || length < 1)
            return "";

        if (length > text.length())
            return text;

        return text.substring(0, length);
    }

    /**
     * 从右边起截取指定长度的字符串；
     */
    public static final String right(String text, int length) {
        if (isEmpty(text) || length < 1)
            return "";

        if (length > text.length())
            return text;

        return text.substring(text.length() - length);
    }

    /**
     * 格式化字符串显示，默认为空字符串；
     */
    public static final String format(String value) {
        return format(value, "");
    }

    /**
     * 格式化字符串显示，并指定默认值；
     */
    public static final String format(String value, String defaultValue) {
        if (isEmpty(value))
            return defaultValue;
        else
            return value.trim();
    }

    /**
     * 格式化 Boolean 类型显示，默认为空字符串；
     */
    public static final String format(Boolean value) {
        return format(value, "");
    }

    /**
     * 格式化 Boolean 类型显示，并指定默认值
     */
    public static final String format(Boolean value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value.booleanValue() ? "是" : "否";
    }

    /**
     * 格式化 Integer 类型显示，默认为空字符串；
     */
    public static final String format(Integer value) {
        return format(value, "");
    }

    /**
     * 格式化 Integer 类型显示，并指定默认值
     */
    public static final String format(Integer value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value.toString();
    }

    /**
     * @param
     * @author sunyf
     * @description: ${判断对象是否为空并去前后空格，为空返回""}
     * @created 2017/6/9 9:44
     **/
    public static final String alterNull(Object str) {
        return (str == null || str.toString().length() == 0) ? "" : str.toString().trim();
    }

    /**
     * @param
     * @author sunyf
     * @description: ${判断字符串是否为空并去前后空格，为空返回""}
     * @created 2017/6/9 9:44
     **/
    public static final String alterNullStr(String str) {
        return (str == null || str.length() == 0) ? "" : str.trim();
    }

    /**
     * 按照指定长度格式化整型变量；<br>
     * 例如：按照 4 位样式：0000，格式化数据：12 --> 0012<br>
     * 样式："###,###"，格式化数据：1200 ---> 1,200
     *
     * @param style 参见 java.text.DecimalFormat 的说明；
     */
    public static final String format(String style, int value) {
        return new DecimalFormat(style).format(value);
    }

    /**
     * 按照指定长度格式化整型变量；
     */
    public static final String format(String style, long value) {
        return new DecimalFormat(style).format(value);
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd HH:mm:ss，默认为空字符串；
     */
    public static final String format(Date date) {
        return formatDateTimeByStyle("yyyy-MM-dd HH:mm:ss", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd HH:mm:ss，并指定默认值；
     */
    public static final String format(Date date, Date defaultValue) {
        if (date == null)
            date = defaultValue;
        return formatDateTimeByStyle("yyyy-MM-dd HH:mm:ss", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd HH:mm:ss，并指定默认值；
     */
    public static final String format(Date date, String defaultValue) {
        return formatDateTimeByStyle("yyyy-MM-dd HH:mm:ss", date, defaultValue);
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd；
     */
    public static final String formatDate(Date date) {
        return formatDateTimeByStyle("yyyy-MM-dd", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd，并指定默认值；
     */
    public static final String formatDate(Date date, Date defaultValue) {
        if (date == null) date = defaultValue;
        return formatDateTimeByStyle("yyyy-MM-dd", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd，并指定默认值；
     */
    public static final String formatDate(Date date, String defaultValue) {
        return formatDateTimeByStyle("yyyy-MM-dd", date, defaultValue);
    }

    /**
     * 格式化日期时间字符串显示，格式：HH:mm:ss；
     */
    public static final String formatTime(Date date) {
        return formatDateTimeByStyle("HH:mm:ss", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：HH:mm:ss，并指定默认值；
     */
    public static final String formatTime(Date date, Date defaultValue) {
        if (date == null) date = defaultValue;
        return formatDateTimeByStyle("HH:mm:ss", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：HH:mm:ss，并指定默认值；
     */
    public static final String formatTime(Date date, String defaultValue) {
        return formatDateTimeByStyle("HH:mm:ss", date, defaultValue);
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd HH:mm:ss；
     */
    public static final String formatDateTime(Date date) {
        return formatDateTimeByStyle("yyyy-MM-dd HH:mm:ss", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd HH:mm:ss，并指定默认值；
     */
    public static final String formatDateTime(Date date, Date defaultValue) {
        if (date == null) date = defaultValue;
        return formatDateTimeByStyle("yyyy-MM-dd HH:mm:ss", date, "");
    }

    /**
     * 格式化日期时间字符串显示，格式：yyyy-MM-dd HH:mm:ss，并指定默认值；
     */
    public static final String formatDateTime(Date date, String defaultValue) {
        return formatDateTimeByStyle("yyyy-MM-dd HH:mm:ss", date, defaultValue);
    }

    /**
     * 按照指定样式格式化日期时间字符串显示；
     */
    public static final String formatDateTime(String style, Date date) {
        return formatDateTimeByStyle(style, date, "");
    }

    /**
     * 按照指定样式格式化日期时间字符串显示，并指定默认值；
     */
    public static final String formatDateTime(String style, Date date, Date defaultValue) {
        if (date == null) date = defaultValue;
        return formatDateTimeByStyle(style, date, "");
    }

    /**
     * 按照指定样式格式化日期时间字符串显示，并指定默认值；
     */
    public static final String formatDateTime(String style, Date date, String defaultValue) {
        return formatDateTimeByStyle(style, date, defaultValue);
    }

    /**
     * unix时间戳转换为指定样式的日期字符串格式
     */
    public static final String TimeStamp2Date(String timestampString, String formats) {
        if (StringUtil.isEmpty(timestampString)) {
            return null;
        }
        Long timestamp = Long.parseLong(timestampString);
        String date = DateFormatUtils.format(new Date(timestamp), formats);
        return date;
    }

    /**
     * unix时间戳转换为指定样式的日期字符串格式
     */
    public static final String TimeStamp2Date(Long timestamp, String formats) {
        if (StringUtil.isEmpty(timestamp)) {
            return null;
        }
        String date = DateFormatUtils.format(new Date(timestamp), formats);
        return date;
    }

    /**
     * 内部调用方法，格式化日期时间字符串显示，指定格式；
     */
    private static final String formatDateTimeByStyle(String style, Date date, String defaultValue) {
        if (isEmpty(style) || isEmpty(date))
            return defaultValue;

        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return StringUtil.format(sdf.format(date), defaultValue);
    }

    /**
     * 格式化一个通用对象；
     */
    public static final Object format(Object object, Object defaultValue) {
        if (object == null)
            object = defaultValue;
        return object;
    }

    /**
     * 将一个字符数组中每一项的值用指定的符号进行连接，默认不过滤空值；
     */
    public static final String join(String[] array, String joiner) {
        return join(array, joiner, false);
    }

    /**
     * 将一个字符数组中每一项的值用指定的符号进行连接，并指定是否过滤空值；
     */
    public static final String join(String[] array, String joiner, boolean isFilterEmpty) {
        if (StringUtil.isEmpty(array))
            return "";

        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (isFilterEmpty) {
            while (sb.length() == 0) {
                if (!isEmpty(array[i])) {
                    sb.append(array[i]);
                }
                i++;
                if (i >= array.length)
                    break;
            }
        } else {
            sb.append(array[i]);
            i++;
        }
        for (; i < array.length; i++) {
            if (isFilterEmpty) {
                if (!isEmpty(array[i])) {
                    sb.append(joiner);
                    sb.append(array[i]);
                }
            } else {
                sb.append(joiner);
                sb.append(array[i]);
            }
        }

        return sb.toString();
    }

    /**
     * 将一个整型数组中每一项的值用指定的符号进行连接；
     */
    public static final String join(int[] array, String joiner) {
        if (StringUtil.isEmpty(array))
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(joiner);
            sb.append(array[i]);
        }

        return sb.toString();
    }

    /**
     * 将一个 List 类型中每一项的值用指定的符号进行连接，默认不过滤空值；
     */
    public static final String join(List<String> list, String joiner) {
        return StringUtil.join(list, joiner, false);
    }

    /**
     * 将一个 List 类型中每一项的值用指定的符号进行连接，并指定是否过滤空值；
     */
    public static final String join(List<String> list, String joiner, boolean isFilterEmpty) {
        String[] array = StringUtil.ListToArray(list);
        return StringUtil.join(array, joiner, isFilterEmpty);
    }

    /**
     * 用指定的分隔符，分隔指定内容，如果分割失败(输入值为 null)则返回一个长度为 0 的字符数组；<br/>
     * 语言默认的 String.split(String regex) 函数是以正则表达式为输入，并且过滤了一些特殊字符，
     * 例如：回车，逗号等，语义上已经出现偏差放弃使用；
     */
    public static final String[] split(String content, String splitor) {
        return split(content, splitor, false);
    }

    /**
     * 用指定的分隔符，分隔指定内容，并指定是否过滤空值；
     */
    public static final String[] split(String content, String splitor, boolean isFilteEmpty) {
        if (content == null || content.length() == 0) {
            return new String[0];
        }

        if (splitor == null || splitor.length() == 0 || content.indexOf(splitor) == -1) {
            String[] ary = {content};
            return ary;
        }

        if (StringUtil.isSame(content, splitor)) {
            String[] ary = {"", ""};
            return ary;
        }

        int count = 0;
        int length = splitor.length();
        int beginIndex = 0;
        int closeIndex = content.indexOf(splitor);

        while (closeIndex > -1) {
            count++;
            beginIndex = closeIndex + length;
            closeIndex = content.indexOf(splitor, beginIndex);
        }

        String[] ary = new String[count + 1];
        beginIndex = 0;
        closeIndex = content.indexOf(splitor);
        int i = 0;
        for (; i < count; i++) {
            ary[i] = content.substring(beginIndex, closeIndex);

            beginIndex = closeIndex + length;
            closeIndex = content.indexOf(splitor, beginIndex);
        }
        ary[i] = content.substring(beginIndex);

        if (isFilteEmpty) {
            return StringUtil.ListToArray(StringUtil.ArrayToList(ary, true));
        } else {
            return ary;
        }
    }

    /**
     * 用指定的长度，分隔指定内容，如果分割失败则返回一个长度为 0 的字符数组；
     */
    public static final String[] split(String content, int length) {
        if (isEmpty(content) || length < 1)
            return new String[0];

        int max = content.length();
        int size = max / length;
        if (size * length < max) size++;
        String[] ary = new String[size];
        for (int i = 0; i < size; i++) {
            int end = (i + 1) * length;
            if (end > max) end = max;
            ary[i] = content.substring(i * length, end);
        }

        return ary;
    }

    /**
     * 用指定的长度，分隔指定数组为二维数组，如果分割失败则返回一个二维的长度为 0 的字符数组；
     */
    public static final String[][] split(String[] arySource, int length) {
        if (isEmpty(arySource) || length < 1)
            return new String[0][0];

        int size = arySource.length;
        int count = (int) size / length;
        if (size - count * length > 0) {
            count++;
        }
        String[][] ary = new String[count][length];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < length; j++) {
                int index = i * length + j;
                if (index < size) {
                    ary[i][j] = arySource[index];
                }
            }
        }
        return ary;
    }

    /**
     * 将 List 中的值倒入到一个字符数组中，默认不过滤其中的空值;
     */
    public static final String[] ListToArray(List<String> list) {
        return ListToArray(list, false);
    }

    /**
     * 将 List 中的值倒入到一个字符数组中，并指定是否过滤其中的空值;
     */
    public static final String[] ListToArray(List<String> list, boolean isFilteEmpty) {
        if (StringUtil.isEmpty(list))
            return null;

        String[] array = new String[size(list)];
        for (int i = 0; i < array.length; i++) {
            if (isFilteEmpty) {
                if (!isEmpty(list.get(i))) ;
                array[i] = list.get(i);
            } else {
                array[i] = list.get(i);
            }
        }
        return array;
    }

    /**
     * 将字符数组中的值倒入到一个 List 中，默认不过滤其中的空值;
     */
    public static final List<String> ArrayToList(String[] array) {
        return ArrayToList(array, false);
    }

    /**
     * 将字符数组中的值倒入到一个 List 中，并指定是否过滤其中的空值;
     */
    public static final List<String> ArrayToList(String[] array, boolean filterEmpty) {
        if (StringUtil.isEmpty(array))
            return null;

        List<String> list = new ArrayList<String>(array.length);

        for (int i = 0; i < size(array); i++) {
            if (filterEmpty) {
                if (!isEmpty(array[i]))
                    list.add(array[i]);
            } else {
                list.add(array[i]);
            }
        }

        return list;
    }

    /**
     * 以字符串形式返回当前的时间点，格式：yyyy-MM-dd HH-mm-ss；
     */
    public static final String getCurrentTime() {
        return StringUtil.format(new Date());
    }

    /**
     * 编码字符串中 HTML 字符：&,空格,<,>,\,单引号,<br>：
     * 转义字符对应如下
     * <ul>
     * <li>& => &amp;amp;
     * <li>  => &amp;nbsp;
     * <li>< => &amp;lt;
     * <li>> => &amp;gt;
     * <li>' => &amp;apos;
     * <li>" => &amp;quot;
     * <li>\r\n => <br>;
     * </ul>
     */
    public static final String encodeHtml(String text) {
        if (isEmpty(text))
            return "";

        text = text.replaceAll("&", "&amp;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("\"", "&quot;"); // 双引号；
        text = text.replaceAll("'", "&apos;"); // 单引号；
        text = text.replaceAll(StringUtil.LineSeperator, "<br></br>"); // 回车；

        return text;
    }

    /**
     * 解码字符串中 HTML 字符：&,空格,<,>,\,单引号,<br>：
     */
    public static final String decodeHtml(String html) {
        if (isEmpty(html))
            return "";

        html = html.replaceAll("&amp;", "&");
        html = html.replaceAll("&nbsp;", " ");
        html = html.replaceAll("&lt;", "<");
        html = html.replaceAll("&gt;", ">");
        html = html.replaceAll("&quot;", "\""); // 双引号；
        html = html.replaceAll("&apos;", "'");  // 单引号；
        html = html.replaceAll("<br>", StringUtil.LineSeperator); // 回车；

        return html;
    }

    /**
     * 编码 URL 字符串，采用平台默认的字符集；
     */
    public static final String encodeUrl(String url) {
        return encodeUrl(url, StringUtil.FileEncoding);
    }

    /**
     * 编码 URL 字符串，采用指定的字符集；
     */
    public static final String encodeUrl(String url, String charset) {
        if (isEmpty(url))
            return "";

        try {
            // 因为 URLEncoder 只是将空格替换为加号，这可能会导致一些问题故处理下；
            url = URLEncoder.encode(url, charset).replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
        }

        return url;
    }

    /**
     * 解码 URL 字符串，采用平台默认的字符集；
     */
    public static final String decodeUrl(String url) {
        return decodeUrl(url, StringUtil.FileEncoding);
    }

    /**
     * 解码 URL 字符串，采用指定的字符集；
     */
    public static final String decodeUrl(String url, String charset) {
        if (isEmpty(url))
            return "";

        try {
            url = URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException e) {
        }

        return url;
    }

    /**
     * 编码输入字符串为 Unicode 数字序列，输出格式：\\uXXXX；
     */
    public static final String encodeUnicode(String input) {
        if (isEmpty(input))
            return "";

        char[] aryChar = input.toCharArray();
        StringBuilder sb = new StringBuilder(aryChar.length * 6);
        for (int i = 0; i < aryChar.length; i++) {
            String hex = Integer.toHexString(aryChar[i]);
            if (hex.length() <= 2) {
                hex = "00" + hex;
            }
            sb.append("\\u" + hex);
        }
        return sb.toString();
    }

    /**
     * 解码 Unicode 数字序列为字符串，输入格式：\\uXXXX；
     */
    public static final String decodeUnicode(String input) {
        if (isEmpty(input))
            return "";

        input = input.toLowerCase();
        int beginIndex = input.indexOf("\\u");
        int closeIndex = 0;
        StringBuilder sb = new StringBuilder(input.length());

        while (beginIndex > -1) {
            closeIndex = input.indexOf("\\u", beginIndex + 2);
            String s = "";
            if (closeIndex == -1) {
                s = input.substring(beginIndex + 2, input.length());
                beginIndex = -1;
            } else {
                s = input.substring(beginIndex + 2, closeIndex);
                beginIndex = closeIndex;
            }
            char c = (char) Integer.parseInt(s, 16);
            sb.append(new Character(c).toString());
        }

        return sb.toString();
    }

    /**
     * 生成一个 option 标签；
     */
    public static final String generateOption(String value, String label) {
        return generateOption(value, label, false);
    }

    public static final String generateOption(String value, String label, boolean isSelected) {
        StringBuilder sb = new StringBuilder();

        sb.append("<option value=\"" + value + "\"");
        if (isSelected)
            sb.append(" selected");
        sb.append(">");
        sb.append(label);
        sb.append("</option>");

        return sb.toString();
    }

    /**
     * 检测指定域名是否为另一域名的子域名，例如：www.domain.com,domain.com；
     */
    public static final boolean isSubDomain(String subDomain, String domain) {
        if (StringUtil.isEmpty(subDomain) || StringUtil.isEmpty(domain))
            return false;

        return subDomain.endsWith(domain);
    }

    /**
     * 检测指定字符串是否包含 Sql 关键字；<br/>
     * 过滤的关键字包括：select，insert，update，delete，char, chr，script，iframe，onerror，onload，onmouse；
     * 程序账户只有：reader，writer 权限，所以无需检测：create，drop，grant，exec；
     */
    public static final boolean isSqlValid(String text) {
        if (StringUtil.isEmpty(text)) {
            return true;
        }

        String temp = text.toLowerCase();
        boolean isExist = temp.indexOf("select ") > -1 || temp.indexOf("insert ") > -1 || temp.indexOf("update ") > -1 || temp.indexOf("delete ") > -1;
        if (!isExist) {
            isExist = temp.indexOf("char") > -1 || temp.indexOf(" or ") > -1 || temp.indexOf("'or ") > -1 || temp.indexOf(" or'") > -1;
            if (!isExist) {
                isExist = temp.indexOf("script") > -1 || temp.indexOf("iframe") > -1 || temp.indexOf("><a") > -1;
            }
            if (!isExist) {
                isExist = temp.indexOf("onload") > -1 || temp.indexOf("onerror") > -1 || temp.indexOf("onmouse") > -1;
            }
        }
        return !isExist;
    }

    /**
     * 从一个 URL 中提取出站点域名，无端口号，例如：www.domain.com；
     *
     * @param url 一个合法的 URL 地址；
     */
    public static final String abstractSiteHost(String url) {
        if (StringUtil.isEmpty(url))
            return null;

        url = abstractSiteURL(url).replace("http://", "").replace("https://", "");
        int index = url.indexOf(":");
        return (index > -1) ? url.substring(0, index) : url;
    }

    /**
     * 从一个 URL 中提取出站点路径，无 / 结尾，包含端口号，例如：http://www.domain.com；
     *
     * @param url 一个合法的 URL 地址；
     */
    public static final String abstractSiteURL(String url) {
        if (StringUtil.isEmpty(url))
            return null;

        url = url.replace("http://", "");
        int index = url.indexOf("/");
        if (index == -1) {
            return "http://" + url;
        } else {
            return "http://" + url.substring(0, index);
        }
    }

    /**
     * 从一个 URL 中提取出站点目录，以 / 结尾，例如：http://www.domain.com/dir/；
     *
     * @param url 一个合法的 URL 地址；
     */
    public static final String abstractSiteDirectory(String url) {
        if (StringUtil.isEmpty(url))
            return null;

        int index = url.lastIndexOf("/");
        if (index > -1) {
            return url.substring(0, url.lastIndexOf("/") + 1);
        } else {
            return url;
        }
    }

    /**
     * 使用指定的前缀和后缀包装指定数据，如果数据为空则不输出任何内容；<br/>
     * 前后缀支持占位符：{data}
     */
    public static final String wrap(String data, String prefix, String suffix) {
        return wrap(data, prefix, suffix, true);
    }

    /**
     * 使用指定的前缀和后缀包装指定数据，并指定是否对数据进行空过滤；<br/>
     * 前后缀支持占位符：{data}
     */
    public static final String wrap(String data, String prefix, String suffix, boolean isFilterEmpty) {
        if (isFilterEmpty && StringUtil.isEmpty(data)) {
            return "";
        } else {
            if (prefix != null) {
                prefix = prefix.replace("{data}", data);
            }
            if (suffix != null) {
                suffix = suffix.replace("{data}", data);
            }
            return StringUtil.format(prefix) + StringUtil.format(data) + StringUtil.format(suffix);
        }
    }

    /**
     * 使用指定的前缀和后缀包装一组指定的数据，并指定是否对数据进行空过滤，以及分组内容的分隔符；<br/>
     * 前后缀支持占位符：{data}
     */
    public static final String[] wrap(String[] aryData, String prefix, String suffix, boolean isFilterEmpty) {
        if (StringUtil.isEmpty(aryData))
            return new String[]{};

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < aryData.length; i++) {
            String data = wrap(aryData[i], prefix, suffix, isFilterEmpty);
            if (isFilterEmpty) {
                if (!StringUtil.isEmpty(data)) {
                    list.add(data);
                }
            } else {
                list.add(data);
            }
        }
        return StringUtil.ListToArray(list);
    }

    /**
     * 使用指定的前缀和后缀包装一组指定的数据，并指定是否对数据进行空过滤，以及分组内容的分隔符；<br/>
     * 前后缀支持占位符：{data}
     */
    public static final String wrap(String[] aryData, String prefix, String suffix, boolean isFilterEmpty, String splitor) {
        if (StringUtil.isEmpty(aryData))
            return "";

        splitor = StringUtil.format(splitor);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < StringUtil.size(aryData); i++) {
            String data = StringUtil.wrap(aryData[i], prefix, suffix, isFilterEmpty);
            if (isFilterEmpty) {
                if (!StringUtil.isEmpty(data)) {
                    if (i != 0) {
                        sb.append(splitor);
                    }
                    sb.append(data);
                }
            } else {
                if (i != 0) {
                    sb.append(splitor);
                }
                sb.append(data);
            }
        }
        return sb.toString();
    }

    /**
     * 基于数组参数的文本替换，源数组参数充当占位符；
     */
    public static final String replace(String template, String[] src, String[] aim) {
        if (StringUtil.isEmpty(template) || StringUtil.isEmpty(src) || StringUtil.isEmpty(aim))
            return template;

        int size = src.length;
        for (int i = 0; i < size; i++) {
            template = template.replace(src[i], aim[i]);
        }
        return template;
    }

    /**
     * 基于数组参数的多组文本替换，源数组参数充当占位符；
     */
    public static final String replace(String template, String[] src, List<String[]> aim) {
        if (StringUtil.isEmpty(template) || StringUtil.isEmpty(src) || StringUtil.isEmpty(aim))
            return template;

        int size = StringUtil.size(aim);
        StringBuilder sb = new StringBuilder(template.length() * size);
        for (int i = 0; i < size; i++) {
            String[] ary = aim.get(i);
            sb.append(StringUtil.replace(template, src, ary));
        }
        return sb.toString();
    }

    /**
     * 在一个字符串中，对指定的字符串，替换指定的次数；
     *
     * @param content      被替换的字符串；
     * @param src          要替换的字符串；
     * @param aim          替换成的字符；
     * @param replaceCount 替换次数，-1 表示进行全部替换；
     */
    public static final String replace(String content, String src, String aim, int replaceCount) {
        if (StringUtil.isEmpty(content) || StringUtil.isEmpty(src) || StringUtil.isNull(aim) || replaceCount == 0)
            return content;

        if (replaceCount == -1)
            return content.replace(src, aim);

        int beginIndex = 0;
        int closeIndex = 0;
        int counter = 0;
        int srcLength = src.length();
        StringBuilder sb = new StringBuilder(content.length());
        while (counter < replaceCount && (closeIndex = content.indexOf(src, beginIndex)) > -1) {
            counter++;
            sb.append(content.substring(beginIndex, closeIndex));
            sb.append(aim);

            beginIndex = closeIndex + srcLength;
            closeIndex = content.indexOf(src, beginIndex);
        }
        sb.append(content.substring(beginIndex));
        return sb.toString();
    }

    /**
     * 获得一个字符串中包含另一个字符的数量；
     *
     * @param theString 被查找的字符串；
     * @param subString 要查找的字符串；
     */
    public static final int getSubStringCount(String theString, String subString) {
        if (StringUtil.isNull(theString) || StringUtil.isNull(subString) || theString.length() < subString.length())
            return 0;

        int index = theString.indexOf(subString);
        int len = subString.length();
        int count = 0;
        while (index > -1) {
            count++;
            index = theString.indexOf(subString, index + len);
        }
        return count;
    }

    /**
     * 去除 HTML 内容中指定的标签，但保留标签内容；
     */
    public static final String stripHtmlTag(String html, String tag) {
        if (StringUtil.isEmpty(html) || StringUtil.isEmpty(tag))
            return "";

        String regex = "</?" + tag + "[^>]*>";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        html = matcher.replaceAll("");

        regex = "<" + tag + "[^>]*/?>";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(html);
        html = matcher.replaceAll("");

        return html;
    }

    /**
     * 去除内容中的html标签；
     */
    public static final String stripHtmlTag(String tag) {
        if (StringUtil.isEmpty(tag)) {
            return "";
        }
        return tag.replaceAll("<[^<]*>", "").replaceAll("\\s*|\t|\r|\n", "").replaceAll("&nbsp;", "");
    }

    /**
     * 把字节数组转换成16进制字符串；
     */
    public static String byteToHex(byte[] array) {
        return byteToHex(array, null);
    }

    /**
     * 把字节数组转换成16进制字符串，并指定每个字节之间的分隔符；
     */
    public static String byteToHex(byte[] array, String splitor) {
        if (StringUtil.isEmpty(array))
            return null;

        // 检查索引范围
        int end = array.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < end; i++) {
            sb.append(hex[(array[i] & 0xF0) >>> 4]).append(hex[array[i] & 0xF]);
            if (splitor != null) {
                sb.append(splitor);
            }
        }
        if (sb.length() > 0 && splitor != null)
            sb.deleteCharAt(sb.length() - splitor.length());
        return sb.toString();
    }

    /**
     * 把一个16进制字符串转换为字节数组，字符串没有空格，所以每两个字符一个字节；
     */
    public static byte[] hexToByte(String input) {
        if (StringUtil.isEmpty(input))
            return null;

        int len = input.length();
        byte[] output = new byte[len >>> 1];
        for (int i = 0; i <= (len - 2); i += 2) {
            output[i >>> 1] = (byte) (Integer.parseInt(input.substring(i, i + 2).trim(), 16) & 0xFF);
        }
        return output;
    }

    /**
     * 转换按照指定字符分隔的 16 进制字符串，如果转换失败则返回 null ；
     */
    public static byte[] hexToByte(String input, String splitor) {
        if (StringUtil.isEmpty(input))
            return null;

        if (StringUtil.isEmpty(splitor))
            return hexToByte(input);

        byte[] output = null;
        input = input.trim();
        StringTokenizer st = new StringTokenizer(input, splitor);
        output = new byte[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            String byteString = st.nextToken();

            // 一个字节是2个16进制数，如果不对，则返回
            if (byteString.length() != 2)
                throw new IllegalArgumentException("输入数据格式错误！");

            output[i] = (byte) (Integer.parseInt(byteString, 16) & 0xFF);
        }

        return output;
    }

    /**
     * 保留2位小数
     *
     * @param decimal
     * @return
     */
    public static String round2(BigDecimal decimal) {

        decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        String OutString = decimal.toString();
        return OutString;
    }

    /**
     * 正则验证
     *
     * @param str
     * @param regex
     * @return
     */
    public static boolean doesMatche(String str, String regex) {
        if (StringUtil.isEmpty(str) || StringUtil.isEmpty(regex))
            return false;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * str1和str2中的所有进行.equals，存在一个相等返回true
     */
    public static boolean equalsCompareToArray(String str1, Object... str2) {
        if (StringUtil.isEmpty(str1)) {
            return false;
        }
        Object[] objArray = str2;
        int n = objArray.length;
        int i = 0;
        while (n-- != 0) {
            if (str1.equals(objArray[i])) {
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * 获得某字符串（matString）中某个字符（string）第n次出现的位置,不存在这个字符串和字符串中包含的字符不够n的时候返回-1
     */
    public static int getCharacterPosition(String string, String matString, int n) {

        // string这里是获取"/"符号的位置
        // matString 在该字符串中寻找
        Matcher slashMatcher = Pattern.compile(string).matcher(matString);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            // 当"/"符号第几次次出现的位置
            if (mIdx == n) {
                break;
            }
        }
        if (mIdx == 0 || n > mIdx) {
            return -1;
        }
        return slashMatcher.start();
    }
}


