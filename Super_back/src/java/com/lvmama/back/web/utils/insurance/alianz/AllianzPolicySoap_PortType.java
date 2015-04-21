/**
 * AllianzPolicySoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.back.web.utils.insurance.alianz;

public interface AllianzPolicySoap_PortType extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String changePassword(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPolicy(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAESforTest(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String queryPolicy(java.lang.String xml) throws java.rmi.RemoteException;
}
