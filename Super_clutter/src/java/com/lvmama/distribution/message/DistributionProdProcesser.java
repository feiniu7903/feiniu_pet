package com.lvmama.distribution.message;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.distribution.service.DistributionCommonService;

public class DistributionProdProcesser implements MessageProcesser{
	private final Log log=LogFactory.getLog(DistributionProdProcesser.class);
	private DistributionCommonService distributionCommonService;
	private ProdProductService prodProductService;
	
	@Override
	public void process(Message message) {
		if(message.isProductChangeMsg() || message.isProductOnoffMsg() || message.isProductBranchOnoffMsg()){
			Long productId=message.getObjectId();
			distributionCommonService.addProductToDistributor(productId);
			
			/*// 销售产品更改渠道取消分销逻辑变更
			if(message.isProductChangeMsg()) {
				distributionCommonService.cancelDistributorByProdChannel(productId);
			}*/
		}
		
		if(message.isChangeMetaTimePriceMsg()){
			Long metaBranchId = message.getObjectId();
			List<ProdProduct> productList =  prodProductService.selectProductByMetaBranchId(metaBranchId);
			for(ProdProduct product : productList){
				distributionCommonService.addProductToDistributor(product.getProductId());
			}
		}
		
		if(message.isChangeSellPriceMsg()){
			Long prodBranchId = message.getObjectId();
			ProdProduct product = prodProductService.selectProductByProdBranchId(prodBranchId);
			distributionCommonService.addProductToDistributor(product.getProductId());
			
		}
		
		/*// 采购产品更改支付对象取消分销逻辑变更
		if(message.isMetaProductChangeMsg()) {
			String payTarget = message.getAddition();
			Long metaProductId = message.getObjectId();
			distributionCommonService.cancelDistributorByMetaProdTarget(metaProductId, payTarget);
		}*/
		
	}
	
	public void setDistributionCommonService(
			DistributionCommonService distributionCommonService) {
		this.distributionCommonService = distributionCommonService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	
}
