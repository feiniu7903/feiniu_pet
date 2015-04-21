package com.lvmama.comm.pet.po.fin;


/**
 * 科目配置
 * 
 * @author taiqichao
 * 
 */
public class FinGLSubjectConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long subjectConfigId;
	
	/**
	 * 配置项1
	 */
	private String config1;
	
	/**
	 * 配置项2
	 */
	private String config2;
	
	/**
	 * 配置项3
	 */
	private String config3;
	
	/**
	 * 配置项4
	 */
	private String config4;
	
	/**
	 * 配置项5
	 */
	private String config5;
	
	/**
	 * 配置项6
	 */
	private String config6;
	
	/**
	 * 配置项7
	 */
	private String config7;
	
	/**
	 * 科目CODE
	 */
	private String subjectCode;
	
	/**
	 * 科目类别
	 * @see com.lvmama.comm.vo.Constant.FIN_SUBJECT_TYPE
	 */
	private String subjectType;
	

	public FinGLSubjectConfig() {
	}


	public Long getSubjectConfigId() {
		return subjectConfigId;
	}


	public void setSubjectConfigId(Long subjectConfigId) {
		this.subjectConfigId = subjectConfigId;
	}


	public String getConfig1() {
		return config1;
	}


	public void setConfig1(String config1) {
		this.config1 = config1;
	}


	public String getConfig2() {
		return config2;
	}


	public void setConfig2(String config2) {
		this.config2 = config2;
	}


	public String getConfig3() {
		return config3;
	}


	public void setConfig3(String config3) {
		this.config3 = config3;
	}


	public String getConfig4() {
		return config4;
	}


	public void setConfig4(String config4) {
		this.config4 = config4;
	}


	public String getConfig5() {
		return config5;
	}


	public void setConfig5(String config5) {
		this.config5 = config5;
	}
	

	public String getConfig6() {
		return config6;
	}


	public void setConfig6(String config6) {
		this.config6 = config6;
	}


	public String getConfig7() {
		return config7;
	}


	public void setConfig7(String config7) {
		this.config7 = config7;
	}


	public String getSubjectCode() {
		return subjectCode;
	}


	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}


	public String getSubjectType() {
		return subjectType;
	}


	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}


	@Override
	public String toString() {
		return "FinGLSubjectConfig [subjectConfigId=" + subjectConfigId
				+ ", config1=" + config1 + ", config2=" + config2
				+ ", config3=" + config3 + ", config4=" + config4
				+ ", config5=" + config5 + ", config6=" + config6
				+ ", config7=" + config7 + ", subjectCode=" + subjectCode
				+ ", subjectType=" + subjectType + "]";
	}
}
