/**
 * Order.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.lingnan.model;

public interface Order extends javax.xml.rpc.Service {

/**
 * 景区门票预订中心
 */
    public java.lang.String getOrderSoapAddress();

    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderSoap getOrderSoap() throws javax.xml.rpc.ServiceException;

    public com.lvmama.passport.processor.impl.client.lingnan.model.OrderSoap getOrderSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
