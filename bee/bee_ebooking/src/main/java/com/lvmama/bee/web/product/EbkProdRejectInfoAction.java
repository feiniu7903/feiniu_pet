package com.lvmama.bee.web.product;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;
import com.lvmama.comm.bee.service.ebooking.EbkProdRejectInfoService;
@Results({ @Result(name = "rejectedInfo", location = "/WEB-INF/pages/ebooking/product/subPage/rejectedInfo.jsp")
})
public class EbkProdRejectInfoAction extends EbkBaseAction {

	private EbkProdRejectInfoService ebkProdRejectInfoService;
	
	private Long ebkProdProductId;
	
	private List<EbkProdRejectInfo> rejectList;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5923496264613354202L;

	/**
	 * 查看驳回信息
	 */
	@Action(value="/product/query/rejectMessage")
	public String queryRejectMessage(){
		Long supplierId =getCurrentSupplierId();
		if(null!=supplierId && null!=ebkProdProductId){
			EbkProdRejectInfo ebkProdRejectInfoDO = new EbkProdRejectInfo();
			ebkProdRejectInfoDO.setProductId(ebkProdProductId);
			rejectList = ebkProdRejectInfoService.query(ebkProdRejectInfoDO);
		}
		return "rejectedInfo";
	}

	public EbkProdRejectInfoService getEbkProdRejectInfoService() {
		return ebkProdRejectInfoService;
	}

	public void setEbkProdRejectInfoService(
			EbkProdRejectInfoService ebkProdRejectInfoService) {
		this.ebkProdRejectInfoService = ebkProdRejectInfoService;
	}

	public Long getEbkProdProductId() {
		return ebkProdProductId;
	}

	public void setEbkProdProductId(Long ebkProdProductId) {
		this.ebkProdProductId = ebkProdProductId;
	}

	public List<EbkProdRejectInfo> getRejectList() {
		return rejectList;
	}

	public void setRejectList(List<EbkProdRejectInfo> rejectList) {
		this.rejectList = rejectList;
	}
}
