package com.lvmama.comm.pet.po.fin;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant.FILIALE_NAME;
import com.lvmama.comm.vo.Constant.FIN_GL_ACCOUNT_TYPE;
import com.lvmama.comm.vo.Constant.PRODUCT_TYPE;
import com.lvmama.comm.vo.Constant.RECON_GW_TYPE;
import com.lvmama.comm.vo.Constant.REGION_NAMES;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;

/**
 * 数据对象
 * @since 2014-03-10
 * @author taiqichao
 */
public class FinGlSubjectCfg implements Serializable {

    private static final long serialVersionUID = 139444247156389668L;

    /**
     * column FIN_GL_SUBJECT_CFG.SUBJECT_CONFIG_ID
     */
    private Long subjectConfigId;

    /**
     * column FIN_GL_SUBJECT_CFG.CONFIG1
     */
    private String config1;

    /**
     * column FIN_GL_SUBJECT_CFG.CONFIG2
     */
    private String config2;

    /**
     * column FIN_GL_SUBJECT_CFG.CONFIG3
     */
    private String config3;

    /**
     * column FIN_GL_SUBJECT_CFG.CONFIG4
     */
    private String config4;

    /**
     * column FIN_GL_SUBJECT_CFG.CONFIG5
     */
    private String config5;

    /**
     * column FIN_GL_SUBJECT_CFG.CONFIG6
     */
    private String config6;

    /**
     * column FIN_GL_SUBJECT_CFG.CONFIG7
     */
    private String config7;

    /**
     * column FIN_GL_SUBJECT_CFG.B_SUBJECT_CODE
     */
    private String borrowSubjectCode;

    /**
     * column FIN_GL_SUBJECT_CFG.L_CONFIG1
     */
    private String lendConfig1;

    /**
     * column FIN_GL_SUBJECT_CFG.L_CONFIG2
     */
    private String lendConfig2;

    /**
     * column FIN_GL_SUBJECT_CFG.L_CONFIG3
     */
    private String lendConfig3;

    /**
     * column FIN_GL_SUBJECT_CFG.L_CONFIG4
     */
    private String lendConfig4;

    /**
     * column FIN_GL_SUBJECT_CFG.L_CONFIG5
     */
    private String lendConfig5;

    /**
     * column FIN_GL_SUBJECT_CFG.L_CONFIG6
     */
    private String lendConfig6;

    /**
     * column FIN_GL_SUBJECT_CFG.L_CONFIG7
     */
    private String lendConfig7;

    /**
     * column FIN_GL_SUBJECT_CFG.L_SUBJECT_CODE
     */
    private String lendSubjectCode;

    /**
     * column FIN_GL_SUBJECT_CFG.ACCOUNT_TYPE
     */
    private String accountType;

