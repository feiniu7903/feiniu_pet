package com.lvmama.back.message;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

/**
 * 收到了采购产品更改采购价的消息后，首先需要变更所有的相关产品的销售价，然后再更新所有与该采购产品相关的销售产品的门市价
 * @author wangzhengyan
 *
 */
public class UpdateMarketPriceProcesser implements MessageProcesser {
	
    private TopicMessageProducer productMessageProducer;
    private ProdProductBranchService prodProductBranchService;
    private ProdProductService prodProductService;
    private ComSearchInfoUpdateService comSearchInfoUpdateService;
	public void process(Message message) {
		 if(message.isChangeMetaTimePriceMsg()){
			 PropertyEditor editor=new TimeRange.TimeRangePropertEditor();
			 editor.setAsText(message.getAddition());
			 TimeRange timeRange=(TimeRange)editor.getValue();			 
			 List<ProdProductBranchItem> list=prodProductBranchService.selectItemListByMetaBranch(message.getObjectId());
			 Set<Long> set=new HashSet<Long>();
			 if(CollectionUtils.isNotEmpty(list)){
				 for(ProdProductBranchItem ppbi:list){
					 ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(ppbi.getProdBranchId());
					 if(branch!=null){
						 //更新销售价
						 prodProductBranchService.updateTimePriceByProdBranchId(branch.getProdBranchId(),timeRange);
						 
						 //再更新其市场价
						 prodProductBranchService.updatePriceByBranchId(branch.getProdBranchId());
						 //如果修改时间早于当前时间，触发search_info做修改
						 if(timeRange.getStart().before(new Date())){
							 comSearchInfoUpdateService.productBranchUpdated(branch.getProdBranchId());
						 }
						 set.add(branch.getProductId());						 
					 }
				 }
			 }
			 //对有修改的类别对应的产品做价格变更
			 for(Long ids:set){
				 try{
					 prodProductService.updatePriceByProductId(ids);	
					 productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(ids));
					//发送修改销售产品的通知ebk消息
					productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(ids));
				 }catch(Exception ex){
					 ex.printStackTrace();
				 }
			 }
		 }
	}


	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}


	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
	
}
