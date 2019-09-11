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
public class AceDeserializer {

    @SneakyThrows
    public static <T> T deserialize(String src, Class<T> clazz) {
        return JacksonObjectMapper.getInstance().readValue(src, clazz);
    }

    @SneakyThrows
    public static <T> List<T> deserializeList(String src, Class<T> clazz) {
        CollectionType typeReference = JacksonObjectMapper.getInstance().getTypeFactory().constructCollectionType(List.class, clazz);
        return JacksonObjectMapper.getInstance().readValue(src, typeReference);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> deserializeMap(String src, Class<K> keyClass, Class<V> valueClazz) {
        MapType typeReference = JacksonObjectMapper.getInstance().getTypeFactory().constructMapType(Map.class, keyClass, valueClazz);
        return JacksonObjectMapper.getInstance().readValue(src, typeReference);
    }

    @SneakyThrows
    public static <R, T extends R, E> T deserializeMapGenericType(String src, Class<R> clazz, Class<E> genericClass) {
        JavaType javaType = JacksonObjectMapper.getInstance().getTypeFactory().constructParametricType(clazz, genericClass);
        return JacksonObjectMapper.getInstance().readValue(src, javaType);
    }

    @SneakyThrows
    public static <T> T deserialize(String src, Class<?> clazz, Class<?> parameterClasses) {
        JavaType javaType = JacksonObjectMapper.getInstance().getTypeFactory().constructParametricType(clazz, parameterClasses);
        return JacksonObjectMapper.getInstance().readValue(src, javaType);
    }

}
