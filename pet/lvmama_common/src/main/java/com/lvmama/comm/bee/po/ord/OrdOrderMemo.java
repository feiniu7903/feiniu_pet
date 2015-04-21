package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class OrdOrderMemo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1830325134034160474L;

	private String type;
	
    private Long memoId;

    private Long orderId;

    private String content;
    
    private String uuid;
    
    private String operatorName;
    
    private Date createTime;
    
    /**
     * 是否用户备注(EBK中显示),显示(true),不显示(false)
     */
    private String userMemo;
    /**
     * (true/false)处理/未处理
     */
    private String status;
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getMemoId() {
        return memoId;
    }

    public void setMemoId(Long memoId) {
        this.memoId = memoId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getZhType() {
		return Constant.ORD_MEMO_TYPE.getCnName(type);
	}
	
	public String getZhCreateTime() {
		return DateUtil.getFormatDate(createTime, "yyyy-MM-dd HH:mm:SS");
	}

	/**
	 * 设置是否用户备注(EBK中显示)
	 * @param userMemo 显示(true),不显示(false)
	 */
	public void setUserMemo(String userMemo) {
		if(StringUtils.isBlank(userMemo)){
			this.userMemo="false";
		}else{
			this.userMemo=userMemo;
		}
	}
	
	/**
	 * 是否有用户备注
	 * @return
	 */
	public String getUserMemo(){
		if(StringUtils.isBlank(userMemo)){
			return "false";
		}else{
			return this.userMemo;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public boolean hasUserMemoApprove(){
		return "false".equals(this.status);
	}
	public boolean hasUserMemo(){
		return "true".equals(userMemo);
	}
	
}