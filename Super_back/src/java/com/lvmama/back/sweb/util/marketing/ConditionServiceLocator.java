package com.lvmama.back.sweb.util.marketing;
/**
 * ConditionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class ConditionServiceLocator extends org.apache.axis.client.Service implements ConditionService {

    public ConditionServiceLocator() {
    }


    public ConditionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ConditionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ConditionServiceSoap
    private java.lang.String ConditionServiceSoap_address = "http://192.168.0.26:7001/analytics-ws/saw.dll?SoapImpl=conditionService";

    public java.lang.String getConditionServiceSoapAddress() {
        return ConditionServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConditionServiceSoapWSDDServiceName = "ConditionServiceSoap";

    public java.lang.String getConditionServiceSoapWSDDServiceName() {
        return ConditionServiceSoapWSDDServiceName;
    }

    public void setConditionServiceSoapWSDDServiceName(java.lang.String name) {
        ConditionServiceSoapWSDDServiceName = name;
    }

    public ConditionServiceSoap_PortType getConditionServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConditionServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConditionServiceSoap(endpoint);
    }

    public ConditionServiceSoap_PortType getConditionServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ConditionServiceStub _stub = new ConditionServiceStub(portAddress, this);
            _stub.setPortName(getConditionServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConditionServiceSoapEndpointAddress(java.lang.String address) {
        ConditionServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ConditionServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ConditionServiceStub _stub = new ConditionServiceStub(new java.net.URL(ConditionServiceSoap_address), this);
                _stub.setPortName(getConditionServiceSoapWSDDServiceName());
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
        if ("ConditionServiceSoap".equals(inputPortName)) {
            return getConditionServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ConditionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ConditionServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ConditionServiceSoap".equals(portName)) {
            setConditionServiceSoapEndpointAddress(address);
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
