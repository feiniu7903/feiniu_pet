package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class EbkOrderDataRev implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8322794454221575478L;

	private Long dataId;

    private Long ebkCertificateItemId;

    private Long ebkCertificateId;

    private Date createTime;

    private String value;
    
    private String dataType;

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * 判断是否是凭证
	 * @return
	 */
	public boolean hasCertificate(){
		return Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE.name().equals(dataType);
	}
	public boolean hasCertificateItem(){
		return Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM.name().equals(dataType);
	}
	public boolean hasCertificateItemDay(){
		return Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM_DAY.name().equals(dataType);
	}
	public boolean hasPerson(){
		return Constant.EBK_CERT_OBJ_TYPE.ORD_PERSON.name().equals(dataType);
	}
}