    public FinGlSubjectCfg() {
        super();
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.SUBJECT_CONFIG_ID
     */
    public Long getSubjectConfigId() {
        return subjectConfigId;
    }

    /**
     * setter for Column FIN_GL_SUBJECT_CFG.SUBJECT_CONFIG_ID
     * @param subjectConfigId
     */
    public void setSubjectConfigId(Long subjectConfigId) {
        this.subjectConfigId = subjectConfigId;
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.CONFIG1
     */
    public String getConfig1() {
        return config1;
    }
    
    public String getConfig1Cn() {
    	return PRODUCT_TYPE.getCnName(config1);
    }

    /**
     * setter for Column FIN_GL_SUBJECT_CFG.CONFIG1
     * @param config1
     */
    public void setConfig1(String config1) {
        this.config1 = config1;
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.CONFIG2
     */
    public String getConfig2() {
        return config2;
    }
    
    
    public String getConfig2Cn() {
        return SUB_PRODUCT_TYPE.getCnName(config2);
    }

    /**
     * setter for Column FIN_GL_SUBJECT_CFG.CONFIG2
     * @param config2
     */
    public void setConfig2(String config2) {
        this.config2 = config2;
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.CONFIG3
     */
    public String getConfig3() {
        return config3;
    }
    
    /**
     * setter for Column FIN_GL_SUBJECT_CFG.CONFIG3
     * @param config3
     */
    public void setConfig3(String config3) {
        this.config3 = config3;
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.CONFIG4
     */
    public String getConfig4() {
        return config4;
    }

    /**
     * setter for Column FIN_GL_SUBJECT_CFG.CONFIG4
     * @param config4
     */
    public void setConfig4(String config4) {
        this.config4 = config4;
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.CONFIG5
     */
    public String getConfig5() {
        return config5;
    }
    
    public String getConfig5Cn(){
    	return REGION_NAMES.getCnName(config5);
    }

    /**
     * setter for Column FIN_GL_SUBJECT_CFG.CONFIG5
     * @param config5
     */
    public void setConfig5(String config5) {
        this.config5 = config5;
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.CONFIG6
     */
    public String getConfig6() {
        return config6;
    }
    
    public String getConfig6Cn(){
    	return RECON_GW_TYPE.getCnName(config6);
    }
    /**
     * setter for Column FIN_GL_SUBJECT_CFG.CONFIG6
     * @param config6
     */
    public void setConfig6(String config6) {
        this.config6 = config6;
    }

    /**
     * getter for Column FIN_GL_SUBJECT_CFG.CONFIG7
     */
    public String getConfig7() {
        return config7;
    }
    
    public String getConfig7Cn(){
    	return FILIALE_NAME.getCnName(config7);
    }

    /**
     * setter for Column FIN_GL_SUBJECT_CFG.CONFIG7
     * @param config7
     */
    public void setConfig7(String config7) {
        this.config7 = config7;
    }

	public String getBorrowSubjectCode() {
		return borrowSubjectCode;
	}

	public void setBorrowSubjectCode(String borrowSubjectCode) {
		this.borrowSubjectCode = borrowSubjectCode;
	}

	public String getLendConfig1() {
		return lendConfig1;
	}
	
	public String getLendConfig1Cn() {
    	return PRODUCT_TYPE.getCnName(lendConfig1);
    }

	public void setLendConfig1(String lendConfig1) {
		this.lendConfig1 = lendConfig1;
	}

	public String getLendConfig2() {
		return lendConfig2;
	}
	
	public String getLendConfig2Cn() {
        return SUB_PRODUCT_TYPE.getCnName(lendConfig2);
    }

	public void setLendConfig2(String lendConfig2) {
		this.lendConfig2 = lendConfig2;
	}

	public String getLendConfig3() {
		return lendConfig3;
	}

	public void setLendConfig3(String lendConfig3) {
		this.lendConfig3 = lendConfig3;
	}

	public String getLendConfig4() {
		return lendConfig4;
	}

	public void setLendConfig4(String lendConfig4) {
		this.lendConfig4 = lendConfig4;
	}

	public String getLendConfig5() {
		return lendConfig5;
	}

	public void setLendConfig5(String lendConfig5) {
		this.lendConfig5 = lendConfig5;
	}
	
	public String getLendConfig5Cn(){
    	return REGION_NAMES.getCnName(lendConfig5);
    }

	public String getLendConfig6() {
		return lendConfig6;
	}
	
	public String getLendConfig6Cn(){
    	return RECON_GW_TYPE.getCnName(lendConfig6);
    }

	public void setLendConfig6(String lendConfig6) {
		this.lendConfig6 = lendConfig6;
	}

	public String getLendConfig7() {
		return lendConfig7;
	}

	public void setLendConfig7(String lendConfig7) {
		this.lendConfig7 = lendConfig7;
	}
	
	public String getLendConfig7Cn(){
    	return FILIALE_NAME.getCnName(lendConfig7);
    }

	public String getLendSubjectCode() {
		return lendSubjectCode;
	}

	public void setLendSubjectCode(String lendSubjectCode) {
		this.lendSubjectCode = lendSubjectCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
    
	public String getAccountTypeCn() {
		return FIN_GL_ACCOUNT_TYPE.getCnName(accountType);
	}


}