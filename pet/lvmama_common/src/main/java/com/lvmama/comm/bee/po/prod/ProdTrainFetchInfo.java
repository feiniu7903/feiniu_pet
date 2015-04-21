package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

public class ProdTrainFetchInfo implements Serializable {
	
	public static enum STATUS{
		CREATE,
		COMPLETE;
	}
	
    private Long prodTrainFetchInfoId;

    private String fetchCatalog;

    private String fetchKey;

    private Date visitTime;

    private String fetchStatus=STATUS.CREATE.name();

    private Date createTime;
    
    /**
     * 更新请求描述
     */
    private String operInfo;

	public Long getProdTrainFetchInfoId() {
        return prodTrainFetchInfoId;
    }

    public void setProdTrainFetchInfoId(Long prodTrainFetchInfoId) {
        this.prodTrainFetchInfoId = prodTrainFetchInfoId;
    }

    public String getFetchCatalog() {
        return fetchCatalog;
    }

    public void setFetchCatalog(String fetchCatalog) {
        this.fetchCatalog = fetchCatalog == null ? null : fetchCatalog.trim();
    }

    public String getFetchKey() {
        return fetchKey;
    }

    public void setFetchKey(String fetchKey) {
        this.fetchKey = fetchKey == null ? null : fetchKey.trim();
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getFetchStatus() {
        return fetchStatus;
    }

    public void setFetchStatus(String fetchStatus) {
        this.fetchStatus = fetchStatus == null ? null : fetchStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getOperInfo() {
		return operInfo;
	}
	public void setOperInfo(String operInfo) {
		this.operInfo = operInfo;
	}
}