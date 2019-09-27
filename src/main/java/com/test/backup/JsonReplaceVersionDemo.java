package com.test.backup;

import com.test.model.Student;
import com.test.model.Teacher;
import com.test.service.VersionInterface;
import com.test.utils.serializer.DeserializerUtils;
import com.test.utils.serializer.SerializerUtils;

public class JsonReplaceVersionDemo {
    public static void main(String[] args) {
        String content = "{\"num\":\"123\",\"name\":\"2222\",\"version\":\"22\"}";

        int version = DeserializerUtils.deserialize(content, getClassByOrder(true)).getVersion();

        System.out.println(SerializerUtils.serialize(
                DeserializerUtils.deserialize(content,
                        getClassByOrder(true)).setVersion(version + 1)));

    }

    private static Class<? extends VersionInterface> getClassByOrder(boolean flag) {

        if (flag) {
            return Teacher.class;
        }

        return Student.class;
    }

}
