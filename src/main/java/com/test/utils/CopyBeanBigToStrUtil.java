/**
 *
 */
package com.test.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 通过映射，把model中的BigDecimal的属性值copy到model2的String类型的同名属性中
 *
 * @author lijiannan
 *
 */
public class CopyBeanBigToStrUtil {

    public static void copyBigDecimalToString(Object model, Object model2) throws Exception {
        // 获取实体类的所有属性，返回Field数组
        Field[] field = model.getClass().getDeclaredFields();
        // 获取属性的名字
        for (int i = 0; i < field.length; i++) {
            // 获取属性的名字
            String name = field[i].getName();
            // 获取属性类型
            String type = field[i].getGenericType().toString();

            // 关键。。。可访问私有变量
            field[i].setAccessible(true);

            // 将属性的首字母大写
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());

			/*if (type.equals("class java.lang.String")) {
				// 如果type是类类型，则前面包含"class "，后面跟类名
				Method m = model.getClass().getMethod("get" + name);
				// 调用getter方法获取属性值
				String value = (String) m.invoke(model);
				if (value != null) {

					System.out.println("attribute value:" + value);
				}

			}*/

            //如果model属性是BigDecimal类型进行对model2的同名String类型属性赋值
            if (type.equals("class java.math.BigDecimal")) {
                // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = model.getClass().getMethod("get" + name);
                // 调用getter方法获取属性值
                BigDecimal value = (BigDecimal) m.invoke(model);
                if (value != null) {
                    Method m2 = model2.getClass().getMethod("set" + name, String.class);
                    m2.invoke(model2, value.toString());
                }
            }

        }
    }

    public static void main(String[] args) {

    }
}
