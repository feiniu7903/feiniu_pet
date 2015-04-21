package com.lvmama.passport.processor.impl.client.yzspringmoonlit;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.passport.processor.impl.exception.TicketTypeNonexistentException;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 《春江花月夜-唯美扬州》实景演出  帮助类
 * @author linkai
 *
 */
public class YzSpringMoonlitHelp {
	/**
	 * 该字段表示查询是否成功的返回值
	 */
	public static final String QUERY_TICKET_FLAG_FIELD = "queryTicketFlag";
	/**
	 * 查询返回消息字段
	 */
	public static final String QUERY_RETURN_INFO_FIELD = "queryReturnInfo";
	/**
	 * 查询成功
	 */
	public static final String QUERY_TICKET_SUCCESS = "1";
	/**
	 * 查询失败
	 */
	public static final String QUERY_TICKET_FAILED = "0";
	
	
	/**
	 * 票务查询接口
	 * @param visitTime	 	游玩时间
	 * @param ticketNo		票种编号
	 * @param sectionNo		区域编号
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryTicket(String visitTime, String ticketNo, String sectionNo) throws Exception {
		Map<String, String> map;
		
		String userName = WebServiceConstant.getProperties("yzspringMoonlit.username");
		String password = WebServiceConstant.getProperties("yzspringMoonlit.password");
		// 查询参数
		Map<String,String> requestParas=new LinkedHashMap<String, String>();
		requestParas.put("playDate", visitTime);
		requestParas.put("userName", userName);
		requestParas.put("password", password);
		String check = makeSign(requestParas);
		requestParas.put("checkValue", check);
		
		String url = WebServiceConstant.getProperties("yzspringMoonlit.order.query");
		String resXml = HttpsUtil.requestPostForm(url, requestParas);
		String rspCode = TemplateUtils.getElementValue(resXml, "//result/code");
		String rspDesc = TemplateUtils.getElementValue(resXml, "//result/message");
		// 返回成功
		if (rspCode.equals("0")) {
			map = queryTicketFilter(resXml, ticketNo, sectionNo);
		} else {
			map = new HashMap<String, String>();
			map.put(QUERY_TICKET_FLAG_FIELD, QUERY_TICKET_FAILED);
			map.put(QUERY_RETURN_INFO_FIELD, rspDesc);
		}
		return map;
	}
	
	/**
	 * 对查询结果进行过滤
	 * @param resXml
	 * @param ticketNo
	 * @param sectionNo
	 * @return
	 * @throws DocumentException
	 */
	private Map<String, String> queryTicketFilter(String resXml, String ticketNo, String sectionNo) throws DocumentException {
		// 场次编号
		String programTimeNo = TemplateUtils.getElementValue(resXml, "//result/programTime/programTimeNo");
		// 准备匹配结果
		Map<String, String> eqMap = new HashMap<String, String>();
		eqMap.put("ticketNo", ticketNo);
		eqMap.put("sectionNo", sectionNo);
		// String[] params = {"leftSeat"};
		// List<Map<String, String>> list = getXmlParams(resXml, "//result/programTime/ticket", eqMap, params);
		// 匹配ticket下的所有子项
		Map<String, String> map = getXmlParam(resXml, "//result/programTime/ticket", eqMap);
		if (!map.isEmpty()) {
			map.put("programTimeNo", programTimeNo);
			map.put(QUERY_TICKET_FLAG_FIELD, QUERY_TICKET_SUCCESS);
		} else {
			throw new TicketTypeNonexistentException("您选择的票种不存在，请选择其他票种。 ");
		}
		return map;
	}
	
	/**
	 * 生成签名信息
	 * @return
	 */
	public String makeSign(Map<String, String> params)throws Exception {
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		boolean first = true;
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
				if (first) {
					prestr = prestr + value;
					first = false;
				} else {
					prestr = prestr + value;
				}
		}
		String key = WebServiceConstant.getProperties("yzspringMoonlit.key");
		String sign = MD5.encode32(prestr+key);
		return sign;
	}
	
	/**
	 * 根据 判断条件来获取对应的返回值
	 * @param xml		xml源
	 * @param xPath		路劲集合
	 * @param eqMap		判断条件
	 * @param reParams	需要返回的字段
	 * @return
	 * @throws DocumentException
	 */
	public List<Map<String, String>> getXmlParams(String xml, String xPath, Map<String, String> eqMap, String[] reParams) throws DocumentException {
        return getXmlParams(xml, xPath, eqMap, reParams, false);
	}
	
	/**
	 * 根据 判断条件来获取对应的返回值
	 * @param xml		xml源
	 * @param xPath		路劲集合
	 * @param eqMap		判断条件
	 * @param reParams	需要返回的字段
	 * @param queryFirst 是否只需查询一个结果
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, String>> getXmlParams(String xml, String xPath, Map<String, String> eqMap, String[] reParams, boolean queryFirst) throws DocumentException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Document doc = getDocument(xml);
		List<Element> elementList = doc.selectNodes(xPath);
		for (Element element : elementList) {
			if (equalsElementTexts(element, eqMap)) {
				Map<String, String> re = new HashMap<String, String>();
				if (reParams != null && reParams.length > 0) {
					for (String p : reParams) {
						re.put(p, getElementText(element, p));
					}
				} else {
					for (Iterator<Element> i = element.elementIterator(); i.hasNext(); ) {
						Element e = i.next();
						re.put(e.getName(), e.getTextTrim());
					}
				}
				list.add(re);
				// 只需查询一个结果
				if (queryFirst) {
					break;
				}
			}
		}
        return list;
	}
	
	public List<Map<String, String>> getXmlParams(String xml, String xPath, Map<String, String> eqMap) throws DocumentException {
        return getXmlParams(xml, xPath, eqMap, null);
	}
	
	/**
	 * 根据 判断条件来获取对应的返回值（只返回第一个匹配上的节点）
	 * 
	 * @param xml		xml源
	 * @param xPath		路劲集合
	 * @param eqMap		判断条件
	 * @return
	 * @throws DocumentException
	 */
	public Map<String, String> getXmlParam(String xml, String xPath, Map<String, String> eqMap) throws DocumentException {
        return getXmlParam(xml, xPath, eqMap, null);
	}
	
	/**
	 * 根据 判断条件来获取对应的返回值（只返回第一个匹配上的节点）
	 * 
	 * @param xml		xml源
	 * @param xPath		路劲集合
	 * @param eqMap		判断条件
	 * @param reParams	需要返回的字段
	 * @return
	 * @throws DocumentException
	 */
	public Map<String, String> getXmlParam(String xml, String xPath, Map<String, String> eqMap, String[] reParams) throws DocumentException {
		List<Map<String, String>> list = getXmlParams(xml, xPath, eqMap, reParams, true);
		if (list.isEmpty()) {
			return Collections.emptyMap();
		}
        return list.get(0);
	}
	
	private boolean equalsElementTexts(Element element, Map<String, String> eqMap) {
		boolean isSuccess = true;
		for (String key : eqMap.keySet()) {
			String v = getElementText(element, key);
			if (!v.equals(eqMap.get(key))) {
				isSuccess = false;
				break;
			}
		}
		return isSuccess;
	}
	
	private String getElementText(Element element, String key) {
		Element el = element.element(key);
		return el.getTextTrim();
	}
	
	private Document getDocument(String xml) throws DocumentException {
		StringReader sr = new StringReader(xml);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(sr);
        return doc;
	}
}
