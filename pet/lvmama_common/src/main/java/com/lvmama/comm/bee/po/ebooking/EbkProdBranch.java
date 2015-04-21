package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @since 2013-09-24
 */
public class EbkProdBranch implements Serializable {

    private static final long serialVersionUID = 138001284499764125L;

    /**
     * column EBK_PROD_BRANCH.PROD_BRANCH_ID
     */
    private Long prodBranchId;

    /**
     * column EBK_PROD_BRANCH.PROD_PRODUCT_ID
     */
    private Long prodProductId;

    /**
     * column EBK_PROD_BRANCH.BRANCH_NAME
     */
    private String branchName;

    /**
     * column EBK_PROD_BRANCH.BRANCH_TYPE
     */
    private String branchType;

    /**
     * column EBK_PROD_BRANCH.ADULT_QUANTITY
     */
    private Long adultQuantity;

    /**
     * column EBK_PROD_BRANCH.CHILD_QUANTITY
     */
    private Long childQuantity;

    /**
     * column EBK_PROD_BRANCH.CREATE_TIME
     */
    private Date createTime;

    /**
     * column EBK_PROD_BRANCH.VIRTUAL_BRANCH_IDS
     */
    private String virtualBranchIds;
    
	/**采购产品类别id**/
    private Long metaProdBranchId;
    
    /**销售产品类别id**/
    private Long prodProductBranchId;

	/**
	 * 类别时间价格
	 */
	private List<EbkProdTimePrice> ebkProdTimePrices=new ArrayList<EbkProdTimePrice>();

	private boolean isInVirtualBranch = false;
	
	private String ebkProductViewType;
    
	private String defaultBranch = Constant.TRUE_FALSE.FALSE.getCode();

    public EbkProdBranch() {
        super();
    }

    public EbkProdBranch(Long prodBranchId, Long prodProductId, String branchName, String branchType, Long adultQuantity, Long childQuantity, Date createTime) {
        this.prodBranchId = prodBranchId;
        this.prodProductId = prodProductId;
        this.branchName = branchName;
        this.branchType = branchType;
        this.adultQuantity = adultQuantity;
        this.childQuantity = childQuantity;
        this.createTime = createTime;
    }

    /**
     * getter for Column EBK_PROD_BRANCH.PROD_BRANCH_ID
     */
    public Long getProdBranchId() {
        return prodBranchId;
    }

    /**
     * setter for Column EBK_PROD_BRANCH.PROD_BRANCH_ID
     * @param prodBranchId
     */
    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

    /**
     * getter for Column EBK_PROD_BRANCH.PROD_PRODUCT_ID
     */
    public Long getProdProductId() {
        return prodProductId;
    }

    /**
     * setter for Column EBK_PROD_BRANCH.PROD_PRODUCT_ID
     * @param prodProductId
     */
    public void setProdProductId(Long prodProductId) {
        this.prodProductId = prodProductId;
    }

    /**
     * getter for Column EBK_PROD_BRANCH.BRANCH_NAME
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * setter for Column EBK_PROD_BRANCH.BRANCH_NAME
     * @param branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * getter for Column EBK_PROD_BRANCH.BRANCH_TYPE
     */
    public String getBranchType() {
        return branchType;
    }

    public String getBranchTypeCh(){
    	if(!StringUtil.isEmptyString(ebkProductViewType) && !StringUtil.isEmptyString(branchType)){
    		if(Constant.EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equalsIgnoreCase(ebkProductViewType)
    			||Constant.EBK_PRODUCT_VIEW_TYPE.DOMESTIC_LONG.name().equalsIgnoreCase(ebkProductViewType)
    			||Constant.EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equalsIgnoreCase(ebkProductViewType)){
    			return Constant.ROUTE_BRANCH.getCnName(branchType);
    		}
    		if(Constant.EBK_PRODUCT_VIEW_TYPE.HOTEL.name().equalsIgnoreCase(ebkProductViewType)){
    			return Constant.HOTEL_BRANCH.getCnName(branchType);
    		}
    	}
    	return branchType;
    }
    /**
     * setter for Column EBK_PROD_BRANCH.BRANCH_TYPE
     * @param branchType
     */
    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    /**
     * getter for Column EBK_PROD_BRANCH.ADULT_QUANTITY
     */
    public Long getAdultQuantity() {
        return adultQuantity;
    }

    /**
     * setter for Column EBK_PROD_BRANCH.ADULT_QUANTITY
     * @param adultQuantity
     */
    public void setAdultQuantity(Long adultQuantity) {
        this.adultQuantity = adultQuantity;
    }

    /**
     * getter for Column EBK_PROD_BRANCH.CHILD_QUANTITY
     */
    public Long getChildQuantity() {
        return childQuantity;
    }

    /**
     * setter for Column EBK_PROD_BRANCH.CHILD_QUANTITY
     * @param childQuantity
     */
    public void setChildQuantity(Long childQuantity) {
        this.childQuantity = childQuantity;
    }

    /**
     * getter for Column EBK_PROD_BRANCH.CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * setter for Column EBK_PROD_BRANCH.CREATE_TIME
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getMetaProdBranchId() {
		return metaProdBranchId;
	}

	public void setMetaProdBranchId(Long metaProdBranchId) {
		this.metaProdBranchId = metaProdBranchId;
	}

	public Long getProdProductBranchId() {
		return prodProductBranchId;
	}

	public void setProdProductBranchId(Long prodProductBranchId) {
		this.prodProductBranchId = prodProductBranchId;
	}
    
    

	public List<EbkProdTimePrice> getEbkProdTimePrices() {
		return ebkProdTimePrices;
	}

	public void setEbkProdTimePrices(List<EbkProdTimePrice> ebkProdTimePrices) {
		this.ebkProdTimePrices = ebkProdTimePrices;
	}

	public String getEbkProductViewType() {
		return ebkProductViewType;
	}

	public void setEbkProductViewType(String ebkProductViewType) {
		this.ebkProductViewType = ebkProductViewType;
	}

	public String getDefaultBranch() {
		return defaultBranch;
	}

	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}
	
	public String getVirtualBranchIds() {
		return virtualBranchIds;
	}

	public void setVirtualBranchIds(String virtualBranchIds) {
		this.virtualBranchIds = virtualBranchIds;
	}

	public boolean getIsInVirtualBranch() {
		return isInVirtualBranch;
	}

	public void setInVirtualBranch(boolean isInVirtualBranch) {
		this.isInVirtualBranch = isInVirtualBranch;
	}

}