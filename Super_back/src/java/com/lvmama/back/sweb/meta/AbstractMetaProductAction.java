/**
 * 
 */
package com.lvmama.back.sweb.meta;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.service.meta.MetaProductService;

/**
 * @author yangbin
 *
 */
public abstract class AbstractMetaProductAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1088084178417924712L;
	protected MetaProductService metaProductService;
	protected String menuType;
	protected String metaProductType;
	protected Long metaProductId;
	
	public abstract void doBefore();
	
	public abstract String toEdit();
	public abstract void save();
	public String goAfter(){
		return "input";
	}
	
	public AbstractMetaProductAction(String menuType) {
		super();
		this.menuType = menuType;
	}


	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}
	

	/**
	 * @return the metaProductType
	 */
	public String getMetaProductType() {
		return metaProductType;
	}


	/**
	 * @param metaProductService the metaProductService to set
	 */
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	
	/**
	 * @param metaProductId the metaProductId to set
	 */
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	/**
	 * @return the metaProductId
	 */
	public Long getMetaProductId() {
		return metaProductId;
	}
	
	
}
