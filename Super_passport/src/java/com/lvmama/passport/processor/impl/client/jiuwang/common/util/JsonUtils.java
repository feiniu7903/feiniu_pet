package com.lvmama.passport.processor.impl.client.jiuwang.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lvmama.passport.processor.impl.client.jiuwang.common.Transient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 实现描述：Json处理方法，封装fastJson
 * 
 * 
 * @version v1.0.0
 * @see
 * @since 
 */
public class JsonUtils {

    public static byte[] marshalToByte(Object obj) {
        return JSON.toJSONBytes(obj); // 默认为UTF-8
    }

    public static byte[] marshalToByte(Object obj, SerializerFeature... features) {
        return JSON.toJSONBytes(obj, features); // 默认为UTF-8
    }

    public static String marshalToString(Object obj) {
        return JSON.toJSONString(obj); // 默认为UTF-8
    }

    public static String marshalToString(Object obj, SerializerFeature... features) {
        return JSON.toJSONString(obj, features); // 默认为UTF-8
    }

    /**
     * 可以允许指定一些过滤字段进行生成json对象
     */
    public static String marshalToString(Object obj, String... fliterFields) {
        final List<String> propertyFliters = Arrays.asList(fliterFields);
        SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.getPropertyFilters().add(new PropertyFilter() {

                @Override
                public boolean apply(Object source, String name, Object value) {
                    return !propertyFliters.contains(name);
                }

            });
            serializer.write(obj);
            return out.toString();
        } finally {
            out.close();
        }
    }

    public static String marshalToStringWithoutTransient(Object obj) {
        // 获取忽略字段
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> transientFileds = new ArrayList<String>();
        for (Field f : fields) {
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }

            Transient anno = f.getAnnotation(Transient.class);
            if (anno != null && anno.value()) {
                transientFileds.add(f.getName());
            }
        }

        return JsonUtils.marshalToString(obj, transientFileds.toArray(new String[transientFileds.size()]));
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshalFromByte(byte[] bytes, Class<T> targetClass) {
        return (T) JSON.parseObject(bytes, targetClass);// 默认为UTF-8
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshalFromByte(byte[] bytes, TypeReference<T> type) {
        return (T) JSON.parseObject(bytes, type.getType());
    }

    public static <T> T unmarshalFromString(String json, Class<T> targetClass) {
        return JSON.parseObject(json, targetClass);// 默认为UTF-8
    }

    public static <T> T unmarshalFromString(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);// 默认为UTF-8
    }

    public static <T> List<T> unmarshalFromString2List(String json, Class<T> targetClass) {
        return JSON.parseArray(json, targetClass); // 默认为UTF-8
    }
}
