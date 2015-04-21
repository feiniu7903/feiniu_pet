package com.lvmama.comm.pet.vo.mark;

import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.utils.StringUtil;


public class MarkChannelVO extends MarkChannel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4738600084952874866L;
	private Long  firstId;
	private String firstName;
	private String firstCode;
	private Long   secondId;
	private String secondName;
	private String secondCode;
	private String threeName;
	private Long   threeId;
	private String threeCode;
	private String uniqueId;//供zk页面当中使用
	

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstCode() {
		return firstCode;
	}
	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getSecondCode() {
		return secondCode;
	}
	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}
	public String getThreeName() {
		return threeName;
	}
	public void setThreeName(String threeName) {
		this.threeName = threeName;
	}
	public String getThreeCode() {
		return threeCode;
	}
	public void setThreeCode(String threeCode) {
		this.threeCode = threeCode;
	}

	public Long getFirstId() {
		return firstId;
	}
	public void setFirstId(Long firstId) {
		this.firstId = firstId;
	}
	public Long getSecondId() {
		return secondId;
	}
	public void setSecondId(Long secondId) {
		this.secondId = secondId;
	}
	public Long getThreeId() {
		return threeId;
	}
	public void setThreeId(Long threeId) {
		this.threeId = threeId;
	}

	public String getUniqueId(){
		if(uniqueId==null){
			uniqueId=getFirstId()+"_"+getSecondCode()+"_"+getThreeCode()+"_"+getCreateTime().getTime()+StringUtil.getRandomString(10000, 3);			
		}
		return uniqueId;
	}
}
