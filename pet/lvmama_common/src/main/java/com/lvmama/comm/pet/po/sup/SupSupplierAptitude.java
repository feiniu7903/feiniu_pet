package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.EnumUtils;

import com.lvmama.comm.vo.Constant;



/**
 * 供应商资质
 * @author yuzhibing
 *
 */
public class SupSupplierAptitude implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2221828423597812319L;

	private Long supplierAptitudeId;

    private Long supplierId;

    private String aptitudeAudit;

    private Date aptitudeEndTime;
    
    private Long businessLicenceFile;
    private Long organizationFile;
    private Long operationPermissionFile;
    private Long insuranceFile;
    private Long taxRegistrationFile;
    
    
    private Date createTime;
    
    public Long getSupplierAptitudeId() {
        return supplierAptitudeId;
    }

    public void setSupplierAptitudeId(Long supplierAptitudeId) {
        this.supplierAptitudeId = supplierAptitudeId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getAptitudeAudit() {
        return aptitudeAudit;
    }

    public void setAptitudeAudit(String aptitudeAudit) {
        this.aptitudeAudit = aptitudeAudit == null ? null : aptitudeAudit.trim();
    }

    public Date getAptitudeEndTime() {
        return aptitudeEndTime;
    }

    public void setAptitudeEndTime(Date aptitudeEndTime) {
        this.aptitudeEndTime = aptitudeEndTime;
    }

	public Long getBusinessLicenceFile() {
		return businessLicenceFile;
	}

	public void setBusinessLicenceFile(Long businessLicenceFile) {
		this.businessLicenceFile = businessLicenceFile;
	}

	public Long getOrganizationFile() {
		return organizationFile;
	}

	public void setOrganizationFile(Long organizationFile) {
		this.organizationFile = organizationFile;
	}

	public Long getOperationPermissionFile() {
		return operationPermissionFile;
	}

	public void setOperationPermissionFile(Long operationPermissionFile) {
		this.operationPermissionFile = operationPermissionFile;
	}

	public Long getInsuranceFile() {
		return insuranceFile;
	}

	public void setInsuranceFile(Long insuranceFile) {
		this.insuranceFile = insuranceFile;
	}

	public Long getTaxRegistrationFile() {
		return taxRegistrationFile;
	}

	public void setTaxRegistrationFile(Long taxRegistrationFile) {
		this.taxRegistrationFile = taxRegistrationFile;
	}
	
	public boolean isNotEmptyFile(String code){
		Constant.SUP_APTITUDE_TYPE type=EnumUtils.getEnum(Constant.SUP_APTITUDE_TYPE.class, code);
		boolean flag=false;
		if(type!=null){
			switch (type) {
			case BUSINESS_LICENCE:
				flag=businessLicenceFile!=null;
				break;
			case INSURANCE:
				flag=insuranceFile!=null;
				break;
			case OPERATION_PERMISSION:
				flag=insuranceFile!=null;
				break;
			case ORGANIZATION:
				flag=organizationFile!=null;
				break;
			case TAX_REGISTRATION:
				flag=taxRegistrationFile!=null;
				break;
			}
		}
		return flag;
	}
	
	public Long getFileId(String code){
		Constant.SUP_APTITUDE_TYPE type=EnumUtils.getEnum(Constant.SUP_APTITUDE_TYPE.class, code);
		Long pid=0L;
		if(type!=null){
			switch (type) {
			case BUSINESS_LICENCE:
				pid=businessLicenceFile;
				break;
			case INSURANCE:
				pid=insuranceFile;
				break;
			case OPERATION_PERMISSION:
				pid=insuranceFile;
				break;
			case ORGANIZATION:
				pid=organizationFile;
				break;
			case TAX_REGISTRATION:
				pid=taxRegistrationFile;
				break;
			}
		}
		return pid;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getZhAptitudeAudit() {
		return Constant.SUP_APTITUDE_STATUS_TYPE.getCnName(aptitudeAudit);
	}
}