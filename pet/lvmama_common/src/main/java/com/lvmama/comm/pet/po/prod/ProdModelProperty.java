package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;

/**
 * 模块属性
 * @author ganyingwen
 *
 */
public class ProdModelProperty implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -603633583827399033L;
	private Long id;
	/**
	 * 一级模块ID
	 */
	private Long firstModelId;
	/**
	 * 一级模块名称
	 */
	private String firstModelName;
	/**
	 * 二级模块ID
	 */
	private Long secondModelId;
	/**
	 * 二级模块名称
	 */
	private String secondModelName;
	/**
	 * 属性名称
	 */
	private String property;
	/**
	 * 拼音
	 */
	private String pingying;
	/**
	 * 词库
	 */
	private String thesaurus;
	/**
	 * 产品类型。该模板属性能被用于的产品类型
	 */
	private String productType;
	private String[] productTypes;
	/**
	 * 序号。用于前后台排序使用
	 */
	private Integer orderNum;
	/**
	 * 有效性
	 */
	private String isValid;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	
	private String isMaintain;
	private long seq;
	
	


	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getIsMaintain() {
		return isMaintain;
	}

	public void setIsMaintain(String isMaintain) {
		this.isMaintain = isMaintain;
	}
    /**
     * 获取所有自由行类型列表
     * @return
     */
	public List<CodeItem> getSubProductTypeList(){
		List<CodeItem> codeItemList = ProductUtil.getRouteSubTypeList();
		if (productTypes != null) {
			for (CodeItem ci : codeItemList) {			
				for (String type : productTypes) {
					if (ci.getCode().equals(type)) {
						ci.setChecked("true");
					}
				}
			}
		}
		return codeItemList;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFirstModelId() {
		return firstModelId;
	}
	public void setFirstModelId(Long firstModelId) {
		this.firstModelId = firstModelId;
	}
	public Long getSecondModelId() {
		return secondModelId;
	}
	public void setSecondModelId(Long secondModelId) {
		this.secondModelId = secondModelId;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getPingying() {
		return pingying;
	}
	public void setPingying(String pingying) {
		this.pingying = pingying;
	}
	public String getThesaurus() {
		return thesaurus;
	}
	public void setThesaurus(String thesaurus) {
		this.thesaurus = thesaurus;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getFirstModelName() {
		return firstModelName;
	}
	public void setFirstModelName(String firstModelName) {
		this.firstModelName = firstModelName;
	}
	public String getSecondModelName() {
		return secondModelName;
	}
	public void setSecondModelName(String secondModelName) {
		this.secondModelName = secondModelName;
	}
	public String[] getProductTypes() {
		if (StringUtils.isNotEmpty(productType)) {
			productTypes = productType.split(";");
		}
		return productTypes;
	}
	public void setProductTypes(String[] productTypes) {
		this.productTypes = productTypes;
	}
}
