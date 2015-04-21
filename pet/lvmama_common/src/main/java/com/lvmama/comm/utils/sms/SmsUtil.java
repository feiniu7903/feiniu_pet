package com.lvmama.comm.utils.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.SmsStatusReport;

/**
 * 短信工具类
 * @author ready
 *
 */
public class SmsUtil {
	/**
	 * 将emay的状态报告xml转换为状态报告列表数据
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List<SmsStatusReport> parseEmayReportXml(String xml) throws Exception {
		List<SmsStatusReport> reports = new ArrayList<SmsStatusReport>();
		Document doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement();
		Iterator reportIter = rootElt.elementIterator("report"); // 获取根节点下的子节点head
		// 遍历head节点
		while (reportIter.hasNext()) {
			Element reportEle = (Element) reportIter.next();
			Long serialId = Long.valueOf(reportEle.elementTextTrim("seqId"));
			Integer result = "0".equals(reportEle
					.elementTextTrim("reportStatus")) ? SmsContentLog.REPORT_FOR_SUCCESS
					: SmsContentLog.REPORT_FOR_ERROR;
			Date date = DateUtil.getDateByStr(
					reportEle.elementTextTrim("receiveDate"),
					DateUtil.PATTERN_yyyyMMddHHmmss);
			reports.add(new SmsStatusReport(serialId, result, date, ""));
		}

		return reports;
	}
}
