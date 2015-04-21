package test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.lvmama.passport.utils.HttpsUtil;

public class YongliguolvTest {
	@Test
	public void test() throws Exception{
		String xmlStr = this.getXmlStr("Yongliguolv.xml");
		String url = "http://192.168.0.2432/passport/yongliguolv/transCheck.do";
		Map<String , String > requestParas = new HashMap<String, String>();
		requestParas.put("xmlTransCheck", xmlStr);
		String response = HttpsUtil.requestPostForm(url, requestParas);
		System.out.println(response);
	}
	
	
	String getXmlStr(String fileName) throws Exception{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("test/"+fileName);
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(inputStream);
		return document.asXML();
	}
}
