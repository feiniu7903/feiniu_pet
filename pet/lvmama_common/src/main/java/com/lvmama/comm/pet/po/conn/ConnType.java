package com.lvmama.comm.pet.po.conn;

import java.util.Date;

public class ConnType implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6908912032212110381L;

	private Long callTypeId;

    private String callTypeCode;

    private String callTypeName;

    private String valid;

    private Date createTime;

    private Long layer;

    private Long parentId;

    private Long seq;

    private String callback;

    public Long getCallTypeId() {
        return callTypeId;
    }

    public void setCallTypeId(Long callTypeId) {
        this.callTypeId = callTypeId;
    }

    public String getCallTypeCode() {
        return callTypeCode;
    }

    public void setCallTypeCode(String callTypeCode) {
        this.callTypeCode = callTypeCode == null ? null : callTypeCode.trim();
    }

    public String getCallTypeName() {
        return callTypeName;
    }

    public void setCallTypeName(String callTypeName) {
        this.callTypeName = callTypeName == null ? null : callTypeName.trim();
    }

    public String getValid() {
        return valid;
    }

    public void setIsValid(String valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getLayer() {
        return layer;
    }

    public void setLayer(Long layer) {
        this.layer = layer;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}