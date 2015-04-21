package com.lvmama.passport.processor.impl.client.fangte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.ScenicProduct;
import com.lvmama.passport.processor.impl.client.fangte.model.Order;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
//import com.lvmama.passport.utils.HttpClient;
/**
 * 
 * @author lipengcheng , dingming
 *
 */
public class FangteUtil {
	private static Log log = LogFactory.getLog(FangteUtil.class);
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SO_TIMEOUT = 180000;
	private static final String CHARACTER_ENCODING = "UTF-8";
	/***
	 *   查询可售票类接口调用方法(华强方特)
	 * @return
	 */
	public static List<ScenicProduct> tgGetTicketType(){
		String targetUrl=WebServiceConstant.getProperties("fangte.url")+"tgGetTicketType.aspx?"+"uid="+WebServiceConstant.getProperties("fangte.uid")+"&pwd="+WebServiceConstant.getProperties("fangte.pwd");
		String res = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		List<ScenicProduct> scenicProduct = new ArrayList<ScenicProduct>();
		log.info("response: " + res);
		String[] split=res.split("[|]");
		String json=split[1];
		 try {
			 scenicProduct = buildScenicProduct(json);
		} catch (Exception e) {
			e.printStackTrace();
	   }
		return scenicProduct;
	}
	
	//JSON解析
	public static List<ScenicProduct> buildScenicProduct(String jsonResult) throws Exception {
		List<ScenicProduct> scenicProducts = new ArrayList<ScenicProduct>();
		JSONObject jsonObj=JSONObject.fromObject(jsonResult);
		if(jsonObj.has("T_blog")){
			JSONArray jsonArray = jsonObj.getJSONArray("T_blog"); 
			if(jsonArray.size()>0){
				for(int i=0;i<jsonArray.size();i++){
					JSONObject jsonObjcet=jsonArray.getJSONObject(i);
					String parkid=jsonObjcet.getString("parkid");
					String name=jsonObjcet.getString("name");
					String exetypeid=jsonObjcet.getString("exetypeid");
					String exetypename=jsonObjcet.getString("exetypename");
					String tickettypeid=jsonObjcet.getString("tickettypeid");
					String tickettypename=jsonObjcet.getString("tickettypename");
					String standardprice=jsonObjcet.getString("standardprice");
					if(standardprice != null){
						double price = Double.valueOf(standardprice);
						standardprice = String.valueOf(price);
					}
					ScenicProduct scenicProduct = new ScenicProduct();
					scenicProduct.setProductName(name);
					scenicProduct.setProdBranchName(tickettypename);
					scenicProduct.setProductIdSupplier(tickettypeid);
					scenicProduct.setProductTypeSupplier(parkid);
					scenicProduct.setPrice(standardprice);
					scenicProduct.setExtId(exetypeid);
					scenicProducts.add(scenicProduct);
					log.info("parkid="+parkid+" name="+name+" exetypeid="+exetypeid+" exetypename="+exetypename+" tickettypeid="+tickettypeid
							+" tickettypename="+tickettypename+" standardprice="+standardprice);
				}
			}
		}
		return scenicProducts;
	}
	
	/***
	 * 订票接口调用方法(华强方特)
	 * @param parkid  公园编号
	 * @param ticketcode  取票码,游客凭此代码到窗口取票 , 由合作方提供
	 * @param ticketlist  订票列表,如:全价票2人每张175元,儿童票3人每张123元   写成 "T0*2*175,T1*3*123"
	 * @param plandate    预订入园日期,如 2012-04-01
	 * @param exetypeid   带队类型id(使用网上自驾游类别    9)
	 * @return  成功返回订单号,错误返回错误信息
	 */
	public static String[] tgOrderTicket(Order order) {
		String targetUrl = WebServiceConstant.getProperties("fangte.url") + "tgOrderTicket.aspx?" 
								+ "parkid=" + order.getParkid()
								+ "&ticketcode=" + order.getTicketcode() 
								+ "&ticketlist=" + order.getTicketlist()
								+ "&plandate=" + order.getPlandate() 
								+ "&exetypeid=" + order.getExetypeid() 
								+ "&phone=" +order.getPhone()
								+ "&uid="+ WebServiceConstant.getProperties("fangte.uid") 
								+ "&pwd=" + WebServiceConstant.getProperties("fangte.pwd") 
								+ "&webkey=" + WebServiceConstant.getProperties("fangte.webkey");
		log.info("fangte tgOrderTicket request: " + targetUrl);
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		log.info("fangte tgOrderTicket response: " + result);
		return result.split("[|]");
	}
	
