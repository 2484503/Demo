package com.test.utils.serializer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/8/21 10:16
 */
public class AceConverter {

    @SneakyThrows
    public static <T> T convert(Object object, Class<T> clazz) {
        return AceDeserializer.deserialize(AceSerializer.serialize(object), clazz);
    }

    @SneakyThrows
    public static <T> List<T> convertList(Object object, Class<T> clazz) {
        return AceDeserializer.deserializeList(AceSerializer.serialize(object), clazz);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> convertMap(Object object, Class<K> keyClass, Class<V> valueClazz) {
        return AceDeserializer.deserializeMap(AceSerializer.serialize(object), keyClass, valueClazz);
    }

    @SneakyThrows
    public static <R, T extends R, E> T convertMapGenericType(Object object, Class<R> clazz, Class<E> genericClass) {
        return AceDeserializer.deserializeMapGenericType(AceSerializer.serialize(object), clazz, genericClass);
    }

    @SneakyThrows
    public static <T> T convert(String src, Class<?> clazz, Class<?> parameterClasses) {
        JavaType javaType = JacksonObjectMapper.getInstance().getTypeFactory().constructParametricType(clazz, parameterClasses);
        return JacksonObjectMapper.getInstance().readValue(src, javaType);
    }

}
