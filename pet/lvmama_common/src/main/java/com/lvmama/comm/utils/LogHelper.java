/**
 * 
 */
package com.lvmama.comm.utils;

import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.ReflectionUtils;

import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.vo.Constant;

/**
 * 日志记录类，对LogViewUtil的操作优化处理
 * 
 * 操作方式
 * <code>
 * LogHelper<Demo> logHelper=LogHelper.makeLog(obj);
 * logHelper.log(fieldName,fieldNameCn);
 * 
 * logHelper.toResult();
 * </code>
 * @author yangbin
 *
 */
public class LogHelper<T> {
	Logger log = Logger.getLogger(LogHelper.class);
	public static enum LOG_TYPE{
		
	} 

	private T oldObject;
	private T newObject;
	private Class<T> clazz;
	private Map<Class,PropertyEditor> propEditorMap=new HashMap<Class, PropertyEditor>();
		
	/**
	 * 创建的对象是否是新创建
	 */
	private boolean isNew=true;
	
	private StringBuffer content;
	/**
	 * 构造
	 * @param oldObject 数据库当中已经存在的对象
	 * @param newObject 新的对象
	 */
	public LogHelper(Class<T> clazz,T oldObject, T newObject) {
		this(clazz);
		this.oldObject = oldObject;
		this.newObject = newObject;
		this.isNew=false;
	}
	/**
	 * 构建的一个全新的对象的日志
	 * @param newObject
	 */
	public LogHelper(Class<T> clazz,T newObject) {
		this(clazz);
		this.newObject = newObject;		
	}
	
	/**
	 * 注册属性格式化类型
	 * @param clazz
	 * @param pe
	 */
	public<TT> void register(Class<TT> clazz,PropertyEditor pe){
		propEditorMap.put(clazz, pe);
	}
	
	protected LogHelper(Class<T> clazz){
		super();
		content=new StringBuffer();
		this.clazz=clazz;
		register(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	/**
	 * 加入需要记录日志的属性
	 * @param prop 属性名称
	 * @param propLabel 属性中文名称
	 */
	public void log(String prop,String propLabel){
		try {
			Field field=clazz.getDeclaredField(prop);
			ReflectionUtils.makeAccessible(field);			
			Object newVal=field.get(newObject);
			if(isNew){
				if(!isNullOrEmpty(newVal)){
					logAdd(propLabel, newVal);
				}
			}else{//编辑对象
				Object oldVal=field.get(oldObject);
				if (notEquals(oldVal, newVal)) {
					logEdit(propLabel,oldVal,newVal);
				}
			}
			
		} catch (Exception e) {
		} 
	}
	
	/**
	 * 加入需要记录日志的属性
	 * @param prop 属性名称
	 * @param propLabel 属性中文名称
	 */
	public void log(String prop,String propLabel,String methodName){
		try {
			Field field=clazz.getDeclaredField(prop);
			ReflectionUtils.makeAccessible(field);		
			java.lang.reflect.Method method=clazz.getMethod(methodName);
			Object newVal=field.get(newObject);
			Object zhNewVal=method.invoke(newObject);					
			if(isNew){
				if(!isNullOrEmpty(newVal)){
					logAdd(propLabel, zhNewVal);
				}
			}else{//编辑对象
				Object oldVal=field.get(oldObject);
				if (notEquals(oldVal, newVal)) {
					Object zhOldVal=method.invoke(oldObject);
					logEdit(propLabel,zhOldVal,zhNewVal);
				}
			}
			
		} catch (Exception e) {
		} 
	}
	
	private void logAdd(String propLabel,Object newVal){
		content.append("创建了 [");
		content.append(propLabel);
		content.append("],新值为\"");
		content.append(format(newVal));
		content.append("\";");
	}
	
	private void logEdit(String propLabel,Object oldVal,Object newVal){
		//如果有变动，记录日志		
		content.append("修改了[");
		content.append(propLabel);
		content.append("],");
		content.append("旧值为\"");
		content.append(format(oldVal));
		content.append("\",新值为\"");
		content.append(format(newVal));
		content.append("\";");
	}
	
	private boolean notEquals(Object oldVal,Object newVal){
		String tmpOld,tmpNew;
		if(isNullOrEmpty(oldVal)){
			tmpOld="";
		}else{
			tmpOld=oldVal.toString();
		}
		if(isNullOrEmpty(newVal)){
			tmpNew="";
		}else{
			tmpNew=newVal.toString();
		}
		return !StringUtils.equals(tmpNew, tmpOld);
	}
	
	private boolean isNullOrEmpty(Object val){
		if(val instanceof String){
			return (StringUtils.isEmpty(String.class.cast(val)));
		}else{
			return val==null;
		}
	}
	
	/**
	 * 格式化数据类型
	 * @param obj
	 * @return
	 */
	private Object format(Object obj){
		if(isNullOrEmpty(obj)){
			return "";
		}
		Class clz=obj.getClass();
		if(propEditorMap.containsKey(clz)){
			PropertyEditor pe=propEditorMap.get(clz);			
			pe.setValue(obj);
			return pe.getAsText();
		}else{
			return obj;
		}
	}
	
	/**
	 * 由调用方自己给出对比值处理
	 * @param propLabel
	 * @param oldVal
	 * @param newVal
	 */
	public<TT> void log(String propLabel,TT oldVal,TT newVal){
		if(isNew){
			logAdd(propLabel, newVal);
		}else{
			logEdit(propLabel, oldVal, newVal);
		}
	}
	
	public String toResult(){
		return content.toString();
	}
	
	@Override
	public String toString(){
		return "LogHelp class:"+clazz.getName();
	}
	
	public static<T> LogHelper<T> makeLog(T newObj){
		Class clazz=newObj.getClass();
		return new LogHelper<T>(clazz,newObj);		
	}
	public static<T> LogHelper<T> makeLog(T oldObj,T newObj){		
		Class clazz=newObj.getClass();
		return new LogHelper<T>(clazz,oldObj,newObj);		
	}
	
	public static void main(String[] args) throws Exception{
		SupSupplier ss=new SupSupplier();
		ss.setAddress(null);
		ss.setCityId("yyyyyy");	
		ss.setSupplierType(Constant.SUPPLIER_TYPE.AIRLINE.name());
		ss.setCreateTime(new java.util.Date());
		SupSupplier s2=new SupSupplier();
		s2.setAddress("");
		s2.setCityId("yyyyyy");
		s2.setCityName("上海");
		s2.setSupplierType(Constant.SUPPLIER_TYPE.INSURANCE.name());
		s2.setSupplierId(10L);
		s2.setCreateTime(DateUtils.addDays(new Date(), 3));
		LogHelper<SupSupplier> logHelper=LogHelper.makeLog(ss);
		System.out.println(ss.getCreateTime().equals(s2.getCreateTime()));
		logHelper.log("address", "地址");
		logHelper.log("cityId","市ID");
		logHelper.log("cityName", "城市名称");
		logHelper.log("supplierId","供应商ID");
		logHelper.log("createTime","时间");
		logHelper.log("supplierType","类型","getZhSupplierType");
		logHelper.log("省份", "上海", "黑龙江");
		System.out.println(logHelper.toResult());
	}
}
