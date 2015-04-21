/**
 * MMS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface MMS extends javax.xml.rpc.Service {
    public java.lang.String getMMSSoap12Address();

    public org.tempuri.MMSSoap getMMSSoap12() throws javax.xml.rpc.ServiceException;

    public org.tempuri.MMSSoap getMMSSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getMMSSoapAddress();

    public org.tempuri.MMSSoap getMMSSoap() throws javax.xml.rpc.ServiceException;

    public org.tempuri.MMSSoap getMMSSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
