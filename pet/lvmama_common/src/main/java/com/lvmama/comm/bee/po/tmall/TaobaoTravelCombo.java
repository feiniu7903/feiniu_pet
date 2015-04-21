package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;

public class TaobaoTravelCombo implements Serializable {
    private static final long serialVersionUID = 2571631277491926371L;

    private Long id;
    private Long tbProdSyncId;
    private Long tbPid;
    private Long tbVid;
    private String tbComboName;
    private String isSync;
    private Long productId;

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
    public Long getTbPid() {
        return tbPid;
    }
    public void setTbPid(Long tbPid) {
        this.tbPid = tbPid;
    }
    public Long getTbVid() {
        return tbVid;
    }
    public void setTbVid(Long tbVid) {
        this.tbVid = tbVid;
    }
    public String getTbComboName() {
        return tbComboName;
    }
    public void setTbComboName(String tbComboName) {
        this.tbComboName = tbComboName;
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
}
