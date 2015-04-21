package com.lvmama.pet.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.PaymentConstant;
//import org.apache.commons.httpclient.util.DateUtil;

public class ByPayUtil {
	/**
	 * 构造账务明细查询接口
	 * 
	 * @param sParaTemp
	 *            请求参数集合
	 * @return 表单提交HTML信息
	 * @throws Exception
	 * @author fengyu
	 */
	public static String queryReconData(Date date)
			throws Exception {
		// 增加基本配置
		Map<String, String> sParaTemp = new HashMap<String, String>();
		String url = PaymentConstant.getInstance().getProperty("BYPAY_TEL_RECON_QUERY_URL");
		sParaTemp.put("merchantId", PaymentConstant.getInstance().getProperty("BYPAY_TEL_MERID"));

		String dateStr = DateFormatUtils.format(date, "YYYY-MM-DD");

		sParaTemp.put("date", dateStr);

		return HttpsUtil.requestPostForm(url, sParaTemp);
	}
}