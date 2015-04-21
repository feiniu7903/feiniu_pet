/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.lvmama.pet.vo;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

/**
 * 调用的错误信息
 * 
 * @author jun.huyj
 * @version $Id: ErrorCode.java, v 0.1 Nov 12, 2008 2:52:08 PM jun.huyj Exp $
 */
@XObject("err")
public class AlipayWAPErrorCode {
    /**
     * 主错误码
     */
    @XNode("code")
    private String code;

    /**
     * 子错误码
     */
    @XNode("sub_code")
    private String subCode;

    /**
     * 错误信息
     */
    @XNode("msg")
    private String msg;

    /**
     * 错误详情
     */
    @XNode("detail")
    private String detail;

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return Returns the subCode.
     */
    public String getSubCode() {
        return subCode;
    }

    /**
     * @param subCode The subCode to set.
     */
    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    /**
     * @return Returns the msg.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg The msg to set.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return Returns the detail.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail The detail to set.
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

}
