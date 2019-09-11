package com.test.utils.serializer;

import com.fasterxml.jackson.databind.JavaType;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

/**
 * 反序列化工具，数据源不是String类型的用这个
 *
 * @author lijn
 * @version 1.0
 * @date 2019/8/21 10:16
 */
public class ConverterUtils {

    @SneakyThrows
    public static <T> T convert(Object object, Class<T> clazz) {
        return DeserializerUtils.deserialize(SerializerUtils.serialize(object), clazz);
    }

    @SneakyThrows
    public static <T> List<T> convertList(Object object, Class<T> clazz) {
        return DeserializerUtils.deserializeList(SerializerUtils.serialize(object), clazz);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> convertMap(Object object, Class<K> keyClass, Class<V> valueClazz) {
        return DeserializerUtils.deserializeMap(SerializerUtils.serialize(object), keyClass, valueClazz);
    }

    @SneakyThrows
    public static <R, T extends R, E> T convertMapGenericType(Object object, Class<R> clazz, Class<E> genericClass) {
        return DeserializerUtils.deserializeMapGenericType(SerializerUtils.serialize(object), clazz, genericClass);
    }

    @SneakyThrows
    public static <T> T convert(String src, Class<?> clazz, Class<?> parameterClasses) {
        JavaType javaType = JacksonObjectMapper.getInstance().getTypeFactory().constructParametricType(clazz, parameterClasses);
        return JacksonObjectMapper.getInstance().readValue(src, javaType);
    }

}
