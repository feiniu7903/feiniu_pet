package com.lvmama.back.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.ebooking.EbkProdImportService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 
 * @author zhushuying
 * 
 */
public class EbkProductImportProcesser implements MessageProcesser {

	private EbkProdImportService ebkProdImportService;
	private EbkProdProductService ebkProdProductService;
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	
	@Override
	public void process(Message message) {
		if (message.isProductChangeEbkMsg()) {
			try {
					// 修改销售产品
					ebkProdImportService.importProdProduct(message.getObjectId());
					log.info("ProdProduct prodProductId:"+message.getObjectId()+",import end...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (message.isMetaProductTypeByRouteChangeMsg()) {
			try {
				// 修改采购产品
				ebkProdImportService.importMetaProduct(message.getObjectId());
				log.info("MetadProduct metaProductId:"+message.getObjectId()+",import end...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	
	public void setEbkProdImportService(EbkProdImportService ebkProdImportService) {
		this.ebkProdImportService = ebkProdImportService;
	}

	public void setEbkProdProductService(EbkProdProductService ebkProdProductService) {
		this.ebkProdProductService = ebkProdProductService;
	}
}
