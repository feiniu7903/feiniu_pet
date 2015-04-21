package com.lvmama.back.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;

/**
 * 收到了销售产品时间价格表的变动，更新该销售产品中的销售价
 * @author wangzhengyan
 *
 */
public class UpdateProductPriceProcesser implements MessageProcesser {
	
	private TopicMessageProducer productMessageProducer;
	private ProdProductService prodProductService;
	private ProdProductBranchService prodProductBranchService;
	private static final Log log = LogFactory.getLog(UpdateProductPriceProcesser.class);

	public void process(Message message) {
		if (message.isChangeSellPriceMsg()) {
			//现在传入的为产品类别的ID.
			ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(message.getObjectId());
			if(branch!=null){
				prodProductBranchService.updatePriceByBranchId(branch.getProdBranchId());
				prodProductService.updatePriceByProductId(branch.getProductId());
				log.info("update product price" + message.getObjectId());
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(branch.getProductId()));
				//发送修改销售产品的通知ebk消息
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(branch.getProductId()));
			}
		}
	}

	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
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
