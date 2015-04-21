package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-5-4<p/>
 * Time: 上午11:21<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ProdAvailableBonus implements Serializable {

    private static final long serialVersionUID = 3900423083920645367L;

    /**
     * 主键
     */
    private Long availableId;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 类型等级
     */
    private String typeLevel;
    /**
     *每单可用奖金额度
     */
    protected Float amount;

    public Long getAvailableId() {
        return availableId;
    }

    public void setAvailableId(Long availableId) {
        this.availableId = availableId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(String typeLevel) {
        this.typeLevel = typeLevel;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
