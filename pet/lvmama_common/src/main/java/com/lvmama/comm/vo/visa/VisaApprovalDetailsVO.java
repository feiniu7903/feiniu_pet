package com.lvmama.comm.vo.visa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
 
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

public class VisaApprovalDetailsVO implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -8182426914927699125L;
	private Long visaApprovalId;
	private String name;
	private String occupation;
	private Long orderId;
	private List<String> title=new ArrayList<String>();
	private List<String> approvalStatus=new ArrayList<String>();
	private String titleToStatus;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOccupation() {
		return occupation;
	}
	public String getCnOccupation() {
		if(occupation!=null){
			return Constant.VISA_OCCUPATION.getCnName(occupation);
		}
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	 
	public Long getVisaApprovalId() {
		return visaApprovalId;
	}
	public void setVisaApprovalId(Long visaApprovalId) {
		this.visaApprovalId = visaApprovalId;
	}
	private    String[] getVerticalTitleToStatus() {
		this.title=new ArrayList<String>();
		this.approvalStatus=new ArrayList<String>();
		if(StringUtils.isNotBlank(titleToStatus)){
			   String[] list=  titleToStatus.split(",");
			   for(String str :list){
				   String[]  elment=str.split("~");
				   //表头
				   if(elment.length>=1&&elment[0].equals("<font color=\"red\">增补材料</font>")){
					   elment[0]="增补材料";
 				   }
				   this.title.add(elment.length>=1?elment[0]:"");
				    //表状态数据
				   if(elment.length>=2&&elment[1]!=null){
					   if(elment[1].equals("Y")){
						   this.approvalStatus.add("(√)");
					   }else if(elment[1].equals("N")){
						   this.approvalStatus.add("(×)");
					   }else {
						   this.approvalStatus.add(" ");
					   }
				   }else {
					   this.approvalStatus.add(" ");
				   }
				  
			   }
			   return list;
		   }else {
			   return null;
		   }
	}
	public List<String> getTitle() {
		getVerticalTitleToStatus();
		return title;
	}
	public List<String> getApprovalStatus() {
		getVerticalTitleToStatus();
		return approvalStatus;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
 
 
}
