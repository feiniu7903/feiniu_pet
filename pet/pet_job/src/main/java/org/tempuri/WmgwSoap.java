/**
 * WmgwSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface WmgwSoap extends java.rmi.Remote {

    /**
     * 客服网关，群发信息
     */
    public java.lang.String mongateCsSendSmsEx(java.lang.String userId, java.lang.String password, java.lang.String pszMobis, java.lang.String pszMsg, int iMobiCount) throws java.rmi.RemoteException;

    /**
     * 接收上行信息函数
     */
    public java.lang.String[] mongateCsGetSmsExEx(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 接收状态报告函数,返回字符串数组 返回：参数错误、登陆错误、无状态报告返回null;
     * 正常返回格式:日期,时间,信息编号,*,状态,状态详细信息(0接收成功 1发送暂缓 2接收失败)
     * 如：2009-05-18,15:41:32,0518154127116007,*,0,DELIVRD
     */
    public java.lang.String[] mongateCsGetStatusReportExEx(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 查询余额条数
     */
    public int mongateQueryBalance(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 支持长信息
     */
    public java.lang.String mongateCsSpSendSmsNew(java.lang.String userId, java.lang.String password, java.lang.String pszMobis, java.lang.String pszMsg, int iMobiCount, java.lang.String pszSubPort) throws java.rmi.RemoteException;
}
