/**
 * 
 */
package com.lvmama.back.message;

import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;

/**
 * 重新计算产品时间价格表消息
 * @author yangbin
 *
 */
public class RealTimeUpdateTimePriceProcesser implements MessageProcesser {

	private ProdProductBranchService prodProductBranchService;
	private TopicMessageProducer productMessageProducer;
	@Override
	public void process(Message message) {
		if(message.isProdBranchItemChangeMsg()){//采购打包或删除
			//此处修改全部日期
			//传入的是销售类别的ID
			prodProductBranchService.updateTimePriceByProdBranchId(message.getObjectId());
			
			//发出销售产品类别修改消息
			productMessageProducer.sendMsg(MessageFactory
					.newProductSellPriceMessage(message.getObjectId()));
		}
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	/**
	 * @param productMessageProducer the productMessageProducer to set
	 */
	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}	
	
	
}
