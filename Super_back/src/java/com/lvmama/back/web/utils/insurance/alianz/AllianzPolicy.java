/**
 * AllianzPolicy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.back.web.utils.insurance.alianz;

public interface AllianzPolicy extends javax.xml.rpc.Service {
    public java.lang.String getAllianzPolicySoap12Address();

    public AllianzPolicySoap_PortType getAllianzPolicySoap12() throws javax.xml.rpc.ServiceException;

    public AllianzPolicySoap_PortType getAllianzPolicySoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getAllianzPolicySoapAddress();

    public AllianzPolicySoap_PortType getAllianzPolicySoap() throws javax.xml.rpc.ServiceException;

    public AllianzPolicySoap_PortType getAllianzPolicySoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
