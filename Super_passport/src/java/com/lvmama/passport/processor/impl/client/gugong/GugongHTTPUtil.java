package com.lvmama.passport.processor.impl.client.gugong;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

public class GugongHTTPUtil {
	private static final Log log = LogFactory.getLog(GugongHTTPUtil.class);

	public static GugongTimePrice getTimePrices(String intodate)throws Exception{
		// 查询时间价格表的永乐方地址
		String serviceUrl = GugongConstant.GUGONG_URLS.GUGONG_URLS_TIME_PRICE.getUrl();
		// 永乐方提供的联盟ID
		String unionid = GugongConstant.unionid;
		String password = GugongConstant.password;

		JSONObject jsonParam = new JSONObject();
		jsonParam.put("unionid", unionid);
		jsonParam.put("intodate", intodate);
		jsonParam.put("sign", Md5.encode(unionid.concat(intodate).concat(password)));
		String data = jsonParam.toString();

		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("productinfo", data);

		HttpResponseWrapper response = null;
		String jsonResult = "";
		response = HttpsUtil.requestPostFormResponse(serviceUrl, requestParas);
		jsonResult = response.getResponseString();
		log.info("永乐方返回的时间价格结果：".concat(jsonResult));
		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));

			GugongTimePrice gugongTimePrice = (GugongTimePrice) JSONObject.toBean(jsonObject, GugongTimePrice.class);
			return gugongTimePrice;
		}

		return null;
	}

	public static GugongOrderResponse sendOrder(String orderInfo) throws Exception {
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("orderinfo", orderInfo);

		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse(GugongConstant.GUGONG_URLS.GUGONG_URLS_ORDER.getUrl(), requestParas);
		String jsonResult = response.getResponseString();
		log.info("Gugong Apply Response message:" + jsonResult);
		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));
			GugongOrderResponse gugongOrderResult = (GugongOrderResponse) JSONObject.toBean(jsonObject, GugongOrderResponse.class);
			return gugongOrderResult;
		}
		return null;
	}
	//退款接口
	public static GugongOrderResponse getRefundResponse(PassCode passCode) throws Exception{
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("orderid", passCode.getSerialNo());
		jsonParam.put("unionid", GugongConstant.unionid);
		jsonParam.put("sign", Md5.encode(passCode.getSerialNo().toString().concat(GugongConstant.unionid).concat(GugongConstant.password)));
		String data = jsonParam.toString();
		log.info("Gugong Refund requestParam:"+data);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("orderinfo", data);


		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse(GugongConstant.GUGONG_URLS.GUGONG_URLS_ORDER_REFUND.getUrl(), requestParas);
		String jsonResult = response.getResponseString();
		log.info("Gugong Refund Response message:" + jsonResult);
		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));
			GugongOrderResponse gugongOrderResult = (GugongOrderResponse) JSONObject.toBean(jsonObject, GugongOrderResponse.class);
			return gugongOrderResult;
		}
		return null;
	}

	public static GugongOrderResponse getOrderStatus(PassCode passCode) throws Exception{
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("orderid", passCode.getSerialNo());
		jsonParam.put("unionid", GugongConstant.unionid);
		jsonParam.put("sign", Md5.encode(passCode.getSerialNo().toString().concat(GugongConstant.unionid).concat(GugongConstant.password)));
		String data = jsonParam.toString();

		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("orderinfo", data);

		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse(GugongConstant.GUGONG_URLS.GUGONG_URLS_ORDER_SEARCH.getUrl(), requestParas);
		String jsonResult = response.getResponseString();
		log.info("Gugong Search Order Status message:" + jsonResult);

		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));

			GugongOrderResponse gugongResponse = (GugongOrderResponse) JSONObject.toBean(jsonObject, GugongOrderResponse.class);
			return gugongResponse;
		}
		return null;
	}
	
	// 同一入院日，同一身份证号，剩余入园人数查询
	public static GugongCheckRemainNumResponse getCheckRemainNum(String intodate, String idcard) throws Exception {
		// 永乐方提供的联盟ID
		String unionid = GugongConstant.unionid;
		String password = GugongConstant.password;
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("unionid", unionid);
		jsonParam.put("intodate", intodate);
		jsonParam.put("idnumber", idcard);
		jsonParam.put("sign", Md5.encode(unionid.concat(intodate).concat(idcard).concat(password)));
		String data = jsonParam.toString();
		Map<String, String> requestParas = new LinkedHashMap<String, String>();
		requestParas.put("intodateAndIdnumber", data);
		String url = GugongConstant.GUGONG_URLS.GUGONG_URLS.getUrl() + "selectOneIntodateOneIdnumberTicketRemainNum";
		log.info("Gugong CheckRemainNum url:" + url);
		log.info("Gugong CheckRemainNum parames:" + requestParas);
		HttpResponseWrapper response = HttpsUtil.requestPostFormResponse(url, requestParas);
		String jsonResult = response.getResponseString();
		log.info("GugongCheckRemainNumResponse:" + jsonResult);

		if (StringUtils.isNotEmpty(jsonResult)) {
			JSON json = (JSON) JSONSerializer.toJSON(jsonResult);
			JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJava(json));

			GugongCheckRemainNumResponse gugongResponse = (GugongCheckRemainNumResponse) JSONObject.toBean(jsonObject, GugongCheckRemainNumResponse.class);
			return gugongResponse;
		}
		return null;
	}
}
