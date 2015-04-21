package com.lvmama.distribution.service.impl;


public interface DistributionPushService {
    static final String CONTENT_XML = "xml";
    static final String CONTENT_JSON = "json";
    static final String SUBMIT_POST = "post";
    static final String SUBMIT_GET = "get";

    /**
     * 推送类型
     * @author gaoxin
     */
    public enum EVENT_TYPE{
        /** 推送产品 */
        product,
        /** 推送价格 */
        price,
        /** 推送下线 */
        offLine,
        /** 取消订单 */
        cancelorder,
        /** 淘宝--产品信息修改 */
        tb_product_change,
        /** 淘宝--产品上下线 */
        tb_product_onoff,
        /** 淘宝--产品类别上下线 */
        tb_product_branch_onoff,
        /** 淘宝--修改时间价格 */
        tb_change_timeprice
    }

    /**
     * 消息类型
     * @author gaoxin
     *
     */
    public enum OBJECT_TYPE{
        /** 产品 */
        product,
        /** 订单 */
        order,
        /** 淘宝--产品 */
        tb_product
    }


    /**
     * 推送状态
     * @author gaoxin
     *
     */
    public enum PUSH_STATUS{
        /** 未推送 */
        unpushed,
        /** 待重试 */
        pushing,
        /** 已推送 */
        pushed,
        /** 失败 */
        fail
    }
    public enum PUSH_TYPE{
        product,
        offLine,
        price,
        order
    }

    void push(String partnerCode, String body, String signed,String eventType);
}
