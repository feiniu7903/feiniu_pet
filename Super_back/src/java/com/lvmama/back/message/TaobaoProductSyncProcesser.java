package com.lvmama.back.message;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.tmall.TaobaoProductSyncService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant.PRODUCT_TYPE;

public class TaobaoProductSyncProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(TaobaoProductSyncProcesser.class);
	
	private ProdProductService prodProductService;
	private ProdProductBranchService prodProductBranchService;
	private TaobaoProductSyncService taobaoProductSyncService;
	@Override
	public void process(Message message) {
		// 产品信息修改
		if (message.isProductChangeMsg()) {
			log.info("productChange:"+message);
			Long productId = message.getObjectId();
			ProdProduct product = prodProductService.getProdProductById(productId);
			String type = product.getProductType();
			if (type.equals(PRODUCT_TYPE.ROUTE.name())) {
				try {
					taobaoProductSyncService.updateTaobaoTravelDuration(productId);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("updateTaobaoProdAuctionStatus error!productId=" + productId, e);
				}
			}
		}
		
		// 销售产品上下线
		if (message.isProductOnoffMsg()) {
			log.info("productOnoff:"+message);
			Long productId = message.getObjectId();
			try {
				taobaoProductSyncService.updateTaobaoProdAuctionStatus(productId);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateTaobaoProdAuctionStatus error!productId=" + productId, e);
			}
		}
		
		// 销售产品类别上下线
		if (message.isProductBranchOnoffMsg()) {
			log.info("productBranchOnoffMsg:"+message);
			Long productId = message.getObjectId();
			Long prodBranchId = Long.valueOf(message.getAddition());
			try {
				taobaoProductSyncService.updateTaobaoProdBranchAuctionStatus(productId, prodBranchId);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateTaobaoProdBranchAuctionStatus error!productId=" + productId + ", prodBranchId=" + prodBranchId, e);
			}
		}
		
		//修改采购价格和市场价格
		if(message.isChangeMetaTimePriceMsg() || message.isChangeMarketPriceMsg()){
			log.info("metaPriceChange:"+message);
			Long metaBranchId = message.getObjectId();
			List<ProdProduct> productList =  prodProductService.selectProductByMetaBranchId(metaBranchId);
			for(ProdProduct product : productList){
				// 获取销售产品的ID
				Long productId = product.getProductId();
				String type = product.getProductType();
				List<ProdProductBranch> list = prodProductBranchService.getProductBranchByProductId(productId);
				for (ProdProductBranch prodProductBranch : list) {
					Long prodBranchId = prodProductBranch.getProdBranchId();
					// 门票
					if (type.equals(PRODUCT_TYPE.TICKET.name())) {
						try {
							taobaoProductSyncService.updateTaobaoTicketSkuEffDates(productId, prodBranchId);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("updateTaobaoTicketSkuEffDates error!productId=" + productId + ", prodBranchId=" + prodBranchId, e);
						}
						
						// 线路
					} else if (type.equals(PRODUCT_TYPE.ROUTE.name())) {
						try {
							taobaoProductSyncService.updateTaobaoTravelComboCalendar(productId, prodBranchId);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("updateTaobaoTravelComboCalendar error!productId=" + productId + ", prodBranchId=" + prodBranchId, e);
						}
					}
				}
			}
		}
		
		// 销售价格
		if(message.isChangeSellPriceMsg()){
			log.info("productPriceChange:"+message);
			Long prodBranchId = message.getObjectId();
			ProdProduct product = prodProductService.selectProductByProdBranchId(prodBranchId);
			Long productId = product.getProductId();
			String type = product.getProductType();
			// 门票
			if (type.equals(PRODUCT_TYPE.TICKET.name())) {
				try {
					taobaoProductSyncService.updateTaobaoTicketSkuEffDates(productId, prodBranchId);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("updateTaobaoTicketSkuEffDates error!productId=" + productId + ", prodBranchId=" + prodBranchId, e);
				}
				
				// 线路
			} else if (type.equals(PRODUCT_TYPE.ROUTE.name())) {
				try {
					taobaoProductSyncService.updateTaobaoTravelComboCalendar(productId, prodBranchId);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("updateTaobaoTravelComboCalendar error!productId=" + productId + ", prodBranchId=" + prodBranchId, e);
				}
			}
		}
	}
	
	public ProdProductService getProdProductService() {
		return prodProductService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public TaobaoProductSyncService getTaobaoProductSyncService() {
		return taobaoProductSyncService;
	}
	public void setTaobaoProductSyncService(
			TaobaoProductSyncService taobaoProductSyncService) {
		this.taobaoProductSyncService = taobaoProductSyncService;
	}
	public ProdProductBranchService getProdProductBranchService() {
		return prodProductBranchService;
	}
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
}
