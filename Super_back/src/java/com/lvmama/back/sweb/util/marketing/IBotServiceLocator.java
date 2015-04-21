package com.lvmama.back.sweb.util.marketing;
/**
 * IBotServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class IBotServiceLocator extends org.apache.axis.client.Service implements IBotService {

    public IBotServiceLocator() {
    }


    public IBotServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IBotServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IBotServiceSoap
    private java.lang.String IBotServiceSoap_address = "http://192.168.0.26:7001/analytics-ws/saw.dll?SoapImpl=ibotService";

    public java.lang.String getIBotServiceSoapAddress() {
        return IBotServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IBotServiceSoapWSDDServiceName = "IBotServiceSoap";

    public java.lang.String getIBotServiceSoapWSDDServiceName() {
        return IBotServiceSoapWSDDServiceName;
    }

    public void setIBotServiceSoapWSDDServiceName(java.lang.String name) {
        IBotServiceSoapWSDDServiceName = name;
    }

    public IBotServiceSoap_PortType getIBotServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IBotServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIBotServiceSoap(endpoint);
    }

    public IBotServiceSoap_PortType getIBotServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            IBotServiceStub _stub = new IBotServiceStub(portAddress, this);
            _stub.setPortName(getIBotServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIBotServiceSoapEndpointAddress(java.lang.String address) {
        IBotServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (IBotServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                IBotServiceStub _stub = new IBotServiceStub(new java.net.URL(IBotServiceSoap_address), this);
                _stub.setPortName(getIBotServiceSoapWSDDServiceName());
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
        if ("IBotServiceSoap".equals(inputPortName)) {
            return getIBotServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "IBotService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "IBotServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IBotServiceSoap".equals(portName)) {
            setIBotServiceSoapEndpointAddress(address);
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
