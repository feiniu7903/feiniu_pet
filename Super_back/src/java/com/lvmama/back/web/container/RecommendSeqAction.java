package com.lvmama.back.web.container;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;

public class RecommendSeqAction extends BaseAction {
	private static final long serialVersionUID = -284237719384716965L;

	private ProdContainerProductService prodContainerProductService;
	
	private Long containerProductId;
	private Integer recommendSeq;
	private Integer oldRecommendSeq;

	@Override
	protected void doBefore() throws Exception {
		this.recommendSeq = this.oldRecommendSeq;
	}

	public void setRecommendSeq() {
		prodContainerProductService.setRecommendSeq(this.containerProductId, this.recommendSeq, this.oldRecommendSeq, this.getOperatorName());
		this.refreshParent("queryButton");
		this.closeWindow();
	}


	public void setProdContainerProductService(ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}

	public void setContainerProductId(Long containerProduct) {
		this.containerProductId = containerProduct;
	}

	public void setRecommendSeq(Integer recommendSeq) {
		this.recommendSeq = recommendSeq;
	}

	public Integer getRecommendSeq() {
		return recommendSeq;
	}

	public void setOldRecommendSeq(Integer oldRecommendSeq) {
		this.oldRecommendSeq = oldRecommendSeq;
	}
	
}
