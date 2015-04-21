/**
 * OrderLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.wulong.model;

import com.lvmama.passport.utils.WebServiceConstant;

public class OrderLocator extends org.apache.axis.client.Service implements com.lvmama.passport.wulong.model.Order {

    public OrderLocator() {
    	orderHttpSoap11Endpoint_address=WebServiceConstant.getProperties("wulong.url")+".orderHttpSoap11Endpoint/";
    }


    public OrderLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OrderLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for orderHttpSoap11Endpoint
    private java.lang.String orderHttpSoap11Endpoint_address;

    public java.lang.String getorderHttpSoap11EndpointAddress() {
        return orderHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String orderHttpSoap11EndpointWSDDServiceName = "orderHttpSoap11Endpoint";

    public java.lang.String getorderHttpSoap11EndpointWSDDServiceName() {
        return orderHttpSoap11EndpointWSDDServiceName;
    }

    public void setorderHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        orderHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.lvmama.passport.wulong.model.OrderPortType getorderHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(orderHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getorderHttpSoap11Endpoint(endpoint);
    }

    public com.lvmama.passport.wulong.model.OrderPortType getorderHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.lvmama.passport.wulong.model.OrderSoap11BindingStub _stub = new com.lvmama.passport.wulong.model.OrderSoap11BindingStub(portAddress, this);
            _stub.setPortName(getorderHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setorderHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        orderHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.lvmama.passport.wulong.model.OrderPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.lvmama.passport.wulong.model.OrderSoap11BindingStub _stub = new com.lvmama.passport.wulong.model.OrderSoap11BindingStub(new java.net.URL(orderHttpSoap11Endpoint_address), this);
                _stub.setPortName(getorderHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("orderHttpSoap11Endpoint".equals(inputPortName)) {
            return getorderHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://order.webservices.ejdls.cdtskj.com", "order");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://order.webservices.ejdls.cdtskj.com", "orderHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("orderHttpSoap11Endpoint".equals(portName)) {
            setorderHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
