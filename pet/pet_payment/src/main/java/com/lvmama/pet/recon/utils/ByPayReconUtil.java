package com.lvmama.pet.recon.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.PaymentConstant;

public class ByPayReconUtil {
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

		String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");

		sParaTemp.put("date", dateStr);

		return HttpsUtil.requestPostForm(url, sParaTemp);
	}

	public static void main(String[] args) {
		// 增加基本配置
		Map<String, String> sParaTemp = new HashMap<String, String>();
		String url = "http://mbp.bypay.cn/web.trans!getTransToTxt.ac";
		sParaTemp.put("merchantId", "301310048990044");

		String dateStr = "2012-12-09";

		sParaTemp.put("date", dateStr);

		String data = HttpsUtil.requestPostForm(url, sParaTemp);
//		System.out.println(data);

		String DATA_HEAD = "商户号\\|业务类型\\|支付通道\\|商户订单号\\|交易金额\\(元\\)\\|商户交易时间\\|交易状态\\|交易类型\\|卡号\\|支付流水号\\|交易时间\\|交易手续费\\(元\\)\\|清算日期\\|订单标识\\n";
		data = data.replaceAll(DATA_HEAD, "");
		
		System.out.println(data);
//		ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext-pet-payment-beans.xml");
	}
	
}