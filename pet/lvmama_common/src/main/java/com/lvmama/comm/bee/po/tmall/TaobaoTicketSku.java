package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;

public class TaobaoTicketSku implements Serializable {
    private static final long serialVersionUID = 1088560559813191858L;

    private Long id;
    private Long tbProdSyncId;
    private String tbOuterId;
    private String tbPidVid;
    private Long prodBranchId;
    private String isSync;
    private Long productId;
    private String tbVidName;
    private String tbTicketType;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTbProdSyncId() {
        return tbProdSyncId;
    }
    public void setTbProdSyncId(Long tbProdSyncId) {
        this.tbProdSyncId = tbProdSyncId;
    }
    public String getTbOuterId() {
        return tbOuterId;
    }
    public void setTbOuterId(String tbOuterId) {
        this.tbOuterId = tbOuterId;
    }
    public String getTbPidVid() {
        return tbPidVid;
    }
    public void setTbPidVid(String tbPidVid) {
        this.tbPidVid = tbPidVid;
    }
    public Long getProdBranchId() {
        return prodBranchId;
    }
    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }
    public String getIsSync() {
        return isSync;
    }
    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTbVidName() {
        return tbVidName;
    }

    public void setTbVidName(String tbVidName) {
        this.tbVidName = tbVidName;
    }

    public String getTbTicketType() {
        return tbTicketType;
    }

    public void setTbTicketType(String tbTicketType) {
        this.tbTicketType = tbTicketType;
    }
}
