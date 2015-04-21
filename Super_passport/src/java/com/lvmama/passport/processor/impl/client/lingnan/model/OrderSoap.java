/**
 * OrderSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.lingnan.model;

public interface OrderSoap extends java.rmi.Remote {

    /**
     * 预订景区门票为新订单
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo addNew(OrderRequestInfo orderRequestInfo) throws java.rmi.RemoteException;

    /**
     * 快速预订景区门票
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo add(OrderRequestInfo orderRequestInfo) throws java.rmi.RemoteException;

    /**
     * 预付景区门票付款
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo pay(java.math.BigDecimal orderId) throws java.rmi.RemoteException;

    /**
     * 预付景区门票付款，发送短信
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo payAndSms(java.math.BigDecimal orderId) throws java.rmi.RemoteException;

    /**
     * 取消预订
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo cancel(java.lang.String orderId, java.lang.String leaveNote) throws java.rmi.RemoteException;

    /**
     * 撤销/恢复申请取消订单
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo cancelApply(java.math.BigDecimal orderId, org.apache.axis.types.UnsignedByte isCancelApply, java.lang.String logTxt) throws java.rmi.RemoteException;

    /**
     * 是否可以预订？
     */
    public boolean canReserve(OrderRequestInfo orderRequestInfo) throws java.rmi.RemoteException;

    /**
     * 获取预订信息
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo getInfo(java.lang.String orderId) throws java.rmi.RemoteException;

    /**
     * 对帐
     */
    public com.lvmama.passport.processor.impl.client.lingnan.model.CheckAccountInfo[] checkAccount(java.util.Calendar beginDate, java.util.Calendar endDate) throws java.rmi.RemoteException;
}
