package com.lvmama.back.sweb.util.marketing;
/**
 * SAWSessionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class SAWSessionServiceLocator extends org.apache.axis.client.Service implements SAWSessionService {

    public SAWSessionServiceLocator() {
    }


    public SAWSessionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SAWSessionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SAWSessionServiceSoap
    private java.lang.String SAWSessionServiceSoap_address = "http://192.168.0.26:7001/analytics-ws/saw.dll?SoapImpl=nQSessionService";

    public java.lang.String getSAWSessionServiceSoapAddress() {
        return SAWSessionServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SAWSessionServiceSoapWSDDServiceName = "SAWSessionServiceSoap";

    public java.lang.String getSAWSessionServiceSoapWSDDServiceName() {
        return SAWSessionServiceSoapWSDDServiceName;
    }

    public void setSAWSessionServiceSoapWSDDServiceName(java.lang.String name) {
        SAWSessionServiceSoapWSDDServiceName = name;
    }

    public SAWSessionServiceSoap_PortType getSAWSessionServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SAWSessionServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSAWSessionServiceSoap(endpoint);
    }

    public SAWSessionServiceSoap_PortType getSAWSessionServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SAWSessionServiceStub _stub = new SAWSessionServiceStub(portAddress, this);
            _stub.setPortName(getSAWSessionServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSAWSessionServiceSoapEndpointAddress(java.lang.String address) {
        SAWSessionServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SAWSessionServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SAWSessionServiceStub _stub = new SAWSessionServiceStub(new java.net.URL(SAWSessionServiceSoap_address), this);
                _stub.setPortName(getSAWSessionServiceSoapWSDDServiceName());
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
        if ("SAWSessionServiceSoap".equals(inputPortName)) {
            return getSAWSessionServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWSessionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWSessionServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SAWSessionServiceSoap".equals(portName)) {
            setSAWSessionServiceSoapEndpointAddress(address);
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
