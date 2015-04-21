package com.lvmama.back.sweb.insurance;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.web.utils.insurance.alianz.AlianzGetPolicy;
import com.lvmama.back.web.utils.insurance.alianz.AlianzPolicyRecord;
import com.lvmama.back.web.utils.insurance.alianz.AllianzPolicyLocator;
import com.lvmama.back.web.utils.insurance.alianz.AllianzPolicySoap12Stub;

/**
 * 
 * 
 * @author Brian
 * 
 */
public class AlianzPolicyAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AlianzPolicyAction.class);

	private String batchNo;
	private String firstName;
	private String lastName;
	private String gender;
	private String effDate;
	private String idNumber;
	private String mobile;

	@Action("/ins/alianzGetPolicy")
	public String getPolicy() {
		try {
			AllianzPolicySoap12Stub binding = (AllianzPolicySoap12Stub) new AllianzPolicyLocator()
					.getAllianzPolicySoap12();
			AlianzGetPolicy getPolicy = new AlianzGetPolicy(batchNo,
					new AlianzPolicyRecord("ASCY_3", effDate, firstName, lastName,
							gender,idNumber, mobile), "lvmama");
			//System.out.println(getPolicy.toXML());
			logger.info(binding.getPolicy(getPolicy.toXML()));
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
		} catch (Exception re) {
			re.printStackTrace();
		}
		return null;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}
