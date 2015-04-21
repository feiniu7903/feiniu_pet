/**
 * 
 */
package com.lvmama.front.web.product;

import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.front.web.BaseAction;

/**
 * @author yangbin
 *
 */
public abstract class ProductBaseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1174887818599363333L;
	protected ProductHeadQueryService productServiceProxy;
	protected PageService pageService;
	protected long id;	//产品id
	protected Long productId;
	protected long prodBranchId;
	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public void setProdBranchId(long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	public long getId() {
		return id;
	}
	public Long getProductId() {
		return productId;
	}
	public long getProdBranchId() {
		return prodBranchId;
	}
	
}
