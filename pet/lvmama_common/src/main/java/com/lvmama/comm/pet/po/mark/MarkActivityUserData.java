package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-5-20<p/>
 * Time: 下午5:25<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityUserData implements Serializable {
    /**
     * 邮箱
     */
    private String email;
    /**
     * GUID
     */
    private String guid;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 注册日期
     */
    private String registerDate;
    /**
     * 积分
     */
    private String integral;
    /**
     * 奖金
     */
    private String bonus;
    /**
     * 会员等级
     */
    private String grade;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 优惠券金额
     */
    private String couponAmount;
    /**
     * 产品价格
     */
    private String productPrice;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 优惠券到期时间（小于等于60天到期的优惠券）
     */
    private String couponEndTime;
    /**
     * 产品返现价格
     */
    private String productCashRefund;
    /**
     * 积分到期时间
     */
    private String integralEndTime;
    /**
     * 奖金有效期
     */
    private String bonusEndTime;
    /**
     * 产品销售数量
     */
    private String productSaleAmount;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getProductCashRefund() {
        return productCashRefund;
    }

    public void setProductCashRefund(String productCashRefund) {
        this.productCashRefund = productCashRefund;
    }

    public String getIntegralEndTime() {
        return integralEndTime;
    }

    public void setIntegralEndTime(String integralEndTime) {
        this.integralEndTime = integralEndTime;
    }

    public String getBonusEndTime() {
        return bonusEndTime;
    }

    public void setBonusEndTime(String bonusEndTime) {
        this.bonusEndTime = bonusEndTime;
    }

    public String getProductSaleAmount() {
        return productSaleAmount;
    }

    public void setProductSaleAmount(String productSaleAmount) {
        this.productSaleAmount = productSaleAmount;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkActivityUserData that = (MarkActivityUserData) o;

        if (eqs(email, that.email) || eqs(mobile, that.mobile) || eqs(userId, that.userId)) {
            return true;
        }

        return false;
    }

    private boolean eqs(String obj1, String obj2) {

        if (obj1 != null) {
            return obj1.equals(obj2);
        }
        if (obj2 != null) {
            return obj2.equals(obj1);
        }
        return false;
    }

    /**
     * 顺序对应于 getColumnNames()
     *
     * @return
     */
    public String getColumnValues() {
        return email
                + "," + userId
                + "," + mobile
                + "," + registerDate
                + "," + integral
                + "," + bonus
                + "," + grade
                + "," + birthday
                + "," + couponAmount
                + "," + productPrice
                + "," + productName
                + "," + couponEndTime
                + "," + productCashRefund
                + "," + integralEndTime
                + "," + bonusEndTime
                + "," + productSaleAmount
                ;
    }

    /**
     * 顺序对应于 getColumnValues()
     *
     * @return
     */
    public static String getColumnNames() {
        return "email," +
                "customer_id," +
                "mobile," +
                "createdate," +
                "integral," +
                "bonusamount," +
                "grade," +
                "birthday," +
                "couponamount," +
                "productprice," +
                "productname," +
                "couponendtime," +
                "productcashrefund," +
                "integralendtime," +
                "bonusendtime," +
                "productsaleamount" ;
    }
}
