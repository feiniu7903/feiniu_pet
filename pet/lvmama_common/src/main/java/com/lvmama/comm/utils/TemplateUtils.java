package com.lvmama.comm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 模板解析工具类.
 * 用途: 可在对接时用于解析XML格式的请求和响应.
 * 
 * @author qiuguobin
 */
public class TemplateUtils {
	private static Map<String, Template> templatePools = new HashMap<String, Template>();
	
	private TemplateUtils() {
	}

	/**
	 * 填充数据模型到指定模板文件并以字符串形式返回.
	 * 
	 * @param templateDir 模板目录的路径,绝对路径以'/'开头,从classpath开始;相对路径不需要'/',从当前目录开始
	 * @param templateFilename 模板文件名
	 * @param model 数据模型
	 * @return 合并后的字符串模板
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static String fillFileTemplate(String templateDir, String templateFilename, Object model) throws IOException, TemplateException {
		String key = templateDir + templateFilename;
		Template t = (Template) templatePools.get(key);
		if (t == null) {
			Configuration cfg = new Configuration();
			cfg.setTemplateLoader(new ClassTemplateLoader(TemplateUtils.class, templateDir));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setClassicCompatible(true);
			cfg.setNumberFormat("#");
			t = cfg.getTemplate(templateFilename);
			templatePools.put(key, t);
		}
		StringWriter writer = new StringWriter();
		t.process(model, writer);
		return outputSingleLine(writer.toString());
	}
	
	/**
	 * 连成一行输出.
	 */
	private static String outputSingleLine(String s) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(s));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line.trim().replaceAll("\\t", ""));
		}
		return sb.toString();
	}

	/**
	 * 填充数据模型到指定字符串模板并以字符串形式返回.
	 * 
	 * @param stringTemplate 字符串模板
	 * @param model 数据模型
	 * @return 合并后的字符串模板
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static String fillStringTemplate(String stringTemplate, Object model) throws IOException, TemplateException {
		String key = stringTemplate;
		Template t = (Template) templatePools.get(key);
		if (t == null) {
			Configuration cfg = new Configuration();
			cfg.setTemplateLoader(new StringTemplateLoader(stringTemplate));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setClassicCompatible(true);
			cfg.setNumberFormat("#");
			t = cfg.getTemplate("");
			templatePools.put(key, t);
		}
		StringWriter writer = new StringWriter();
		t.process(model, writer);
		return writer.toString();
	}
	
	/**
	 * 编码<code>elementName</code>的所有子元素,不参与编码的元素全部原样输出.
	 * 
	 * @param stringTemplate 字符串模板
	 * @param elementName 需要编码的元素名
	 * @param includeSelf 元素自身是否也参与编码
	 * @param encoder 自定义编码实现
	 * @return 编码后的字符串模板
	 * @throws Exception
	 */
	public static String encodeStringTemplate(String stringTemplate, String elementName, boolean includeSelf, XMLEncoder encoder) throws Exception {
		if (encoder == null) {
			throw new Exception("XMLEncoder cannot be null.");
		}
		Document doc = getDocument(stringTemplate);
		Element root = doc.getRootElement();
		StringBuffer sb = new StringBuffer();
		encodeDocument(sb, root, elementName, includeSelf, encoder);
		return sb.toString();
	}
	
	/**
	 * 获取XML文档对象.
	 * 
	 * @param stringTemplate 字符串模板
	 * @return XML文档对象
	 * @throws DocumentException
	 */
	private static Document getDocument(String stringTemplate) throws DocumentException {
		StringReader sr = new StringReader(stringTemplate);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(sr);
        return doc;
	}
	
	/**
	 * 编码<code>elementName</code>的所有子元素,不参与编码的元素全部原样输出.
	 * 
	 * @see #encodeStringTemplate(String, String, boolean, XMLEncoder)
	 */
	@SuppressWarnings("unchecked")
	private static void encodeDocument(StringBuffer sb, Element element, String elementName, boolean includeSelf, XMLEncoder encoder) throws Exception {
		sb.append(toOpenElementString(element));
		sb.append(element.getTextTrim());
		List<Element> list = element.elements();
		for(Element e : list) {
			if(e.getName().equals(elementName)) {
				sb.append(toOpenElementString(e));
				String plaintext = e.asXML();
				if (!includeSelf) {//元素自身不参与编码
					plaintext = plaintext.replaceFirst(toOpenElementString(e), "");
					plaintext = plaintext.replaceFirst(toCloseElementString(e), "");
				}
				String ciphertext = encoder.encode(plaintext);
				sb.append(ciphertext);
				sb.append(toCloseElementString(e));
				break;
			} else {
				encodeDocument(sb, e, elementName, includeSelf, encoder);
			}
		}
		sb.append(toCloseElementString(element));
	}
	
	/**
	 * 获取字符串模板中指定元素的值.
	 * 
	 * @param stringTemplate 字符串模板
	 * @param elementPath 元素路径,以"//"或"/"开头,"//"表示从相对路径开始查找,"/"表示从绝对路径开始查找
	 * @return 单个或多个元素值
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getElementValues(String stringTemplate, String elementPath) throws DocumentException {
		List<String> values = new ArrayList<String>();
		Document doc = getDocument(stringTemplate);
		List<Element> list = doc.selectNodes(elementPath);
		for (Element e : list) {
			values.add(e.getTextTrim());
		}
		return values;
	}

	public static Map<String,String> getElementAttributeValues(String stringTemplate, String elementPath) throws DocumentException {
		Map<String,String> attributesMap=new HashMap<String,String>();
		Document doc = getDocument(stringTemplate);
		List<Element> list = doc.selectNodes(elementPath);
		for (Element e : list) {
			attributesMap=toElementAttributesMap(e);
		}
		return attributesMap;
	}
	/**
	 * 获取字符串模板中指定元素的值.
	 * 
	 * @param stringTemplate 字符串模板
	 * @param elementPath 元素路径,以"//"或"/"开头,"//"表示从相对路径开始查找,"/"表示从绝对路径开始查找
	 * @return 单个元素值
	 * @throws DocumentException
	 * @see #getElementValues(String, String)
	 */
	public static String getElementValue(String stringTemplate, String elementPath) throws DocumentException {
		List<String> values = getElementValues(stringTemplate, elementPath);
		if (values.size() > 0) {
			return values.get(0);
		}
		return null;
	}

	
	/**
	 * 输出元素属性.
	 */
	@SuppressWarnings("unchecked")
	private static String toElementAttributesString(Element e) {
		String str = "";
		List<Attribute> attrs = e.attributes();
		for(Attribute attr : attrs) {
			str += " " + attr.getName() + "=\"" + attr.getValue() + "\"";
		}
		return str;
	}
	
	/**
	 * 输出元素属性返回map集合.
	 */
	@SuppressWarnings("unchecked")
	private static Map<String,String> toElementAttributesMap(Element e) {
		Map<String,String> attributesMap=new HashMap<String,String>();
		List<Attribute> attrs = e.attributes();
		for(Attribute attr : attrs) {
			attributesMap.put(attr.getName(), attr.getValue());
		}
		return attributesMap;
	}
	
	/**
	 * 输出元素打开标签.
	 */
	private static String toOpenElementString(Element e) {
		return "<" + e.getName() + toElementAttributesString(e) + ">"; 
	}
	
	/**
	 * 输出元素关闭标签.
	 */
	private static String toCloseElementString(Element e) {
		return "</" + e.getName() + ">";
	}
	
	/**
	 * 美化输出XML字符串.
	 */
	public static String formatXml(String text) throws Exception {
		Document document = DocumentHelper.parseText(text);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter out = new StringWriter();
		XMLWriter output = new XMLWriter(out, format);
		output.write(document);
		out.close();
		return out.toString();
	}
	
	private static class StringTemplateLoader implements TemplateLoader {
		private String template;
		
		public StringTemplateLoader(String template) {
			this.template = template;
		}
		
		@Override
		public Object findTemplateSource(String name) throws IOException {
			return new StringReader(template);
		}
		
		@Override
		public Reader getReader(Object templateSource, String encoding) throws IOException {
			return (Reader) templateSource;
		}
		
		@Override
		public long getLastModified(Object templateSource) {
			return 0;
		}
		
		@Override
		public void closeTemplateSource(Object templateSource) throws IOException {
			((StringReader) templateSource).close();
		}
	}
	
	public interface XMLEncoder {
		public String encode(String data) throws Exception;
	}
}


