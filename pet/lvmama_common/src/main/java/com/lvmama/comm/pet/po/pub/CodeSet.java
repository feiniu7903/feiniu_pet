package com.lvmama.comm.pet.po.pub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;

/**
 * 缓存代码集
 * @author ZhuZhengquan
 *
 */
public class CodeSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2963403368632116303L;

	private static final Log LOG = LogFactory.getLog(CodeSet.class);
	
	private static CodeSet instance=new CodeSet();
	/**
	 * 单实例
	 * 
	 * @return 返回 Codeset 实例
	 */
	public static CodeSet getInstance() {
		return instance;
	}
	
	
	private HashMap<String, Long> cachedTimeMap= new HashMap<String, Long>();
	
	/**
	 * 缓存代码集
	 */
	private HashMap<String,List<CodeItem>> cachedSetMap=new HashMap<String,List<CodeItem>>();
	/**
	 * 以HashMap缓存，实现快速提取
	 */
	private HashMap<String,HashMap<String,String>> cachedCodeMap=new HashMap<String,HashMap<String,String>>();

	
	/**
	 * 根据代码集名称，返回列表
	 * @param codeName 等于“表名称”
	 * @return
	 */
	public List<CodeItem> getCachedCodeList(String codesetCode){
		if (!cachedSetMap.containsKey(codesetCode)){
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
	
	/**
	 * 加载代码列表
	 * @param codeName
	 */
	private void loadCodeset(String codeName){
		try{
			String className = Constant.class.getName() + "$"+codeName;
			Class<?> clazzParent = Class.forName(Constant.class.getName());
			Class<?>[] classA = clazzParent.getClasses();
			for (Class<?> class1 : classA) {
				if (class1.getName().equals(className)) {
					Class<?> clazz = Class.forName(className);
					Field field[] = clazz.getFields();
					List<CodeItem> list = new ArrayList<CodeItem>();
					for (Field field2 : field) {
						Method Method[] = clazz.getMethods();
						Object value = null;
						Object attr1 = null;
						for (Method method2 : Method) {
							if ("getCnName".equals(method2.getName())) {
								Method method = clazz.getMethod("getCnName",String.class);
								value = method.invoke(null, field2.getName());
							}
							if ("getAttr1".equals(method2.getName())) {
								Method method = clazz.getMethod("getAttr1",String.class);
								attr1 = method.invoke(null, field2.getName());
							}
						}
						if (value!=null) {
							CodeItem item = new CodeItem();
							item.setCode(field2.getName());
							item.setName(value.toString());
							if (attr1!=null) {
								item.setAttr01(attr1.toString());
							}
							list.add(item);
						}
					}
					cachedSetMap.put(codeName, list);
					HashMap<String,String> items=new HashMap<String,String>();
					for(int i=0;i<list.size();i++){
						items.put(list.get(i).getCode(), list.get(i).getName());
					}
					cachedCodeMap.put(codeName, items);
					cachedTimeMap.put(codeName, System.currentTimeMillis());
				}
			}
			if (cachedSetMap.get(codeName)==null) {
				LOG.error("CODE " + codeName + " did not exists.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
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
		 if (cachedCodeMap.get(codeset)==null){
			loadCodeset(codeset);
		 }
		 Map<String,String> map = cachedCodeMap.get(codeset);
		 if (map!=null) {
			 return map.get(codeItem);
		 }else{
			 LOG.error(codeset + "is not exist.");
			 return "";
		 }
	}
	
	public static void main(String[] args) {
		List<CodeItem> list = CodeSet.getInstance().getCachedCodeList("WAIT_PAYMENT");
		for (CodeItem codeItem : list) {
			System.out.println(codeItem.getAttr01());
		}
	}
	
}
