/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.IdentityUtil;

/**
 * @author yangbin
 *
 */
public class EbkCertDataHotelSuitBuilder extends EbkCertDataHotelBuilder{

	public EbkCertDataHotelSuitBuilder(Map<String, Object> params) {
		super(params);
	}

	@Override
	protected void makeCertItemData(EbkCertificateItem item) {
		Map<String,Object> itemMap=new HashMap<String, Object>();
		converProductName(item.getOrderItemMeta().getProductName(), itemMap);
		addMetaProductName(item.getOrderItemMeta(), itemMap);
		
		OrdOrderItemProd itemProd=getItemProd(item.getOrderItemMeta().getOrderItemId());
		itemMap.put("visitTime", getDate(item.getOrderItemMeta().getVisitTime()));
		if(item.getOrderItemMeta().getVisitTime()==null){
			itemMap.put("leaveTime", "");
		}else{
			itemMap.put("leaveTime", DateUtil.getFormatDate(item.getOrderItemMeta().getEndDate(),"yyyy-MM-dd"));
		}
		
		float f=getTotalAmount(item.getOrderItemMeta(), itemProd);
		totalSettlementPrice+=f;
		itemMap.put("totalSettlementPrice",f);
		itemMap.put("settlementPrice", getPrice(item.getOrderItemMeta(), itemProd));
		itemMap.put("quantity", item.getOrderItemMeta().getProductQuantity()*item.getOrderItemMeta().getQuantity());
		itemMap.put("zhPaymentTarget", getCollect());
		itemMap.put("nights", item.getOrderItemMeta().getNights());
		TimePrice tp = metaProductBranchService.getTimePrice(item.getOrderItemMeta().getMetaBranchId(), item.getOrderItemMeta().getVisitTime());
		if(tp!=null){
			if(tp.getBreakfastCount() != null){
				itemMap.put("breakfastCount", tp.getBreakfastCount());
			}else {
				itemMap.put("breakfastCount", 0L);
			}
			if(tp.getSuggestPrice()!=null){
				itemMap.put("suggestPrice", tp!=null?tp.getSuggestPriceF():0F);
			}
		}
		itemMap.put("id", IdentityUtil.make(item));
		JSONObject obj = JSONObject.fromObject(itemMap);
		findProdProductId(item.getOrderItemMeta().getOrderItemId());
		addData(item.getEbkCertificateItemId(),Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM,obj.toString());
	}

	@Override
	protected Map<String, Object> getCertBaseInfo() {
		Map<String, Object> map= super.getCertBaseInfo();
		ViewPageService viewPageService = SpringBeanProxy.getBean(ViewPageService.class,"viewPageService");
		ViewPage page = viewPageService.getViewPage(productId);
//		System.out.println("productId:"+productId);
		if(page!=null && page.getContents() != null){
			Object costcontain = page.getContents().get(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
			if(costcontain != null){
				map.put("COSTCONTAIN", removeHtml(((ViewContent)costcontain).getContent()));	
			}
			Object nocostcontain = page.getContents().get(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
			if(nocostcontain != null){
				map.put("NOCOSTCONTAIN", removeHtml(((ViewContent)nocostcontain).getContentRn()));
			}
		}
		map.put("totalSettlementPrice", totalSettlementPrice);
		return map;		
	}
	
	/**
	 * 找到子子项对应的销售产品项
	 * @param ordOrderItemProd
	 */
	private void findProdProductId(final Long ordOrderItemProd){
		OrdOrderItemProd itemProd = getItemProd(ordOrderItemProd);
		productId = itemProd.getProductId();
	}
	
	private String removeHtml(String html){
		if(StringUtils.isEmpty(html)||html.equals("null")){
			return "";
		}
		return html.replaceAll("<.*?>", "").trim();
	}
	private Long productId;
	private float totalSettlementPrice=0F;
}
