/**
 * 
 */
package com.lvmama.comm.utils;

import java.io.StringReader;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.springframework.util.ReflectionUtils;

/**
 * @author yangbin
 *
 */
public abstract class XmlUtils {

	public static Document createDocument(String xml)throws DocumentException{
		StringReader reader = new StringReader(xml);
		SAXReader builder = new SAXReader();
		return builder.read(reader);
	}
	
	public static Document createDocument(InputSource is)throws DocumentException{
		SAXReader builder = new SAXReader();
		return builder.read(is);
	}
	
	public static String getChildElementContent(Element ele,String tagName){
		return ele.elementText(tagName);
	}
	/**
	 * 递归获得document 目录下面 某个tagName 的textValue 
	 * 如果有多个相同的tageName 只会取得最近一个tagName 的tagValue
	 * @param ele
	 * @param tagName
	 * @return
	 */
	public static String getChildText(Element ele,String tagName){
		Iterator<Element> i = ele.elementIterator();
		 while(i.hasNext()){
			 Element e = i.next();
			 if(e.getName().equals(tagName)){
				 return e.getText();
			 }
			 if(e.elementIterator().hasNext()){
				 String k = getChildText(e, tagName);
				  if(k!=null){
					  return k;
					  
				  }
			 }
		 }
		 return null;
	}
	
	
	public static void main(String[] args) throws DocumentException {
		String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?><alipay><is_success>T</is_success><request><param name=\"sign\">dc1116680576ff810846c3c1278fa6bb</param><param name=\"_input_charset\">utf-8</param><param name=\"query_token\">alixpayBe31c14c6c0484bac907670e9186c364e</param><param name=\"sign_type\">MD5</param><param name=\"service\">alipay.user.userinfo.share</param><param name=\"partner\">2088001842589142</param></request><response><contact><email>564204416@qq.com</email><is_bank_auth>F</is_bank_auth><is_certified>T</is_certified><is_id_auth>T</is_id_auth><is_licence_auth>F</is_licence_auth><is_mobile_auth>T</is_mobile_auth><mobile>13162418126</mobile><user_id>2088402886867634</user_id><user_status>T</user_status></contact></response><sign>a902924e9fcc22665227fa80abe2a0c9</sign><sign_type>MD5</sign_type></alipay>";
		Document document = XmlUtils.createDocument(xml);
		System.out.println(document.getRootElement().getName());
		System.out.println(document.getRootElement().elements().size());
		String userId = XmlUtils.getChildText(document.getRootElement(), "user_id");
		String mobile = XmlUtils.getChildText(document.getRootElement(), "mobile");
		String email = XmlUtils.getChildText(document.getRootElement(), "email");

		if(!StringUtil.isEmptyString(userId)) {
			//this.setCooperationUid(userId);
			System.out.println(userId +" "+ mobile +" "+email);
		}  if(!StringUtil.isEmptyString(mobile)){
			System.out.println(userId +" "+ mobile +" "+email);
			//this.setMobile(mobile);
		}  if(!StringUtil.isEmptyString(email)){
			System.out.println(userId +" "+ mobile +" "+email);
			//this.setEmail(email);
		}
	}

	public static Element getElement(Document doc,final String xpath){
		List<Element> list=doc.selectNodes(xpath);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 转换一个元素到bean,
	 * 数据不满足的情况下会返回空
	 * @param clazz
	 * @param element
	 * @param annotation
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T toBean(Class<T> clazz,Element element){
		T instance = null;
		try {
			instance = clazz.newInstance();
			Method[] methods=ReflectionUtils.getAllDeclaredMethods(clazz);
			Map<String,Method> map = new HashMap<String, Method>();
			for(Method m:methods){
				if(m.getName().startsWith("set")&&m.getReturnType()==void.class){
					Class[] paramClazz=m.getParameterTypes();
					if(paramClazz.length==1){
						map.put(m.getName().substring(3), m);
					}
				}
			}
			
			boolean flag;
			List<Attribute> attrs=element.attributes();
			for(Attribute a:attrs){
				if(map.containsKey(a.getName())){
					Method mm = map.get(a.getName());
					flag=fill(mm,instance,a.getText());
					if(flag){
						map.remove(a.getName());
					}
				}
			}
			List<Element> eleList=element.elements();
			if(!map.isEmpty()&& CollectionUtils.isNotEmpty(eleList)){
				for(Element ele:eleList){
					if(map.containsKey(ele.getName())){
						Method m =map.get(ele.getName());
						
						flag = fill(m, instance, ele.getText());
						if(flag){
							map.remove(ele.getName());
						}
					}
					if(map.isEmpty()){
						break;
					}
					
					attrs=ele.attributes();
					for(Attribute a:attrs){
						if(map.containsKey(a.getName())){
							Method mm = map.get(a.getName());
							flag=fill(mm,instance,a.getText());
							if(flag){
								map.remove(a.getName());
							}
						}
					}
					
					if(map.isEmpty()){
						break;
					}
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return instance;
	}
	
	private static boolean fill(Method m,Object obj,String val){
		boolean flag=false;
		ReflectionUtils.makeAccessible(m);
		Class[] paramClazz=m.getParameterTypes();
		if(ArrayUtils.contains(types, paramClazz[0])){
			Object arg=conver(paramClazz[0], val);
			try {
				m.invoke(obj, arg);
				flag=true;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return flag;
	}
	public static final <T> List<T> toBeanList(Class<T> clazz,Element ele){
		return toBeanList(clazz,ele,null);
	}
	/**
	 * 根据xml元素转转换成对象列表
	 * @param clazz
	 * @param ele
	 * @param tagName
	 * @return
	 */
	public static final <T> List<T> toBeanList(Class<T> clazz,Element ele,String tagName){
		List<Element> list = tagName==null?ele.elements():ele.elements(tagName);
		List<T> result = new ArrayList<T>();
		for(Element e:list){
			T obj = toBean(clazz, e);
			result.add(obj);
		}
		return result;
	}

	private static final Class[] types = { String.class, int.class, long.class,
			double.class, float.class, boolean.class, Integer.class,
			Long.class, Double.class, Float.class, Boolean.class };
	
	private static Object conver(Class<?> clazz,String val){
		if(clazz.equals(Long.class)||clazz.equals(long.class)){
			return NumberUtils.toLong(val);
		}else if(clazz.equals(Integer.class)||clazz.equals(int.class)){
			return NumberUtils.toInt(val);
		}else if(clazz.equals(Float.class)||clazz.equals(float.class)){
			return NumberUtils.toFloat(val);
		}else if(clazz.equals(Double.class)||clazz.equals(double.class)){
			return NumberUtils.toDouble(val);
		}else if(clazz.equals(Boolean.class)||clazz.equals(boolean.class)){
			return BooleanUtils.toBoolean(val);
		}else{
			return val;
		}
	}
}