	/***
	 * 订票接口调用方法(华强方特)带餐
	 * @param parkid  公园编号
	 * @param ticketcode  取票码,游客凭此代码到窗口取票 , 由合作方提供
	 * @param ticketlist  订票列表,如:全价票2人每张175元,儿童票3人每张123元   写成 "T0*2*175,T1*3*123"
	 * @param plandate    预订入园日期,如 2012-04-01
	 * @param exetypeid   带队类型id(使用网上自驾游类别    9)
	 * @return  成功返回订单号,错误返回错误信息
	 */
	public static String[] tgOrderTicketAndCombo(Order order) {
		String targetUrl = WebServiceConstant.getProperties("fangte.url") + "tgOrderTicket.aspx?" 
								+ "parkid=" + order.getParkid()
								+ "&ticketcode=" + order.getTicketcode() 
								+ "&ticketlist=" + order.getTicketAndCombo()
								+ "&plandate=" + order.getPlandate() 
								+ "&exetypeid=" + order.getExetypeid()
								+ "&phone=" +order.getPhone()
								+ "&uid="+ WebServiceConstant.getProperties("fangte.uid") 
								+ "&pwd=" + WebServiceConstant.getProperties("fangte.pwd") 
								+ "&webkey=" + WebServiceConstant.getProperties("fangte.webkey");
		log.info("fangte tgOrderTicket request: " + targetUrl);
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		log.info("fangte tgOrderTicket response: " + result);
		return result.split("[|]");
	}
	
