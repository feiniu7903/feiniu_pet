package com.lvmama.passport.shanghu;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.passport.shanghu.model.TimePriceBean;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
public class ShanghuUtil{
	static ShanghuUtil shanghuUtil ;
	public static ShanghuUtil init(){
		if(shanghuUtil==null){
			shanghuUtil = new ShanghuUtil();
		}
		return shanghuUtil;
	}
	private static final Log log = LogFactory.getLog(ShanghuUtil.class);
	public List<TimePriceBean> getTimePriceInfo(String productNo,Date date)throws Exception{
		String url=WebServiceConstant.getProperties("shanghutiangui.url");
		String custId=WebServiceConstant.getProperties("shanghutiangui.custId");
		String apikey=WebServiceConstant.getProperties("shanghutiangui.apikey");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String travelDate=sdf.format(date);
		Map<String, String> reqParas = new HashMap<String, String>();
		reqParas.put("custId", custId);
		reqParas.put("apikey", apikey);
		reqParas.put("productNo",productNo );
		reqParas.put("travelDate",travelDate);
		String result=HttpsUtil.requestPostForm(url+"/api/price.jsp", reqParas);
		log.info("shanghu price requestXml:"+result);
		String status = TemplateUtils.getElementValue(result,"//result/status");
		String productResult=getProductDetail(productNo);
		String startDay=TemplateUtils.getElementValue(productResult,"//result/product/startDay");
		String cancelDay=TemplateUtils.getElementValue(productResult,"//result/product/cancelDay");
		List<TimePriceBean> pricelist=new ArrayList<TimePriceBean>();
		if(StringUtils.equals(status,"1")){
			List<String> dates=TemplateUtils.getElementValues(result,"//result/prices/price/date");
			List<String> marketPrices=TemplateUtils.getElementValues(result,"//result/prices/price/marketPrice");
			List<String> salePrices=TemplateUtils.getElementValues(result,"//result/prices/price/salePrice");
			List<String> remainNums=TemplateUtils.getElementValues(result,"//result/prices/price/remainNum");
			List<String> settlementPrices=TemplateUtils.getElementValues(result,"//result/prices/price/SettlementPrice");
			for(int i = 0; i < dates.size(); i++){
				TimePriceBean price=new TimePriceBean();
				price.setDate(dates.get(i));
				price.setMarketPrice(String.valueOf((int)Float.parseFloat(marketPrices.get(i))*100));
				price.setSalePrice(salePrices.get(i));
				price.setRemainNum(remainNums.get(i));
				price.setSettlementPrice(settlementPrices.get(i));
				if(StringUtils.equals(startDay,"")){
					startDay="0";
				}
				price.setStartDay(Long.valueOf(startDay)*24*60);
				
				if(StringUtils.equals(cancelDay,"")){
					cancelDay="0";
				}
				price.setCancelDay(Long.valueOf(cancelDay)*24*60);
				pricelist.add(price);
			}
		}
		return pricelist;
	}
	
	
	public String getProductDetail(String productNo)throws Exception{
		String url=WebServiceConstant.getProperties("shanghutiangui.url");
		String custId=WebServiceConstant.getProperties("shanghutiangui.custId");
		String apikey=WebServiceConstant.getProperties("shanghutiangui.apikey");
		Map<String, String> reqParas = new HashMap<String, String>();
		reqParas.put("custId", custId);
		reqParas.put("apikey", apikey);
		reqParas.put("productNo",productNo);
		String result=HttpsUtil.requestPostForm(url+"/api/detail.jsp", reqParas);
		return result;
	}
}
