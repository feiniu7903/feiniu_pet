package com.lvmama.back.message;

import java.beans.PropertyEditor;
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

/**
 * 对于期票产品,修改销售类别时间价格表后,更新其有效期
 * @author shihui
 *
 */
public class UpdateProdBranchValidTimeProcesser implements MessageProcesser {
	
    private ProdProductBranchService prodProductBranchService;
	public void process(Message message) {
		 if(message.isUpdateProdBranchValidTime()){
			 PropertyEditor editor=new TimeRange.TimeRangePropertEditor();
			 editor.setAsText(message.getAddition());
			 TimeRange timeRange=(TimeRange)editor.getValue();
			//现在传入的为产品类别的ID.
			 List<ProdProductBranchItem> list=prodProductBranchService.selectBranchItemByBranchId(message.getObjectId());
			 //期票销售类别只能打包一个采购类别
			 if(CollectionUtils.isNotEmpty(list)){
				 ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(list.get(0).getProdBranchId());
				 if(branch != null){
					 //重新计算有效期并更新
					 prodProductBranchService.updateTimePriceByProdBranchId(branch.getProdBranchId(),timeRange);
				 }
			 }
		 }
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
}
