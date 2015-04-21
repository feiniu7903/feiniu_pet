package com.lvmama.passport.processor.impl.client.dalilyw;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.passport.processor.impl.client.dalilyw.model.HttpResponse;
import com.lvmama.passport.processor.impl.client.dalilyw.model.OrderBean;
import com.lvmama.passport.processor.impl.client.dalilyw.model.OrderResponse;
import com.lvmama.passport.utils.WebServiceConstant;

public class Test {
	public static void main(String[] args) {
		try {
		//下单
			OrderBean bean=new OrderBean();
			String productTypeSupplier="1";//1 联票 0或不填为景点票  //1012310024 联票   S00049 景点票
			String productId="1012310024";
			String url=WebServiceConstant.getProperties("dalilyw.url");
			bean.setArriveDate("2014-04-25");
			bean.setBuyNum("1");
			bean.setCardno("");
			bean.setCustMobile("15026847838");
			bean.setProductId(productId);//测试的商品Id
			if (StringUtils.equals(productTypeSupplier,"1")){
				bean.setMunit(false);
				url=url+"/member/order/combcreate";
			}else{
				url=url+"/member/order/sceniccreate";
			}
			bean.setCustName("小汤测试单");
			Map<String, Object> params = buildParams(bean);
			HttpResponse saveResult = DalilywUtil.saveOrderRequest(params,url);
			System.out.println("=====下单测试======"+saveResult.getResponseBody());
		
			if(saveResult.getCode()==HttpStatus.SC_OK){
				OrderResponse res=DalilywUtil.parseOrderResponse(saveResult.getResponseBody());
				String order_no=res.getOrder_no();
				System.out.println("order_code:"+res.getOrder_code());
				System.out.println("order_no:"+res.getOrder_no());
				HttpResponse zf=DalilywUtil.balancepay("com.tour.openapi.controller.OrderController.doPayAmtByBalance", order_no);
				System.out.println(zf.getResponseBody());
			}else{
				System.out.println(saveResult.getResponseBody());
			}
			
			//退款接口
			String order_no="14041767900";
			HttpResponse result=DalilywUtil.refundbalance("com.tour.openapi.controller.OrderController.doRefundTheBalance", order_no);
			System.out.println(result.getResponseBody());
			System.out.println(result.getCode());
			
			//取消订单
			String orderNo="14041667341";
			HttpResponse cancelResult = DalilywUtil.cancelRequest("com.tour.openapi.controller.OrderController.doOrderCancel", orderNo);
			System.out.println("======测试取消订单接口：========"+cancelResult.getResponseBody());
			//查询订单
			HttpResponse reponse =DalilywUtil.orderRequest("com.tour.openapi.controller.OrderController.doOrderList",orderNo);
			System.out.println("=======测试查询订单详情接口:========="+reponse.getResponseBody());
			List<OrderResponse> r=DalilywUtil.parseOrderListResponse(reponse.getResponseBody());
			if(isUsed(r))
			System.out.println("已消费");
			else
			System.out.println("未消费");
//			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//联票时有多个订单明细时，其中有个消费，就算已消费
	public static boolean isUsed(List<OrderResponse> response){
		for(int i=0;i<response.size();i++){
			if(StringUtils.equals(response.get(i).getStatusName(),"已消费")){
				return true;
			}
		}
		return false;
	}
	
	private static  Map<String, Object> buildParams(OrderBean bean)throws Exception{
		String appKey=WebServiceConstant.getProperties("dalilyw.appkey");
		String appSecret=WebServiceConstant.getProperties("dalilyw.appsecret");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String unixTimestamp =formatter.format(System.currentTimeMillis());
		Map<String, Object> allData = new HashMap<String, Object>();
		Map<String, String> query = new HashMap<String, String>();
		if(bean.isMunit()){
			query.put("munitid",bean.getProductId());
		}else{
			query.put("combtktid",bean.getProductId());
		}
		query.put("name",bean.getCustName());
		query.put("cardno",bean.getCardno());
		query.put("mobileno",bean.getCustMobile());
		query.put("amount","1");
		query.put("orderdate", bean.getArriveDate());
		String checksign=DalilywUtil.makeSign(query);
		Map<String, String> header = new HashMap<String, String>();
		header.put("appKey",appKey);
		header.put("appSecret",appSecret);
		if(bean.isMunit()){
			header.put("method","com.tour.openapi.controller.OrderController.doCreateScenicOrder");
		}else{
			header.put("method","com.tour.openapi.controller.OrderController.doCreateCombOrder");
		}
		header.put("timeStamp",unixTimestamp);
		header.put("version","1.0");
		header.put("checkSign",checksign);
		allData.put("header", header);
		allData.put("query", query);
		return allData;
	}

}
