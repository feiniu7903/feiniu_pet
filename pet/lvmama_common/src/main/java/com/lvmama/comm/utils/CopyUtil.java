/**
 * 
 */
package com.lvmama.comm.utils;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.ReflectionUtils;

/**
 * 表单与实体指定的值复制.
 * @author yangbin
 *
 */
public abstract class CopyUtil {

	/**
	 * 复制源对象当中指定的属性到目标对象
	 * @param <T> 
	 * @param target 目标对象，返回对象也是该值
	 * @param source 复制源
	 * @param params request传过来的参数列表
	 * @param prefix 参数前辍
	 * @param fields 必须要复制的参数列表,不需要前辍,直接由对象的属性定义
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> T copy(T target,T source,Enumeration params,String prefix,List<String> fields){
		Set<String> existsFields=new HashSet<String>();
		while(params.hasMoreElements()){
			String f=params.nextElement().toString();
			if(f.startsWith(prefix)){
				f=f.replace(prefix, "");
				existsFields.add(f);
				invokeSetProp(source,target,f);
			}
		}
		
		for(String f:fields){
			if(!existsFields.contains(f)){
				invokeSetProp(source, target, f);
			}
		}
		return target;
	}
	
	/**
	 * 字段是否需要复制，指request当中的参数
	 * @author yangbin
	 *
	 */
	public static interface PropCallback{
		/**
		 * 需要
		 * @param field
		 * @return
		 */
		boolean allowField(final String field);
	}
	
	@SuppressWarnings("unchecked")
	public static<T> T copy(T target,T source,Enumeration params,String prefix,PropCallback callback){
		while(params.hasMoreElements()){
			String f=params.nextElement().toString();
			if(f.startsWith(prefix)){
				f=f.replace(prefix, "");
				if(callback.allowField(f)){
					invokeSetProp(source,target,f);
				}
			}
		}		
		return target;
	}
	
	@SuppressWarnings("unchecked")
	public static<T> T copy(T target,T source,Enumeration params,String prefix){
		Set<String> existsFields=new HashSet<String>();
		while(params.hasMoreElements()){
			String f=params.nextElement().toString();
			if(f.startsWith(prefix)){
				f=f.replace(prefix, "");
				existsFields.add(f);
				invokeSetProp(source,target,f);
			}
		}		
		return target;
	}
	
	public static<T> T copy(T target,T source,List<String> fields){
		for(String f:fields){
			invokeSetProp(source, target, f);
		}
		return target;
	}
	
	private static<T> void invokeSetProp(T form,T entity,String f){
		try{
			String prop=String.valueOf(f.charAt(0)).toUpperCase()+f.substring(1);
			Method method=ReflectionUtils.findMethod(form.getClass(), "get"+prop);
			if(method==null){
				prop=f;
				method=ReflectionUtils.findMethod(form.getClass(), "get"+prop);
			}
			
			Object obj=method.invoke(form);
			Class clazz=method.getReturnType();			
			method=ReflectionUtils.findMethod(entity.getClass(), "set"+prop,clazz);				
			method.invoke(entity, obj);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
