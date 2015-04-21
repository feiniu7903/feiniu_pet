/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.data;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.EbkCertDataBuilder;
import com.lvmama.comm.vo.cert.builder.IdentityUtil;

/**
 * @author yangbin
 *
 */
public class EbkCertDataRouteBuilder extends EbkCertDataBuilder {

	public EbkCertDataRouteBuilder(Map<String, Object> params) {
		super(params);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.vo.cert.builder.EbkCertDataBuilder#makeCertItemData(com.lvmama.comm.bee.po.ebooking.EbkCertificateItem)
	 */
	@Override
	protected void makeCertItemData(EbkCertificateItem item) {
		OrdOrderItemMeta meta = item.getOrderItemMeta();
		Map<String,Object> map = new HashMap<String, Object>();
		converProductName(meta.getProductName(),map);
		addMetaProductName(meta, map);
		OrdOrderItemProd itemProd = getItemProd(item.getOrderItemMeta().getOrderItemId());
		
		map.put("orderId", order.getOrderId());
		map.put("visitTime", getDate(meta.getVisitTime()));
		map.put("quantity", meta.getProductQuantity()*meta.getQuantity());
		map.put("settlementPrice", getPrice(meta, itemProd));
		map.put("adultQuantity", meta.getTotalAdultQuantity());
		map.put("childQuantity", meta.getTotalChildQuantity());
		Float totlSettlementPrice = getTotalAmount(meta, itemProd);
		map.put("totalSettlementPrice", totlSettlementPrice);		
		totalSettlementPrice+=totlSettlementPrice;
		map.put("zhPaymentTarget", getCollect());
		map.put("id", IdentityUtil.make(item));
		JSONObject obj = JSONObject.fromObject(map);				
		addData(item.getEbkCertificateItemId(),Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM,obj.toString());				
	}

	@Override
	protected Map<String, Object> getCertBaseInfo() {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("totalSettlementPrice", totalSettlementPrice);//标识的总价
		map.put("metaProductName", metaProductName);
		map.put("visitTime", getDate(order.getVisitTime()));
		
		return map;
	}

	private long totalSettlementPrice=0;
}
