package com.lvmama.back.job;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/***
 * 调用淘宝批量查询接口,将可搬单的订单插入ord_tmall_map表中
 * @author dingming
 *
 */
public class CreateTmallOrderJob {
	
	private static final Log log = LogFactory.getLog(CreateTmallOrderJob.class);
	private  OrdTmallMapService ordTmallMapService; 
	private ProdProductService prodProductService;
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setOrdTmallMapService(OrdTmallMapService ordTmallMapService) {
		this.ordTmallMapService = ordTmallMapService;
	}
 
	public  void creatTmallOrd(String jsonRes){
		
		JSONObject jsonObj=JSONObject.fromObject(jsonRes);
		if(jsonObj.has("trades_sold_get_response")){
			JSONObject trades_sold=jsonObj.getJSONObject("trades_sold_get_response");
			if(trades_sold.has("trades")){
				JSONObject trades=trades_sold.getJSONObject("trades");
				if(trades.has("trade")){
					 JSONArray trade = trades.getJSONArray("trade"); 
					 for(int i=0;i<trade.size();i++){
						 JSONObject jsonObject=trade.getJSONObject(i);
						 try{
							 if(jsonObject.has("seller_flag")){
								 String flag=jsonObject.getString("seller_flag");
								 if(flag.equals("3")){
									//防止相同淘宝订单号重复拉取数据
									 if(ordTmallMapService.selectTmallNo(jsonObject.getString("tid"))==false)
										 continue;
									 if(jsonObject.has("orders")){
										 JSONObject ords=jsonObject.getJSONObject("orders");
										 if(ords.has("order")){
											 JSONArray orders=ords.getJSONArray("order");
											 OrdTmallMap order=null;
											 String pro_id=null;
											 String categ_id=null;
											 ProdProduct prodProduct=null;
											 for(int j=0;j<orders.size();j++){
												 try {
													 JSONObject orderList=orders.getJSONObject(j);
													 order=new OrdTmallMap();
												 
													 if(jsonObject.has("tid")){
														 order.setTmallOrderNo(jsonObject.getString("tid"));
													 }
													 if(jsonObject.has("buyer_nick")){
														 order.setBuyerNick(jsonObject.getString("buyer_nick"));
													 }
													 if(jsonObject.has("receiver_mobile")){
														 order.setBuyerMobile(jsonObject.getString("receiver_mobile"));
													 }
													 if(orderList.has("price")){
														 order.setProductTmallPrice(new BigDecimal(orderList.getString("price")));
													 }
													 if(orderList.has("sku_properties_name")){
														 order.setProductName(orderList.getString("sku_properties_name"));
													 }
													 
													 if(orderList.has("outer_sku_id")){
														 String pro_categ=orderList.getString("outer_sku_id");
														 if(pro_categ!=null&&pro_categ.length()>0){
															 if(pro_categ.indexOf(",")!=-1){
																 String arrs[]=pro_categ.split(",");
																 pro_id=arrs[0].trim();
																 categ_id=arrs[1].trim();
															 }else{
																 pro_id=pro_categ.trim();
																 categ_id=pro_categ.trim();
															 }															 
														 }
													 }else if(orderList.has("outer_iid")){
															 String pro_categ=orderList.getString("outer_iid");
															 if(pro_categ!=null&&pro_categ.length()>0){
																 if(pro_categ.indexOf(",")!=-1){
																	 String arrs[]=pro_categ.split(",");
																	 pro_id=arrs[0].trim();
																	 categ_id=arrs[1].trim();
																 }else{
																	 pro_id=pro_categ.trim();
																	 categ_id=pro_categ.trim();
																 }
															 }
													 }else{
														 if(orderList.has("ticket_outer_id")){
															 String pro_categ=orderList.getString("ticket_outer_id");
															 if(pro_categ!=null&&pro_categ.length()>0){
																 if(pro_categ.indexOf(",")!=-1){
																	 String arrs[]=pro_categ.split(",");
																	 pro_id=arrs[0].trim();
																	 categ_id=arrs[1].trim();
																 }else{
																	 pro_id=pro_categ.trim();
																	 categ_id=pro_categ.trim();
																 }
															 }
													 }
													 }
													 
													 if(pro_id==null||categ_id==null){
														 order.setStatus("failure"); 
														 order.setFailedReason("缺少产品id或类别id");
													 }else{
														 prodProduct = prodProductService.getProdProduct(Long.valueOf(pro_id));
														 if(prodProduct!=null){
															 order.setProductType(prodProduct.getProductType());
														 }
														 order.setProductId(Long.valueOf(pro_id));
														 order.setCategoryId(Long.valueOf(categ_id));
														 order.setStatus("create");
													 }
													 
													 order.setSystemOrder("true");
													 order.setCreateTime(new Date());
													 ordTmallMapService.insert(order);
												 }catch (Exception e) {
													 if(!StringUtil.isEmptyString(order.getTmallOrderNo())){
														 order.setStatus("failure");
														 order.setFailedReason("产品或类别id不标准");
														 order.setSystemOrder("true");
														 order.setCreateTime(new Date());
														 ordTmallMapService.insert(order);
														 log.error(this.getClass(),e);
													 }else{
														 throw e;
													 }
													 
												}
											 }
										 }
									 }
								 }
							 }
						 }catch(Exception e){
							 log.error("json object is: " + jsonObject);
							 log.error(this.getClass(),e);
						 }
					 }
				}
			}
		}
	}
	
	public void run() {
		if (Constant.getInstance().isJobRunnable() && Constant.getInstance().isSyncTmallOrder()) {
			log.info("CreateTmallOrderJob begin run.");
			Date dsDate=DateUtil.dsDay_Date(new Date(), -3);
			boolean flag=true;
			//int size=0;
			String jsonRes=null;
			JSONObject jsonObj=null;
			Long pageNum=0L;
			while(flag && pageNum.longValue()<100) {
				pageNum++;
				jsonRes=TOPInterface.getTradesSold(dsDate, new Date(),pageNum, "WAIT_BUYER_CONFIRM_GOODS");
				if(jsonRes!=null){
					try{
						creatTmallOrd(jsonRes);
						jsonObj=JSONObject.fromObject(jsonRes);
						if(jsonObj.has("trades_sold_get_response")){
							if(!jsonObj.getJSONObject("trades_sold_get_response").has("trades")){
								flag=false;
							}
						}else{
							flag=false;
						}	
					}catch(Exception e){
						log.error("create tmall order error, tmall reseponse is: " + jsonRes);
						log.error(this.getClass(), e);
					}
				}else{
					flag=false;
				}
			}
		}
		
	}

}
