package com.lvmama.comm.hessian;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * 注解Service是远程Service
 * 
 * <pre>
 * <b>当远程服务Service与本地Service名称一致时，只需要指定本地Service名称即可</b>
 * <b>当远程服务Service的远程服务地址与Scanner参数不一致时，可以单独设置remoteServer属性，属性值为const.properties配置文件中的KEY</b>
 * </pre>
 * 
 * @author yanggan
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteService {

	/**
	 * 本地Service名称
	 */
	String value();

	/**
	 * 远程Service名称（默认不指定时与本地Service名称一致）
	 */
	String remoteService() default "";

	/**
	 * 远程服务地址
	 * 默认由Scanner参数指定，参数不满足的情况下，可以单独设置此属性，属性值为const.properties配置文件中的KEY
	 */
	String remoteServer() default "";

	/**
	 * 否使用chunked端口发送Hessian请求（默认为false）
	 */
	boolean chunkedPost() default false;
	
	/**
	 * 远程调用时是否重载方法(默认为true)
	 */
	boolean overloadEnabled() default true;

}
