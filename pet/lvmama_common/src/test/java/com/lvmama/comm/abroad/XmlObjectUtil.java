package com.lvmama.comm.abroad;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 * xml和javabean之间的互相转换工具
 * @author dingming
 *
 */
public class XmlObjectUtil {
	private static final String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
   
	  public static String bean2Xml(Object bean) throws Exception
	  {
	    StringWriter writer = null;
	    try {
	      JAXBContext jc = JAXBContext.newInstance(new Class[] { bean.getClass() });
	      Marshaller m = jc.createMarshaller();

	      m.setProperty("jaxb.fragment", Boolean.valueOf(true));
	      writer = new StringWriter();
	      m.marshal(bean, writer);
	      return prefix + writer.toString();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	      close(writer);
	    }
	    return null;
	  }

	  @SuppressWarnings("unchecked")
	public static <T> T xml2Bean(String xml, Class<?> clazz) throws Exception
	  {
	    StringReader reader = null;
	    try {
	      JAXBContext jc = JAXBContext.newInstance(new Class[] { clazz });
	      Unmarshaller m = jc.createUnmarshaller();
	      reader = new StringReader(xml);
	      return (T)m.unmarshal(reader);
	    } catch (Exception e) {
	    }
	    finally {
	      close(reader);
	    }
	    return null;
	  }

	private static void close(Writer writer) {
		try {
			if (writer == null)
				return;
			writer.close();
		} catch (Exception localException) {
		}
	}

	private static void close(Reader reader) {
		try {
			if (reader == null)
				return;
			reader.close();
		} catch (Exception localException) {
		}
	}
}
