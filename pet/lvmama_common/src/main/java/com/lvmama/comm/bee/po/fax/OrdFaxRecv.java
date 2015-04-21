package com.lvmama.comm.bee.po.fax;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 传真回传.
 */
public class OrdFaxRecv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1811840535624874741L;
	//主键.
    private Long ordFaxRecvId;
    //主叫号码,即供应商此次回传所使用的电话.
    private String callerId;
    //回传接收到的时间.
    private Date recvTime;
    //回传件的文件路径.
    private String fileUrl;
    //创建时间.
    private Date createTime;
    //二维码.
    private String barcode;
    //回传状态. 对应com.lvmama.common.vo.Constant.FAX_RECV_STATUS中定义的枚举值.
    private String recvStatus;
    //回传记录是否有效
    private String valid;

    //private String zhRecvStatus;
    private String pageNum;
    
    private String operatorName;
    
    private List<OrdFaxRecvLink> linkOrderList;

    public Long getOrdFaxRecvId() {
        return ordFaxRecvId;
    }

    public void setOrdFaxRecvId(Long ordFaxRecvId) {
        this.ordFaxRecvId = ordFaxRecvId;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public List<OrdFaxRecvLink> getLinkOrderList() {
		return linkOrderList;
	}

	public void setLinkOrderList(List<OrdFaxRecvLink> linkOrderList) {
		this.linkOrderList = linkOrderList;
	}
    
	public String isLineToOrder(){
		if(linkOrderList.size()>0){
			return "已关联";
		}
		return "未关联";
	}
	
	public String getFullUrl(){
		return Constant.getInstance().getFaxRecv()+fileUrl;
	}
	
	public String getFileName(){
		return fileUrl.substring(fileUrl.lastIndexOf("/")+1);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getRecvStatus() {
		return recvStatus;
	}

	public void setRecvStatus(String recvStatus) {
		this.recvStatus = recvStatus;
	}

	public String getZhRecvStatus() {
		return 	Constant.FAX_RECV_STATUS.getCnName(recvStatus);
	}
	
	public String getZhRecvTime() {
		if(recvTime == null) {
			return "";
		}
		return DateUtil.formatDate(recvTime, "yyyy-MM-dd HH:mm:ss");
	}
	/*
	public void setZhRecvStatus(String zhRecvStatus) {
		this.zhRecvStatus = zhRecvStatus;
	}
	*/

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public List<String> getImageList(){
		if(StringUtils.isEmpty(pageNum)){
			return Collections.emptyList();
		}else{
			return Arrays.asList(pageNum.split(","));
		}
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}