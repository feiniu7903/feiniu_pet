package com.lvmama.passport.processor.impl.client.jiuwang.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实现描述：标识不需要Json序列化的属性
 * 
 * @version v1.0.0
 * @see
 * @since 
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient {

    boolean value() default true;
}
