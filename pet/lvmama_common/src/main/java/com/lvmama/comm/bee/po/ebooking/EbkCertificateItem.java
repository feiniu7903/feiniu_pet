package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class EbkCertificateItem implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6135293934742001425L;

	private Long ebkCertificateItemId;

    private Long ebkCertificateId;

    private Date createTime;

    private Long orderId;

    private Long orderItemMetaId;

    private String faxMemo;

    private Date ordLastCancelTime;

    private Long totalSettlementPrice;

    private Long settlementPrice;

    private Long metaProductId;

    private String metaProductName;
    
    private String productName;

    private String supplierNo;

    private Long quantity;

    private Long nights;
    
    private Date visitTime;
    
    private Long metaBranchId;
    
    private String valid;
    
    private String isResourceSendFax;
    /**
     * 最新的凭证
     */
	private String isNew;
	
    private List<EbkCertificateItem> ebkCertificateItemList;
    
    private List<OrdOrderItemMeta> ordorderItemMetalist;
    
    private OrdOrderItemMeta orderItemMeta;
    
    private List<EbkOrderDataRev> ebkOrderDataRevList;
    
    private EbkCertificate ebkCertificate;
    
    public EbkCertificateItem() {
    }
    /**
     * 用订单子子项初始化产品
     * @param meta
     */
    public EbkCertificateItem(OrdOrderItemMeta meta) {
    	this.orderId = meta.getOrderId();
    	this.orderItemMetaId = meta.getOrderItemMetaId();
    	this.faxMemo = meta.getFaxMemo();
    	this.ordLastCancelTime = meta.getOrdOrder().getLastCancelTime();
    	this.totalSettlementPrice = meta.getSettlementPrice();
    	this.settlementPrice = meta.getSettlementPrice();
    	this.metaProductId = meta.getMetaProductId();
    	this.metaProductName = meta.getProductName();
    	this.quantity = meta.getQuantity()*meta.getProductQuantity();
    	this.nights = meta.getNights();
    	this.orderItemMeta = meta;
    }
    public Long getRoomQuantity() {
    	if(this.ebkCertificate != null && Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(ebkCertificate.getSubProductType())) {
    		return this.quantity;
    	}
    	return this.quantity / nights;
    }
    public String getBranchName() {
    	if(this.metaProductName != null) {
    		int start=this.metaProductName.lastIndexOf("(");
    		String sub=this.metaProductName.substring(start+1);
    		int count=StringUtils.countMatches(sub, ")");
    		while(count-->1){
    			start=this.metaProductName.lastIndexOf("(",start-1);
    		}
    		String metaBranchName = this.metaProductName;
    		if(start != -1){
    			metaBranchName = this.metaProductName.substring(start+1,this.metaProductName.length()-1);
    		}
    		return metaBranchName;
    	}
    	return this.metaProductName;
    }
    public String getRealProductName() {
    	if(this.metaProductName != null) {
    		if(this.metaProductName.lastIndexOf(")")==this.metaProductName.length()-1){
    			int end = this.metaProductName.lastIndexOf("(");
    			if(end<0){
    				end = this.metaProductName.length();
    			}
    			return this.metaProductName.substring(0, end);
    		}
    	}
    	return this.metaProductName;
    }
    
	public Long getEbkCertificateItemId() {
        return ebkCertificateItemId;
    }

    public void setEbkCertificateItemId(Long ebkCertificateItemId) {
        this.ebkCertificateItemId = ebkCertificateItemId;
    }

    public Long getEbkCertificateId() {
        return ebkCertificateId;
    }

    public void setEbkCertificateId(Long ebkCertificateId) {
        this.ebkCertificateId = ebkCertificateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderItemMetaId() {
        return orderItemMetaId;
    }

    public void setOrderItemMetaId(Long orderItemMetaId) {
        this.orderItemMetaId = orderItemMetaId;
    }

    public String getFaxMemo() {
        return faxMemo;
    }

    public void setFaxMemo(String faxMemo) {
        this.faxMemo = faxMemo == null ? null : faxMemo.trim();
    }

    public Date getOrdLastCancelTime() {
        return ordLastCancelTime;
    }

    public void setOrdLastCancelTime(Date ordLastCancelTime) {
        this.ordLastCancelTime = ordLastCancelTime;
    }

    public Long getTotalSettlementPrice() {
        return totalSettlementPrice;
    }

    public void setTotalSettlementPrice(Long totalSettlementPrice) {
        this.totalSettlementPrice = totalSettlementPrice;
    }

    public Long getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(Long settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public Long getMetaProductId() {
        return metaProductId;
    }

    public void setMetaProductId(Long metaProductId) {
        this.metaProductId = metaProductId;
    }

    public String getMetaProductName() {
        return metaProductName;
    }

    public void setMetaProductName(String metaProductName) {
        this.metaProductName = metaProductName == null ? null : metaProductName.trim();
    }

    public String getProductName() {
		return productName;
	}
    
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo == null ? null : supplierNo.trim();
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getNights() {
        return nights;
    }

    public void setNights(Long nights) {
        this.nights = nights;
    }
    
	public Date getVisitTime() {
		return visitTime;
	}
	
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	
	public List<OrdOrderItemMeta> getOrdorderItemMetalist() {
		return ordorderItemMetalist;
	}
	public void setOrdorderItemMetalist(List<OrdOrderItemMeta> ordorderItemMetalist) {
		this.ordorderItemMetalist = ordorderItemMetalist;
	}
	public Long getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public OrdOrderItemMeta getOrderItemMeta() {
		return orderItemMeta;
	}
	public Float getSettlementPriceYuan()
	{
		return PriceUtil.convertToYuan(this.getSettlementPrice());
	}
	public void setOrderItemMeta(OrdOrderItemMeta orderItemMeta) {
		this.orderItemMeta = orderItemMeta;
	}
	public List<EbkOrderDataRev> getEbkOrderDataRevList() {
		return ebkOrderDataRevList;
	}
	public void setEbkOrderDataRevList(List<EbkOrderDataRev> ebkOrderDataRevList) {
		this.ebkOrderDataRevList = ebkOrderDataRevList;
	}
	public EbkCertificate getEbkCertificate() {
		return ebkCertificate;
	}
	public void setEbkCertificate(EbkCertificate ebkCertificate) {
		this.ebkCertificate = ebkCertificate;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public List<EbkCertificateItem> getEbkCertificateItemList() {
		return ebkCertificateItemList;
	}
	public void setEbkCertificateItemList(List<EbkCertificateItem> ebkCertificateItemList) {
		this.ebkCertificateItemList = ebkCertificateItemList;
	}
	public String getIsResourceSendFax() {
		return isResourceSendFax;
	}
	public void setIsResourceSendFax(String isResourceSendFax) {
		this.isResourceSendFax = isResourceSendFax;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	
}