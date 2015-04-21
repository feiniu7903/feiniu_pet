package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
public class DistributionTuanDestroyLog implements Serializable {
	private static final long serialVersionUID = 8681543286181511628L;
	private Long logId;
	private String fileName;
	private String creator;
	private Date uploadTime;
	private Date startTime;
	private Date endTime;
	private Long sumNum;
	private Long successNum;
	private String initFileName;
	private String couponCode;//券码号
	private String errorReson;//失败原因
	
	private String distType;
	private String status;
	
	private Long  pristineId;
	private Long errorId;
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}	
	
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getCnEndTime(){
		if(this.endTime!=null){
			return DateUtil.getFormatDate(this.endTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public String getCnStartTime(){
		if(this.startTime!=null){
			return DateUtil.getFormatDate(this.startTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getSumNum() {
		return sumNum;
	}
	public void setSumNum(Long sumNum) {
		this.sumNum = sumNum;
	}
	public Long getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Long successNum) {
		this.successNum = successNum;
	}
	
	public void setInitFileName(String initFileName) {
		this.initFileName = initFileName;
	}
	public String getInitFileName() {
		if(this.fileName!=null){
			String initFileNameStr=fileName.substring(0,fileName.indexOf("_"))+fileName.substring(fileName.indexOf("."));
			return initFileNameStr;
		}
		return "";
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getErrorReson() {
		return errorReson;
	}
	public void setErrorReson(String errorReson) {
		this.errorReson = errorReson;
	}
	public Long getPristineId() {
		return pristineId;
	}
	public void setPristineId(Long pristineId) {
		this.pristineId = pristineId;
	}
	public Long getErrorId() {
		return errorId;
	}
	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}
	public String getDistType() {
		return distType;
	}
	public void setDistType(String distType) {
		this.distType = distType;
	}
	public String getStatus() {
		return status;
	}
	public String getCnStatus() {
		if(StringUtil.isNotEmptyString(status)){
			return Constant.DISTRIBUTION_TUAN_DESTROY_LOG_STATUS.getCnName(status);
		}
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
