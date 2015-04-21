/**
 * 
 */
package com.lvmama.back.sweb.prod;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.utils.MemcachedUtil;

/**
 * 销售产品基础父类
 * @author yangbin
 *
 */
@Results({
	@Result(name="product_exception",location="/WEB-INF/pages/back/prod/page_not_found.jsp"),
	@Result(name="product_authority",location="/WEB-INF/pages/back/prod/product_authority.jsp")
})
public abstract class AbstractProductAction extends BaseAction {

	private static final long serialVersionUID = 5460228310851633249L;
	protected ProdProductService prodProductService;
	protected Long productId;
	//产品类型
	protected String menuType = "prod";
	protected final String PRODUCT_EXCEPTION_PAGE="product_exception";
	
	protected String hasSensitiveWord;
    /**
     * 返回产品审核查看页面
     */
    protected String auditingStatusMark;
	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	
	public abstract boolean doBefore();
	

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	/**
	 * 清除产品缓存
	 * @param productId
	 */
	protected void removeProductCache(final Long productId){
//		MemCachedUtil.remove("PROD_C_PRODUCT_INFO_" + productId);
		MemcachedUtil.getInstance().remove("PROD_C_PRODUCT_INFO_" + productId);
	}

	/**
	 * 后处理
	 * @return
	 */
	protected final String goAfter(){
        if("Y".equals(auditingStatusMark)){ //返回产品审核查看页面
            return "auditingShow";
        }
		return INPUT;
	}
	//初始化进入方法
	public abstract String goResult();
	
	public abstract String goEdit();
	//保存方法
	public abstract void save();
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

    public String getAuditingStatusMark() {
        return auditingStatusMark;
    }

    public void setAuditingStatusMark(String auditingStatusMark) {
        this.auditingStatusMark = auditingStatusMark;
    }

	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}

	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	}
}
