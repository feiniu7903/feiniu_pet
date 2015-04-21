/**
 * 
 */
package com.lvmama.shholiday.notify;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.shholiday.service.ShHolidayProductService;
import com.lvmama.shholiday.vo.product.ProductInfo;


/**
 * @author yangbin
 *
 */
public class TourProductNotifyRQHandle extends AbstractShholidayNotify{
	private ShHolidayProductService shholidayProductService;
	private MetaProductBranchService metaProductBranchService;
	public TourProductNotifyRQHandle() {
		super("ProductNotifyRQ", "OTA_TourProductNotifyRS");
		shholidayProductService = (ShHolidayProductService)SpringBeanProxy.getBean("shholidayProductService");
		metaProductBranchService = (MetaProductBranchService)SpringBeanProxy.getBean("metaProductBranchService");
	}

	@Override
	protected void handle(Element body)  {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		ProductInfo productInfo = new ProductInfo();
		String productId = body.attributeValue("ProductId");
		productInfo.setSupplierProdId(productId);
		List<MetaProductBranch> metaBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null, productId);
		if(metaBranchs == null || metaBranchs.isEmpty()){
			setError("60000", "产品不存在", "");
		}else{
			boolean needUpdateOnLine = false;
			List<Element> dateTypes = body.element("DataTypes").elements();
			for(Element dateType : dateTypes){
				String type = dateType.attributeValue("Code");
				if(StringUtils.isNotBlank(type)){
					if(PRODUCT_NOTIFY_TYPE.PRODUCTINFO.name().equals(type.toUpperCase())){
						needUpdateOnLine = true;
						Element prodInfo = body.element("ProductInfo");
						String validStr = prodInfo.elementText("Valid");
						if(StringUtils.isNotBlank(validStr)){
							boolean online = "Y".equals(validStr);
							productInfo.setOnline(online);
							if(!online){
								break;
							}
						}
					}else if(PRODUCT_NOTIFY_TYPE.TEAMPRODUCTPRICES.name().equals(type.toUpperCase())){
						updateProductPrice(productId);
					}
				}
			}
			if(needUpdateOnLine){
				onOffLineProduct(productInfo);
			}
			if(productInfo.isOnline()){
				updateProductDetail(productInfo);
			}
		}
		List<String> orderInfos = new ArrayList<String>();
		Element orderEle = body.element("OrderInfos");
		List<Element> orderEles = orderEle.elements();
		for(Element order : orderEles){
			String orderNo = order.attributeValue("OrderPackageNo");
			orderInfos.add(orderNo);
		}
		addParam("orderInfos", orderInfos);
	}

	private void updateProductDetail(ProductInfo productInfo)  {
		String productId = productInfo.getSupplierProdId();
		shholidayProductService.productNotify(productId);
	}

	private void onOffLineProduct(ProductInfo productInfo) {
		try {
			shholidayProductService.onOfflineProduct(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void updateProductPrice(String productId){
		try {
			shholidayProductService.updateProductTimePrice(productId, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
