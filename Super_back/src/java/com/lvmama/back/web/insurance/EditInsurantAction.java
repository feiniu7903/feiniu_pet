package com.lvmama.back.web.insurance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.InsurantService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.IdentityCardUtil;
import com.lvmama.comm.vo.Constant.CERTIFICATE_TYPE;
import com.lvmama.comm.vo.Constant.POLICY_PERSON;
import com.lvmama.comm.vo.Constant.SEX_CODE;

public class EditInsurantAction extends BaseAction {
	private static final long serialVersionUID = 6436042302901178695L;

	private InsurantService insurantService = (InsurantService) SpringBeanProxy.getBean("insurantService");
	
	private String insurantId;
	private String policyId;
	private String orderId;
	private String selectedSexCode = "M";
	private String selectedCertTypes = CERTIFICATE_TYPE.ID_CARD.name();
	
	private List<KeyValuePair> sexTypes = new ArrayList<KeyValuePair>();
	private List<KeyValuePair> certTypes = new ArrayList<KeyValuePair>();
	private InsInsurant insurant;
	
	@Override
	public void doBefore() {
		for (SEX_CODE sex : SEX_CODE.values()) {
			sexTypes.add(new KeyValuePair(sex.getCode(), sex.getChName()));
		}
		for (CERTIFICATE_TYPE type : CERTIFICATE_TYPE.values()) {
			certTypes.add(new KeyValuePair(type.name(), type.getCnName()));
		}
		if (null != insurantId) {
			insurant = insurantService.queryInsurantByPK(new Long(insurantId));
			if (null != insurant) {
				//selectedSexCode = null == insurant.getSex() ? "M" : insurant.getSex();
				//selectedCertTypes = null == insurant.getCertType() ? CERTIFICATE_TYPE.ID_CARD.name() : insurant.getCertType();
				
				
				//lancey 添加，对初始证件没有添加时给设置初始值
				if(StringUtils.isEmpty(insurant.getSex()))
				{
					insurant.setSex("M");
				}
				
				if(StringUtils.isEmpty(insurant.getCertType()))
				{
					insurant.setCertType(CERTIFICATE_TYPE.ID_CARD.name());
				}
				
				selectedSexCode=insurant.getSex();
				selectedCertTypes=insurant.getCertType();
			} 
		}
		if (null == insurant) {
			insurant = new InsInsurant();
			insurant.setInsurantId(-1l);
			insurant.setSex(selectedSexCode);
			insurant.setCertType(selectedCertTypes);
			insurant.setOrderId(new Long(orderId));
			insurant.setPolicyId(new Long(policyId));
			insurant.setPersonType(POLICY_PERSON.INSURANT.name());
		}
	}
	
	/**
	 * 更改性别
	 * @param value
	 */
	public void changeSex(String value) {
		selectedSexCode = value;
		insurant.setSex(selectedSexCode);
	}
	
	/**
	 * 更改证件类型
	 * @param value
	 */
	public void changeCertType(String value) {
		selectedCertTypes = value;
		insurant.setCertType(selectedCertTypes);
	}
	
	/**
	 * 更新投保人/被保险人
	 */
	public void update() {
		if (null == insurant || null == insurant.getInsurantId()) {
			ZkMessage.showError("无法找到需要更新的被保险人的原始信息!");
			return;
		}
		if (validateInsurant()) {			
			insurantService.update(insurant);
			this.refreshParent("refresh");
			this.closeWindow();
		}
		
	}
	
	/**
	 * 新增被保险人
	 */
	public void insert() {
		if (validateInsurant()) {
			insurantService.insert(insurant);
			this.refreshParent("refresh");
			this.closeWindow();
		}
	}

	private boolean validateInsurant() {
		if (null == insurant.getName() || "".equals(insurant.getName().trim())) {
			ZkMessage.showError("姓名不能为空!");
			return false;
		}
		if (null == insurant.getBirthday() || new java.util.Date().before(insurant.getBirthday())) {
			ZkMessage.showError("出生日期不能为空或者晚于等于今天");
			return false;
		}
		if (null == insurant.getMobileNumber() || "".equals(insurant.getMobileNumber().trim())) {
			ZkMessage.showError("联系电话不能为空");
			return false;
		}
		if (null == insurant.getCertNo() || "".equals(insurant.getCertNo().trim())) {
			ZkMessage.showError("证件号码不能为空");
			return false;
		}
		if (CERTIFICATE_TYPE.ID_CARD.name().equalsIgnoreCase(insurant.getCertType()) && !IdentityCardUtil.verify(insurant.getCertNo())) {
			ZkMessage.showError("身份证号码不合法");
			return false;
		}
		return true;
	}
	
	public String getInsurantId() {
		return insurantId;
	}

	public void setInsurantId(String insurantId) {
		this.insurantId = insurantId;
	}

	public InsInsurant getInsurant() {
		return insurant;
	}
	
	public List<KeyValuePair> getSexTypes() {
		return sexTypes;
	}

	public List<KeyValuePair> getCertTypes() {
		return certTypes;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}	
	
}
