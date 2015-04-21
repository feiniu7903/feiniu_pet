package com.lvmama.comm.hessian;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注解Service是HessianService
 * 
 * <pre>
 * <b>HessianService实现接口时需注意，接口的顺序必须为第一个</b>
 * </pre>
 * 
 * @author yanggan
 * 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HessianService {
	
	/**
	 * 远程服务名称
	 */
    String value() default "";
}
