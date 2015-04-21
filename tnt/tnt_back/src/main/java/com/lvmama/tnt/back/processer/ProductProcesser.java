package com.lvmama.tnt.back.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.tnt.comm.vo.TntConstant.OBJECT_TYPE;
import com.lvmama.tnt.prod.service.TntProductService;

/**
 * lvmama 产品更新监控
 * 
 * @author gaoxin
 * 
 */
public class ProductProcesser implements MessageProcesser {
	private final Log log = LogFactory.getLog(ProductProcesser.class);
	
	@Autowired
	private TntProductService tntProductService;
	@Override
	public void process(Message message) {
		log.info("TntProductProcesser message: " + message);
		/*
		 * 本期暂时不用
		 * || message.isProductOnoffMsg()
		|| message.isProductBranchOnoffMsg()*/
		/** 产品更新 */
		if (message.isProductCreateMsg() || message.isProductChangeMsg()) {
			Long productId = message.getObjectId();
			tntProductService.sync(productId, OBJECT_TYPE.PRODUCT);
		}
		
		// 采购产品更改支付对象取消分销逻辑变更
		if (message.isMetaProductChangeMsg()) {
//			String payTarget = message.getAddition();
			Long metaProductId = message.getObjectId();
			tntProductService.sync(metaProductId, OBJECT_TYPE.PRODUCT);
		}

	}
	

}
