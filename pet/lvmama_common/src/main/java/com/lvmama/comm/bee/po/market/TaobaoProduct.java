package com.lvmama.comm.bee.po.market;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.utils.DateUtil;

public class TaobaoProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tbProductInterfaceId;
	private Long tbProductReturnId;
	private String cateName;
	private Long cateId;
	private String pidVid;
	private Long productId;
	private Date tbPushDate;
	private String tbStatus;
	private String isTbOnline;
	private String isMaped;
	private Date jhsPushDate;
	private Date createDate;
	private Date modifyDate;

	private ProdProduct prodProduct;
	
	public String getStrTbStatus() {
		if (null != tbStatus) {
			if ("onsale".equals(tbStatus)) {
				return "上架";
			} else {
				return "下架";
			}
		}
		return "";
	}
	
	public String getStrIsTbOnline() {
		if (null != isTbOnline && "Y".equals(isTbOnline)) {
			return "是";
		}
		return "否";
	}
	
	public String getStrIsMaped() {
		if (null != isMaped && "Y".equals(isMaped)) {
			return "是";
		}
		return "否";
	}

	public Long getTbProductInterfaceId() {
		return tbProductInterfaceId;
	}

	public void setTbProductInterfaceId(Long tbProductInterfaceId) {
		this.tbProductInterfaceId = tbProductInterfaceId;
	}

	public Long getTbProductReturnId() {
		return tbProductReturnId;
	}

	public void setTbProductReturnId(Long tbProductReturnId) {
		this.tbProductReturnId = tbProductReturnId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public String getPidVid() {
		return pidVid;
	}

	public void setPidVid(String pidVid) {
		this.pidVid = pidVid;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getTbPushDate() {
		return tbPushDate;
	}

	public void setTbPushDate(Date tbPushDate) {
		this.tbPushDate = tbPushDate;
	}
	
	public String getTbPushDateStr() {
		if (null == tbPushDate) {
			return "";
		}
		return DateUtil.formatDate(tbPushDate, "yyyy-MM-dd HH:mm:ss");
	}

	public String getTbStatus() {
		return tbStatus;
	}

	public void setTbStatus(String tbStatus) {
		this.tbStatus = tbStatus;
	}

	public String getIsTbOnline() {
		return isTbOnline;
	}

	public void setIsTbOnline(String isTbOnline) {
		this.isTbOnline = isTbOnline;
	}

	public String getIsMaped() {
		return isMaped;
	}

	public void setIsMaped(String isMaped) {
		this.isMaped = isMaped;
	}

	public Date getJhsPushDate() {
		return jhsPushDate;
	}

	public void setJhsPushDate(Date jhsPushDate) {
		this.jhsPushDate = jhsPushDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public ProdProduct getProdProduct() {
		return prodProduct;
	}

	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}

}
