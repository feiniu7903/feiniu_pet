package com.lvmama.passport.beijinglelecool;
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
import com.lvmama.passport.beijinglelecool.model.TimePriceBean;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
public class BeijingLeleCoolUtil{
	static BeijingLeleCoolUtil beijingLeleCoolUtil ;
	public static BeijingLeleCoolUtil init(){
		if(beijingLeleCoolUtil==null){
			beijingLeleCoolUtil = new BeijingLeleCoolUtil();
		}
		return beijingLeleCoolUtil;
	}
	private static final Log log = LogFactory.getLog(BeijingLeleCoolUtil.class);
	public List<TimePriceBean> getTimePriceInfo(String productNo,Date date)throws Exception{
		String url=WebServiceConstant.getProperties("beijinglelecool.url");
		String custId=WebServiceConstant.getProperties("beijinglelecool.custId");
		String apikey=WebServiceConstant.getProperties("beijinglelecool.apikey");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String travelDate=sdf.format(date);
		Map<String, String> reqParas = new HashMap<String, String>();
		reqParas.put("custId", custId);
		reqParas.put("apikey", apikey);
		reqParas.put("productNo",productNo );
		reqParas.put("travelDate",travelDate);
		String result=HttpsUtil.requestPostForm(url+"/api/price.jsp", reqParas);
		log.info("beijinglelecool price requestXml:"+result);
		String status = TemplateUtils.getElementValue(result,"//result/status");
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
				pricelist.add(price);
			}
		}
		return pricelist;
	}
}
