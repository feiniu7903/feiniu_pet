package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaobaoProductSyncPojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5396166116265402373L;
	private Long id;
	private String tbType;
	private String tbTicketType;
	private Long tbItemId;
	private Long tbCid;
	private String tbTitle;
	private Date tbModified;
	private String tbAuctionStatus;
	private Long productId;
	private Long ticketSkuId;
	private Long travelComboId;
	private String tbOuterId;
	private String tbPidVid;
	private Long tbProdSyncId;
	private Long tbPid;
	private Long tbVid;
	private String tbComboName;
	private String isSync;
	private String onLine;
	private String isAperiodic;
	private String productName;
	private Long prodBranchId;
	private String branchName;
	private String filialeName;
	private String branchNames;
    private String tbVidName;
	private List<TaobaoTravelComboType> comboTypes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTbType() {
		return tbType;
	}
	public void setTbType(String tbType) {
		this.tbType = tbType;
	}
	public String getTbTicketType() {
		return tbTicketType;
	}
	public void setTbTicketType(String tbTicketType) {
		this.tbTicketType = tbTicketType;
	}
	public Long getTbItemId() {
		return tbItemId;
	}
	public void setTbItemId(Long tbItemId) {
		this.tbItemId = tbItemId;
	}
	public Long getTbCid() {
		return tbCid;
	}
	public void setTbCid(Long tbCid) {
		this.tbCid = tbCid;
	}
	public String getTbTitle() {
		return tbTitle;
	}
	public void setTbTitle(String tbTitle) {
		this.tbTitle = tbTitle;
	}
	public Date getTbModified() {
		return tbModified;
	}
	public void setTbModified(Date tbModified) {
		this.tbModified = tbModified;
	}
	public String getTbAuctionStatus() {
		return tbAuctionStatus;
	}
	public void setTbAuctionStatus(String tbAuctionStatus) {
		this.tbAuctionStatus = tbAuctionStatus;
	}
	public String getTbOuterId() {
		return tbOuterId;
	}
	public void setTbOuterId(String tbOuterId) {
		this.tbOuterId = tbOuterId;
	}
	public String getIsSync() {
		return isSync;
	}
	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getOnLine() {
		return onLine;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
	public String getIsAperiodic() {
		return isAperiodic;
	}
	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProdBranchId() {
		return prodBranchId;
	}
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getTravelComboId() {
		return travelComboId;
	}
	public void setTravelComboId(Long travelComboId) {
		this.travelComboId = travelComboId;
	}
	public String getTbPidVid() {
		return tbPidVid;
	}
	public void setTbPidVid(String tbPidVid) {
		this.tbPidVid = tbPidVid;
	}
	public Long getTbProdSyncId() {
		return tbProdSyncId;
	}
	public void setTbProdSyncId(Long tbProdSyncId) {
		this.tbProdSyncId = tbProdSyncId;
	}
	public Long getTbPid() {
		return tbPid;
	}
	public void setTbPid(Long tbPid) {
		this.tbPid = tbPid;
	}
	public Long getTbVid() {
		return tbVid;
	}
	public void setTbVid(Long tbVid) {
		this.tbVid = tbVid;
	}
	public String getTbComboName() {
		return tbComboName;
	}
	public void setTbComboName(String tbComboName) {
		this.tbComboName = tbComboName;
	}
	public Long getTicketSkuId() {
		return ticketSkuId;
	}
	public void setTicketSkuId(Long ticketSkuId) {
		this.ticketSkuId = ticketSkuId;
	}
	public String getFilialeName() {
		return filialeName;
	}
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	public List<TaobaoTravelComboType> getComboTypes() {
		return comboTypes;
	}
	public void setComboTypes(List<TaobaoTravelComboType> comboTypes) {
		this.comboTypes = comboTypes;
	}
	public String getBranchNames() {
		return branchNames;
	}
	public void setBranchNames(String branchNames) {
		this.branchNames = branchNames;
	}

    public String getTbVidName() {
        return tbVidName;
    }

    public void setTbVidName(String tbVidName) {
        this.tbVidName = tbVidName;
    }
}
