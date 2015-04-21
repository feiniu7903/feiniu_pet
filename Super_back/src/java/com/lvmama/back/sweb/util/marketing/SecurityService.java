package com.lvmama.back.sweb.util.marketing;
/**
 * SecurityService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface SecurityService extends javax.xml.rpc.Service {
    public java.lang.String getSecurityServiceSoapAddress();

    public SecurityServiceSoap_PortType getSecurityServiceSoap() throws javax.xml.rpc.ServiceException;

    public SecurityServiceSoap_PortType getSecurityServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
