package com.lvmama.comm.pet.po.info;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoQuesCount implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -470487682809056993L;

	private Long id;

    private Long typeId;

    private String tittle;
    
    private String typeName;
    
    private String userName;

    private String content;

    private Date createTime;

    private String productName;

    private String prodType;

    private Long productId;
    
    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH");
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle == null ? null : tittle.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType == null ? null : prodType.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCreateTimeStr(){
		if(this.createTime!=null){
			return sf1.format(this.createTime);
		}
		return "";
	}
}