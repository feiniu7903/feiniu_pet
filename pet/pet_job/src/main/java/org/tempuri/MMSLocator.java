/**
 * MMSLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class MMSLocator extends org.apache.axis.client.Service implements org.tempuri.MMS {

    public MMSLocator() {
    }


    public MMSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MMSLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MMSSoap12
    private java.lang.String MMSSoap12_address = "http://mmsplat.eucp.b2m.cn/MMSCenterInterface/MMS.asmx";

    public java.lang.String getMMSSoap12Address() {
        return MMSSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MMSSoap12WSDDServiceName = "MMSSoap12";

    public java.lang.String getMMSSoap12WSDDServiceName() {
        return MMSSoap12WSDDServiceName;
    }

    public void setMMSSoap12WSDDServiceName(java.lang.String name) {
        MMSSoap12WSDDServiceName = name;
    }

    public org.tempuri.MMSSoap getMMSSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MMSSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMMSSoap12(endpoint);
    }

    public org.tempuri.MMSSoap getMMSSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.MMSSoap12Stub _stub = new org.tempuri.MMSSoap12Stub(portAddress, this);
            _stub.setPortName(getMMSSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMMSSoap12EndpointAddress(java.lang.String address) {
        MMSSoap12_address = address;
    }


    // Use to get a proxy class for MMSSoap
    private java.lang.String MMSSoap_address = "http://mmsplat.eucp.b2m.cn/MMSCenterInterface/MMS.asmx";

    public java.lang.String getMMSSoapAddress() {
        return MMSSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MMSSoapWSDDServiceName = "MMSSoap";

    public java.lang.String getMMSSoapWSDDServiceName() {
        return MMSSoapWSDDServiceName;
    }

    public void setMMSSoapWSDDServiceName(java.lang.String name) {
        MMSSoapWSDDServiceName = name;
    }

    public org.tempuri.MMSSoap getMMSSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MMSSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMMSSoap(endpoint);
    }

    public org.tempuri.MMSSoap getMMSSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.MMSSoapStub _stub = new org.tempuri.MMSSoapStub(portAddress, this);
            _stub.setPortName(getMMSSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMMSSoapEndpointAddress(java.lang.String address) {
        MMSSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.MMSSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.MMSSoap12Stub _stub = new org.tempuri.MMSSoap12Stub(new java.net.URL(MMSSoap12_address), this);
                _stub.setPortName(getMMSSoap12WSDDServiceName());
                return _stub;
            }
            if (org.tempuri.MMSSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.MMSSoapStub _stub = new org.tempuri.MMSSoapStub(new java.net.URL(MMSSoap_address), this);
                _stub.setPortName(getMMSSoapWSDDServiceName());
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
        if ("MMSSoap12".equals(inputPortName)) {
            return getMMSSoap12();
        }
        else if ("MMSSoap".equals(inputPortName)) {
            return getMMSSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "MMS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MMSSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MMSSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MMSSoap12".equals(portName)) {
            setMMSSoap12EndpointAddress(address);
        }
        else 
if ("MMSSoap".equals(portName)) {
            setMMSSoapEndpointAddress(address);
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
