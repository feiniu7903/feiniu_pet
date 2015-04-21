package com.lvmama.operate.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.operate.dao.ComCodesetDAO;

/**
 * 缓存代码集
 * @author ZhuZhengquan
 *
 */
public class CodeSet {
	private static CodeSet instance=new CodeSet();
	/**
	 * 单实例
	 * 
	 * @return 返回 Codeset 实例
	 */
	public static CodeSet getInstance() {
		return instance;
	}
	
	/**
	 * 获取codesetDAO
	 */
	private ComCodesetDAO comCodesetDAO=(ComCodesetDAO)SpringBeanProxy.getBean("comCodesetDAO");
	
	private HashMap<String, Long> cachedTimeMap= new HashMap<String, Long>();
	
	/**
	 * 缓存代码集
	 */
	private HashMap<String,List<CodeItem>> cachedSetMap=new HashMap<String,List<CodeItem>>();
	/**
	 * 以HashMap缓存，实现快速提取
	 */
	private HashMap<String,HashMap<String,String>> cachedCodeMap=new HashMap<String,HashMap<String,String>>();
	
	private boolean isExpired(String codesetCode) {
		Long updateTime = cachedTimeMap.get(codesetCode);
		return (System.currentTimeMillis()-updateTime)>60*60*1000;
	}
	
	/**
	 * 根据代码集名称，返回列表
	 * @param codeName 等于“表名称”
	 * @return
	 */
	public List<CodeItem> getCachedCodeList(String codesetCode){
		if (!cachedSetMap.containsKey(codesetCode) || isExpired(codesetCode)){
			loadCodeset(codesetCode);
		}
		List<CodeItem> ret =cachedSetMap.get(codesetCode);
		if(ret!=null)
		{
			/**
			 * 深复制的参数
			 * @author yangbin
			 */
			try
			{
				return copySearialize(ret);
			}catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		return ret;
	}
	
	public List<CodeItem> getCodeList(String codesetCode){
		return getCachedCodeList(codesetCode);
	}
	
	public List<CodeItem> getCodeListAndBlank(String codesetCode){
		List<CodeItem> list=getCodeList(codesetCode);
		if(CollectionUtils.isEmpty(list)){
			list=new ArrayList<CodeItem>();
		}
		list.add(0,new CodeItem("","请选择"));
		return list;
	}
	
	/**
	 * 使用序列化来深拷贝List
	 * @param src
	 * @return
	 * @throws Exception
	 */
	private List<CodeItem> copySearialize(List<CodeItem> src)throws Exception
	{
		ByteArrayOutputStream baos=null;
		ObjectOutputStream out=null;
		ByteArrayInputStream bin=null;
		java.io.ObjectInputStream ois=null;
		
		try
		{
			baos=new ByteArrayOutputStream();
			out=new ObjectOutputStream(baos);
			out.writeObject(src);
			
			bin=new ByteArrayInputStream(baos.toByteArray());
			ois=new java.io.ObjectInputStream(bin);
			List<CodeItem> result=(List<CodeItem>)ois.readObject();
			return result;
		}finally
		{
			close(baos);
			close(out);
			close(bin);
			close(ois);
		}
	}
	private void close(Closeable cls)
	{
		if(cls!=null)
		{
			try
			{
				cls.close();
			}catch(Exception ex)
			{
				
			}
		}
	}
	
	/**
	 * 根据代码项，返回名称
	 * @param codeset
	 * @param codeItem
	 * @return
	 */
	public String getCodeName(String codeset,String codeItem){
		 if (cachedCodeMap.get(codeset)==null || isExpired(codeset)){
			 loadCodeset(codeset);
		 }
		 return cachedCodeMap.get(codeset).get(codeItem);
	}
	
	/**
	 * 加载代码列表
	 * @param codeName
	 */
	private void loadCodeset(String codeName){
		List<CodeItem> ret = comCodesetDAO.getCodeset(codeName);
		cachedSetMap.put(codeName, ret);
		
		//
		HashMap<String,String> items=new HashMap<String,String>();
		for(int i=0;i<ret.size();i++){
			items.put(ret.get(i).getCode(), ret.get(i).getName());
		}
		cachedCodeMap.put(codeName, items);
		
		cachedTimeMap.put(codeName, System.currentTimeMillis());
	}
	
	public List<CodeItem> getBcertTargetFaxTemplate(Long targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "SUP_B_CERTIFICATE_TARGET");
		map.put("pkFieldName", "TARGET_ID");
		map.put("pkFieldValue", targetId);
		map.put("fieldName", "FAX_TEMPLATE");
		map.put("setCode", "FAX_TEMPLATE");
		return comCodesetDAO.getCodesetChecked(map);
	}
	
	public List<CodeItem> getBcertificateTargetFaxStrategy(Long targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "SUP_B_CERTIFICATE_TARGET");
		map.put("pkFieldName", "TARGET_ID");
		map.put("pkFieldValue", targetId);
		map.put("fieldName", "FAX_STRATEGY");
		map.put("setCode", "FAX_STRATEGY");
		return comCodesetDAO.getCodesetChecked(map);
	}
	
	public List<CodeItem> getSupplierCooperationStatus(Long targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "SUP_SUPPLIER");
		map.put("pkFieldName", "SUPPLIER_ID");
		map.put("pkFieldValue", targetId);
		map.put("fieldName", "CO_STATUS");
		map.put("setCode", "COOPERATION_STATUS");
		return comCodesetDAO.getCodesetChecked(map);
	}
	
	public List<CodeItem> getSupplierCompany(Long targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "SUP_SUPPLIER");
		map.put("pkFieldName", "SUPPLIER_ID");
		map.put("pkFieldValue", targetId);
		map.put("fieldName", "COMPANY_ID");
		map.put("setCode", "SETTLEMENT_COMPANY");
		return comCodesetDAO.getCodesetChecked(map);
	}
	
	public List<CodeItem> getFaxtatus(Long targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "SUP_SUPPLIER");
		map.put("pkFieldName", "SUPPLIER_ID");
		map.put("pkFieldValue", targetId);
		map.put("fieldName", "CO_STATUS");
		map.put("setCode", "COOPERATION_STATUS");
		return comCodesetDAO.getCodesetChecked(map);
	}
}
