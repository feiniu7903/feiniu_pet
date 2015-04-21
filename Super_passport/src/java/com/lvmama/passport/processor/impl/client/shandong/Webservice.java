/**
 * Webservice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong;

public interface Webservice extends javax.xml.rpc.Service {
    public java.lang.String getwebserviceHttpPortAddress();

    public com.lvmama.passport.processor.impl.client.shandong.WebservicePortType getwebserviceHttpPort() throws javax.xml.rpc.ServiceException;

    public com.lvmama.passport.processor.impl.client.shandong.WebservicePortType getwebserviceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
