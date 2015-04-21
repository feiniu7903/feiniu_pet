package com.lvmama.service.handle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.passport.processor.impl.client.renwoyou.RenwoyouClient;
import com.lvmama.passport.processor.impl.client.renwoyou.model.OrderResponse;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.service.CheckStockHandle;

public class RenwoyouCheckStockHandle implements CheckStockHandle {
	private static final Log log = LogFactory.getLog(RenwoyouCheckStockHandle.class);
	private MetaProductBranchService metaProductBranchService;
	@Override
	public List<Item> check(BuyInfo buyinfo,List<Item> list) {
		for (Item item : list) {
			MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
			try {
				log.info("RenwoyouTicketCheckHandle visitTime:"+item.getVisitTime());
				String productIdSupplier = metaBranch.getProductIdSupplier();
				String productTypeSupplier=metaBranch.getProductTypeSupplier();
				log.info("RenwoyouTicketCheckHandle productIdSupplier:"+productIdSupplier);
				log.info("quantity:"+item.getQuantity());
				String visitTime=DateUtil.formatDate(item.getVisitTime(), "yyyy-MM-dd");
				String[] values = productTypeSupplier.split(",");
				if(values.length!=2){
					item.setStock(SupplierProductInfo.STOCK.LACK);
					item.setLackReason("下单异常，请稍后再试！");
					log.info("产品绑定错误！");
				}
				String payType=values[1];
				// 剩余订购数量检测
				Map<String,String> params=buildParams("GET_SCENIC_TICKET_INVT", visitTime, productIdSupplier, payType);
				String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("renwoyou.url"), params);
				log.info("GET_SCENIC_TICKET_INVT result:"+result);
				OrderResponse response=RenwoyouClient.parseTicketInvtrResponse(result);
				if(response==null){
					item.setStock(SupplierProductInfo.STOCK.LACK);
					log.info("库存不足，暂时不能预定。");
					item.setLackReason("库存不足，暂时不能预定。");
				}else{
					if(item.getQuantity() > response.getQty()){
					item.setStock(SupplierProductInfo.STOCK.LACK);
					log.info("库存不足，暂时不能预定。");
					item.setLackReason("库存不足，暂时不能预定。");
					}
				}
			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("下单异常，请稍后再试！");
				log.error("RenwoyouTicketCheckHandle Exception:" + e.getMessage());
			}
		}
		return list;
	}
	
	private static Map<String, String> buildParams(String actionName,String visiteTime,String ticketId,String payType)throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		String uname=WebServiceConstant.getProperties("renwoyou.monthreport.uname");
		String pass=WebServiceConstant.getProperties("renwoyou.monthreport.pass");
		String key=WebServiceConstant.getProperties("renwoyou.monthreport.key");
		if(StringUtils.equals(payType,"0")){ //福建 日结
			uname=WebServiceConstant.getProperties("renwoyou.dayreport.uname");
			pass=WebServiceConstant.getProperties("renwoyou.dayreport.pass");
			key=WebServiceConstant.getProperties("renwoyou.dayreport.key");
		}
		String md5pass=MD5.encode(pass);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("\"action\"".concat(":\"").concat(actionName).concat("\","));
		builder.append("\"startDate\"".concat(":\"").concat(visiteTime).concat("\","));
		builder.append("\"endDate\"".concat(":\"").concat(visiteTime).concat("\","));
		builder.append("\"idList\"".concat(":[").concat(ticketId).concat("]"));
		builder.append("}");
		String body=builder.toString();
		String sign=MD5.encode(uname+md5pass+body+key);
		params.put("u",uname);
		params.put("p",md5pass);
		params.put("body",body);
		params.put("sign",sign);
		return params;
	}


	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
}
