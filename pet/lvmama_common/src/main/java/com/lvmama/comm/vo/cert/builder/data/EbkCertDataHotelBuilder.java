/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.EbkCertDataBuilder;
import com.lvmama.comm.vo.cert.builder.IdentityUtil;

/**
 * 酒店的ebk生成规则
 * @author yangbin
 *
 */
public class EbkCertDataHotelBuilder extends EbkCertDataBuilder {

	
	public EbkCertDataHotelBuilder(Map<String, Object> params) {
		super(params);
		metaProductBranchService = SpringBeanProxy.getBean(MetaProductBranchService.class,"metaProductBranchService");
	}
	
	protected MetaProductBranchService metaProductBranchService;
	

	@Override
	protected void makeCertItemData(EbkCertificateItem item) {
		Map<String,Object> itemMap=new HashMap<String, Object>();
		converProductName(item.getOrderItemMeta().getProductName(), itemMap);
		addMetaProductName(item.getOrderItemMeta(), itemMap);
		branchName = (String) itemMap.get("metaBranchName");
		itemProd = getItemProd(item.getOrderItemMeta().getOrderItemId());
		makeTimeList(item);
		//itemMap.put("nights", makeTimeList(item.getOrderItemMeta()));
		itemMap.put("totalSettlementPrice", getTotalAmount(item.getOrderItemMeta(),itemProd));
		itemMap.put("zhPaymentTarget", getCollect());
		TimePrice tp = metaProductBranchService.getTimePrice(item.getOrderItemMeta().getMetaBranchId(), item.getOrderItemMeta().getVisitTime());
		if(tp != null && tp.getBreakfastCount() != null){
			itemMap.put("breakfastCount",tp.getBreakfastCount());
			itemMap.put("suggestPrice",tp.getSuggestPriceF());
		}else {
			itemMap.put("breakfastCount", 0L);
			itemMap.put("suggestPrice",0F);
		}
		
		itemMap.put("id", IdentityUtil.make(item));
		JSONObject obj = JSONObject.fromObject(itemMap);
		if(obj.toString().length()>3500){
			System.out.println("ERROR:"+obj.toString());
		}
		addData(item.getEbkCertificateItemId(),Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM,obj.toString());
	}

	/**
	 * 生成按天数的数据
	 * @param orderMeta
	 * @return
	 */
	protected void makeTimeList(EbkCertificateItem item){
		OrdOrderItemMeta orderMeta=item.getOrderItemMeta();
		List<OrdOrderItemMetaTime> metaTimes = orderMeta.getAllOrdOrderItemMetaTime();	
		if(CollectionUtils.isNotEmpty(metaTimes)){
			for(OrdOrderItemMetaTime time:metaTimes){		
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("metaBranchName", branchName);
				map.put("visitTime", getDate(time.getVisitTime()));
				if(orderMeta.getVisitTime()==null){
					map.put("leaveTime", "");
				}else{
					Date visitTime = DateUtils.addDays(time.getVisitTime(), 1);
					map.put("leaveTime", DateUtil.getFormatDate(visitTime,"yyyy-MM-dd"));
				}
				map.put("quantity",time.getQuatity()*orderMeta.getProductQuantity());
				map.put("night",1);
				map.put("settlementPrice", getPrice(orderMeta, itemProd));//记录订单子子项的实际结算单价
				addData(item.getEbkCertificateItemId(), Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM_DAY, map);
			}
		}else{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("metaBranchName", branchName);
			map.put("visitTime", getDate(orderMeta.getVisitTime()));
			if(orderMeta.getVisitTime()==null){
				map.put("leaveTime", "");
			}else{
				Date visitTime = DateUtils.addDays(orderMeta.getVisitTime(), 1);
				map.put("leaveTime", DateUtil.getFormatDate(visitTime,"yyyy-MM-dd"));
			}
			map.put("quantity",orderMeta.getQuantity()*orderMeta.getProductQuantity());
			map.put("night",1);
			map.put("settlementPrice", getPrice(orderMeta, itemProd));//记录订单子子项的实际结算单价
			addData(item.getEbkCertificateItemId(), Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM_DAY, map);
		}
		totalSettlementPrice +=getTotalAmount(orderMeta,itemProd);
	}
	
	private OrdOrderItemProd itemProd;
	
	@Override
	protected Map<String, Object> getCertBaseInfo() {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("totalSettlementPrice", totalSettlementPrice);//标识的总价
		map.put("metaProductName", metaProductName);
		map.put("zhPaymentTarget", getCollect());
		return map;
	}
	protected String branchName;
	protected float totalSettlementPrice=0;
}
