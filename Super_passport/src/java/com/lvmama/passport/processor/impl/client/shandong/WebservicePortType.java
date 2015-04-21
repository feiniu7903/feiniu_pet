/**
 * WebservicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong;

public interface WebservicePortType extends java.rmi.Remote {
    public com.lvmama.passport.processor.impl.client.shandong.model.ScenicResult getScenic(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5) throws java.rmi.RemoteException;
    public com.lvmama.passport.processor.impl.client.shandong.model.VersionResult getVersion(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public com.lvmama.passport.processor.impl.client.shandong.model.CancelResult cancellOrder(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException;
    public com.lvmama.passport.processor.impl.client.shandong.model.OrderResult saveOrder(java.lang.String in0, boolean in1, java.lang.String in2, java.lang.String in3, com.lvmama.passport.processor.impl.client.shandong.model.OrderBean in4, java.lang.String in5) throws java.rmi.RemoteException;
    public com.lvmama.passport.processor.impl.client.shandong.model.ProductResult getTickInfo(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5) throws java.rmi.RemoteException;
    public java.lang.String getCode(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException;
    public com.lvmama.passport.processor.impl.client.shandong.model.OrderResult saveActivityOrder(java.lang.String in0, java.lang.String in1, java.lang.String in2, com.lvmama.passport.processor.impl.client.shandong.model.OrderBean in3, java.lang.String in4, com.lvmama.passport.processor.impl.client.shandong.model.DetailSourceBean in5) throws java.rmi.RemoteException;
}