	public static Map<String, String> getResultMap(String result) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String[] splitResult = result.split("[|]");
		resultMap.put("status", splitResult[0]);
		resultMap.put("code", splitResult[1]);
		return resultMap;
	}
	
	/***
	 * 查询订单接口调用方法(华强方特)
	 * @param orderid 订票成功对方返回的 订单号
	 * @return 成功返回JSON集合格式的订票信息,失败返回错误信息
	    *  <parkid>公园编号</parkid>
        *  <name>公园名称</name>
		*	<planid>取票码</planid>
		*	<inputtime>订票时间</inputtime>
		*	<plandate>入园日期</plandate>
		*	<tickettypeid>票类编号</tickettypeid>
		*	<tickettypename>票类名称</tickettypename>
		*	<personsamount>订票人数</personsamount>
		*	<planstate>状态编号</planstate>
		*	<statename>状态名称</statename>
		*	<salesqty>出票人数</ salesqty>
	 */
	public static String[] tgGetOrder(String orderId) {
		String targetUrl = WebServiceConstant.getProperties("fangte.url") + "tgGetOrder.aspx?" + "keyid=" + orderId + "&uid=" + WebServiceConstant.getProperties("fangte.uid") + "&pwd=" + WebServiceConstant.getProperties("fangte.pwd") + "&webkey=" + WebServiceConstant.getProperties("fangte.webkey");
		log.info("fangte tgOrderTicket request: " + targetUrl);
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		log.info("fangte tgOrderTicket response: " + result);
		return result.split("[|]");
	}
	
	/***
	 * 退票接口调用方法,未取票可退订(华强方特)
	 * @param orderid  订票成功对方返回的 订单号
	 * @return 退票成功返回success,失败返回错误信息
	 */
	public static String[] tgCancelOrder(String orderId) {
		String targetUrl = WebServiceConstant.getProperties("fangte.url") + "tgCancelOrder.aspx?" + "keyid=" + orderId + "&uid=" + WebServiceConstant.getProperties("fangte.uid") + "&pwd=" + WebServiceConstant.getProperties("fangte.pwd") + "&webkey=" + WebServiceConstant.getProperties("fangte.webkey");
		log.info("fangte tgCancelOrder request: " + targetUrl);
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		log.info("fangte tgCancelOrder response: " + result);
		return result.split("[|]");
	}
	
 /********************订票接口错误码解析 Start******************************/
  public static String convertErrorCode_OrderTicket(String code_name){
	  Map<String,String> codeMap=new HashMap<String, String>();
	  codeMap.put("plandate_error", "预订日期有误");
	  codeMap.put("ticketcode_error", "取票码重复，请重新生成");
	  codeMap.put("saveticket_fail", "保存数据失败，请重试");
	  codeMap.put("too_large_counts", "单票超过可预订最大人数");
	  return codeMap.get(code_name);
  }
  /********************订票接口错误码解析 End******************************/
  
  /********************查询接口错误码解析 Start******************************/
  public static String convertErrorCode_GetOrder(String code_name){
	  Map<String,String> codeMap=new HashMap<String, String>();
	  codeMap.put("parts_error", "当地服务没响应，不提供查询,分部公园网络连接中断，请等待服务方解决");
	  codeMap.put("keyid_notexists", "订单号不存在,请检查订单号正确性");
	  codeMap.put("error", "查询异常");
	
	  return codeMap.get(code_name);
  }
  /********************查询接口错误码解析 End******************************/
  
  /********************退票接口错误码解析Start******************************/
  public static String convertErrorCode_CancelOrder(String code_name){
	  Map<String,String> codeMap=new HashMap<String, String>();
	  
	  codeMap.put("keyid_notexists", "查找不到此订单");
	  codeMap.put("saveticket_fail", "保存数据出现错误");
	  codeMap.put("error", "退票异常");
	  codeMap.put("try_again_later", "订票与退票时间间隔过短，请稍后再试");
	  codeMap.put("unconfirmed", "未确认订单");
	  codeMap.put("already_cancel", "此订单已退票");
	  codeMap.put("unallow_refund", "此订单不允许退票");
	  codeMap.put("unpaid_cash", "未支付订单不允许退票");
	  codeMap.put("used", "已出票不能退票");
	 
	  return codeMap.get(code_name);
  }
  
  public static JSONObject getOrderJson(String jsonResult) {
		JSONObject jsonObj = JSONObject.fromObject(jsonResult);
		if (jsonObj.has("T_blog")) {
			JSONArray jsonArray = jsonObj.getJSONArray("T_blog");
			if (jsonArray.size() == 1 ) {
				return jsonArray.getJSONObject(0);
			}else if(jsonArray.size() > 1 ){
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject temp = jsonArray.getJSONObject(i);
					if ("2".equals(temp.getString("planstate"))){ // 是否已入园 [对方状态说明1=未出票2=已出票3=已退票]
						return temp;
					}
				}
				
			}
		}
		return null;
	}
  
  /********************退票接口错误码解析 End******************************/
	public static void main(String[] args) throws Exception {
		tgGetTicketType();
		
//		String res=ft.tgOrderTicket(17, "1236f46gfaerg", "T0*2*175,T1*3*123", "2012-08-03", 9);
//		System.out.println(res);
		
//		String res=ft.tgGetOrder("172012072400003");
//		System.out.println(res);
		
//		String res=ft.tgCancelOrder("172012072400003");
//		System.out.println(res);
//		tgGetTicketType();
//		Order order = new Order();
//		order.setParkid("11");
//		order.setPlandate(DateFormatUtils.format(DateUtil.parse("2012-7-28", "yyyy-MM-dd"), "yyyy-MM-dd"));
//		order.setTicketcode("1312312414242");
//		order.setTicketcode(passCode.getSerialNo());
//		order.setNum(num);
//		order.setExetypeid(exeTypeId);
		/*String [] result = tgGetOrder("112012081300001");
		String param2 = result[1];
		JSONObject jsonObj=JSONObject.fromObject(param2);
		JSONArray jsonArray = jsonObj.getJSONArray("T_blog");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		jsonObject.getString("planstate");
		jsonObject.getString("statename");
		System.out.println(result[0]);
		System.out.println(result[1]);*/
	}

}
