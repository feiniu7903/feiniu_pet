/**
 * MMSSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface MMSSoap extends java.rmi.Remote {

    /**
     * 发送彩信,参数(用户名，密码，彩信标题，手机号码，彩信内容，彩信发送类型（1是网关发，2是卡发）)
     */
    public java.lang.String sendMMS(java.lang.String userName, java.lang.String password, java.lang.String title, java.lang.String userNumbers, byte[] MMSContent, int sendType) throws java.rmi.RemoteException;

    /**
     * 发送彩信,参数(用户名，密码，彩信标题，手机号码，彩信内容，定时时间(yyyyMMddHHmm)(年年年年月月日日时时分分))，彩信发送类型（1是网关发，2是卡发）
     */
    public java.lang.String sendMMSEx(java.lang.String userName, java.lang.String password, java.lang.String title, java.lang.String userNumbers, byte[] MMSContent, java.lang.String sendtime, int sendType) throws java.rmi.RemoteException;

    /**
     * 查询彩信剩余条数,参数(用户名，密码，彩信发送类型（1是网关发，2是卡发）)
     */
    public long getMMSCount(java.lang.String userName, java.lang.String password, int sendType) throws java.rmi.RemoteException;

    /**
     * 查询状态报告,参数(用户名,密码)
     */
    public java.lang.String getStatusReport(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 获取上行数据,参数(用户名,密码)
     */
    public java.lang.String getMoContent(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException;
}
