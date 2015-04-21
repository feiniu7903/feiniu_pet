package com.lvmama.passport.processor.impl.client.beizhu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.passport.utils.Md5;

public class BeizhuHTTPUtil {
	private static final Log log = LogFactory.getLog(BeizhuHTTPUtil.class);

	/**
	 * 推送订单
	 * @param beizhuOrder
	 * @return
	 * @throws Exception
	 */
	public static BeizhuResponse sendOrder(BeizhuOrder beizhuOrder) throws Exception {
		Map<String, String> requestParas = beizhuOrder.toRequestParams();

		beizhuOrder.setSign(sign(requestParas));

		log.info("Beizhu Send Request Message: " + beizhuOrder.toString());

		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse(BeizhuConstant.BEIZHU_URLS.BEIZHU_URLS_ORDER_SEND.getUrl(), requestParas);
		String jsonResult = response.getResponseString();

		log.info("Beizhu Apply Response Message:" + jsonResult);

		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));
			BeizhuResponse BeizhuOrderResult = (BeizhuResponse) JSONObject.toBean(jsonObject, BeizhuResponse.class);
			return BeizhuOrderResult;
		}
		return null;
	}

	/**
	 * 查询贝竹对接方的订单状态
	 * @param passCode
	 * @return
	 * @throws Exception
	 */
	public static BeizhuResponse getOrderStatus(PassCode passCode) throws Exception {

		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("order_id", passCode.getAddCode()); // 贝竹方的 订单ID
		requestParas.put("uname", BeizhuConstant.getInstance().username);
		requestParas.put("pwd", BeizhuConstant.getInstance().password);

		sign(requestParas);

		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse(BeizhuConstant.BEIZHU_URLS.BEIZHU_URLS_ORDER_SEARCH.getUrl(), requestParas);
		String jsonResult = response.getResponseString();
		log.info("Beizhu Search Order Status Message:" + jsonResult);

		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));

			BeizhuResponse BeizhuResponse = (BeizhuResponse) JSONObject.toBean(jsonObject, BeizhuResponse.class);
			return BeizhuResponse;
		}
		return null;
	}

	/**
	 * 生成密签信息
	 * @param paramMap
	 * @return
	 */
	private static String sign(Map<String, String> paramMap) {
		Iterator<String> keys = paramMap.keySet().iterator();
		String[] params = new String[paramMap.size()];

		for (int i = 0; keys.hasNext(); i++) {
			String key = (String) keys.next();
			String value = paramMap.get(key);
			params[i] = key + value;
		}

		Arrays.sort(params);
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append(BeizhuConstant.key);
		for (String param : params) {
			sbuffer.append(param);
		}
		sbuffer.append(BeizhuConstant.key);

		String sign = Md5.encode(sbuffer.toString()).toUpperCase();
		paramMap.put("sign", sign);
		return sign;
	}

	/**
	 * 取消订单
	 * @param beizhuOrder
	 * @return
	 * @throws Exception
	 */
	public static BeizhuResponse cancelOrder(BeizhuOrder beizhuOrder) throws Exception {
		Map<String, String> requestParas = beizhuOrder.toRequestParams();

		beizhuOrder.setSign(sign(requestParas));

		log.info("Beizhu Destroy Request Message: " + beizhuOrder.toString());

		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse(BeizhuConstant.BEIZHU_URLS.BEIZHU_URLS_ORDER_CANCEL.getUrl(), requestParas);
		String jsonResult = response.getResponseString();
		
		log.info("Beizhu Destory Response Message:" + jsonResult);
		
		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));
			BeizhuResponse BeizhuOrderResult = (BeizhuResponse) JSONObject.toBean(jsonObject, BeizhuResponse.class);
			return BeizhuOrderResult;
		}
		return null;
	}

}
