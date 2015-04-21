package com.lvmama.comm.vo;

import java.io.Serializable;

import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.spring.SpringBeanProxy;

public class EditEbkProdRecoverUtil implements Serializable {
	private Long ebkProdProductId;
	public EditEbkProdRecoverUtil(Long ebkProdProductId) {
		super();
		this.ebkProdProductId = ebkProdProductId;
	}
	public void auditRecover(){
		synchronized (EditEbkProdRecoverUtil.class) {
			EbkProdProductService ebkProdProductService = (EbkProdProductService)SpringBeanProxy.getBean("ebkProdProductService");
			EbkProdProduct product = ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
			if(Constant.EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name().equals(product.getStatus()) ||
			   Constant.EBK_PRODUCT_AUDIT_STATUS.REJECTED_AUDIT.name().equals(product.getStatus())){
				ebkProdProductService.auditRecover(ebkProdProductId);
			}
		}
	}
	public Long getEbkProdProductId() {
		return ebkProdProductId;
	}
	public void setEbkProdProductId(Long ebkProdProductId) {
		this.ebkProdProductId = ebkProdProductId;
	}
}
