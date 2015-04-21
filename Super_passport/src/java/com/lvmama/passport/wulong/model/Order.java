/**
 * Order.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.wulong.model;

public interface Order extends javax.xml.rpc.Service {
    public java.lang.String getorderHttpSoap11EndpointAddress();

    public com.lvmama.passport.wulong.model.OrderPortType getorderHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException;

    public com.lvmama.passport.wulong.model.OrderPortType getorderHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
