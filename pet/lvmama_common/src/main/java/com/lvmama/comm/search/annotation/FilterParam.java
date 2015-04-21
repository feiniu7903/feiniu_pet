package com.lvmama.comm.search.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 筛选条件的编码
 * 
 * @author YangGan
 *
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.FIELD)  
public @interface FilterParam {
	String value();
	/**
	 * 是否转码 默认值:转码
	 * @return
	 */
	boolean transcode() default true;
}
