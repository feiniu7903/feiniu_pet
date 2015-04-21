/**
 * OrderPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.wulong.model;

public interface OrderPortType extends java.rmi.Remote {
    public java.lang.String orderSubmit(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String content) throws java.rmi.RemoteException;
    public java.lang.String orderCancel(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String orderId, java.lang.String reason) throws java.rmi.RemoteException;
    public java.lang.String orderQuery(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String orderId) throws java.rmi.RemoteException;
    public java.lang.String orderUpdate(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String content) throws java.rmi.RemoteException;
}
