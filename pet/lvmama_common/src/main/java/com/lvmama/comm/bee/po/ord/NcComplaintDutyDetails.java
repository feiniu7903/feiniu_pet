package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-6<p/>
 * Time: 上午10:47<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintDutyDetails implements Serializable {

    private static final long serialVersionUID = -7680788849493980151L;
    /**
     * 主键
     */
    private Long dutyDetailsId;
    /**
     * 责任认定主表ID
     */
    private Long dutyId;
    /**
     * 责任主体（供应商/部门/员工）
     */
    private String dutyMain;
    /**
     * 名称
     */
    private String mainName;
    /**
     * 名称
     */
    private String amount;

    public Long getDutyDetailsId() {
        return dutyDetailsId;
    }

    public void setDutyDetailsId(Long dutyDetailsId) {
        this.dutyDetailsId = dutyDetailsId;
    }



    public String getDutyMain() {
        return dutyMain;
    }

    public void setDutyMain(String dutyMain) {
        this.dutyMain = dutyMain;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public String toString() {
        return "NcComplaintDutyDetails{" +
                "dutyDetailsId=" + dutyDetailsId +
                ", dutyId=" + dutyId +
                ", dutyMain='" + dutyMain + '\'' +
                ", mainName='" + mainName + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
