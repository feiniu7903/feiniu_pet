package com.lvmama.back.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.utils.ord.RouteUtil;

/**
 * 处理产品上下线的消息处理.
 * 
 * @author yangbin
 * 
 */
public class ProductOnOffProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(ProductOnOffProcesser.class);

	private ProdProductService prodProductService;
	private IOpTravelGroupService opTravelGroupService;
	private ProdProductBranchService prodProductBranchService;
	@Override
	public void process(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("得到产品"+message.getObjectId()+"上下线的通知");
		}
		ProdProduct product = null;
		if (message.isProductOnoffMsg()){
			product = prodProductService.getProdProduct(message.getObjectId());
		}else if(message.isChangeSellPriceMsg()) {
			ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(message.getObjectId());
			if(branch!=null&&branch.hasDefault()){//默认类别修改
				product = prodProductService.getProdProduct(branch.getProductId());
			}
		}
		
		//如果取到产品数据
		if (product != null) {
			// 是需要创建团号并且是上线的操作
			if (product.isOnLine() && RouteUtil.hasTravelGroupProduct(product)) {
				if (log.isDebugEnabled()) {
					log.debug("产品需要生成团号" + product.getProductId());
				}
				opTravelGroupService.createTravelGroupByProductId(product.getProductId());
			}
		}
	}
	
	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}
 

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
	
	
}
