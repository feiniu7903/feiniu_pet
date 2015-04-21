package com.lvmama.back.sweb.util.marketing;
/**
 * WebCatalogServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class WebCatalogServiceLocator extends org.apache.axis.client.Service implements WebCatalogService {

    public WebCatalogServiceLocator() {
    }


    public WebCatalogServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebCatalogServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebCatalogServiceSoap
    private java.lang.String WebCatalogServiceSoap_address = "http://192.168.0.26:7001/analytics-ws/saw.dll?SoapImpl=webCatalogService";

    public java.lang.String getWebCatalogServiceSoapAddress() {
        return WebCatalogServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebCatalogServiceSoapWSDDServiceName = "WebCatalogServiceSoap";

    public java.lang.String getWebCatalogServiceSoapWSDDServiceName() {
        return WebCatalogServiceSoapWSDDServiceName;
    }

    public void setWebCatalogServiceSoapWSDDServiceName(java.lang.String name) {
        WebCatalogServiceSoapWSDDServiceName = name;
    }

    public WebCatalogServiceSoap_PortType getWebCatalogServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebCatalogServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebCatalogServiceSoap(endpoint);
    }

    public WebCatalogServiceSoap_PortType getWebCatalogServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            WebCatalogServiceStub _stub = new WebCatalogServiceStub(portAddress, this);
            _stub.setPortName(getWebCatalogServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebCatalogServiceSoapEndpointAddress(java.lang.String address) {
        WebCatalogServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WebCatalogServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                WebCatalogServiceStub _stub = new WebCatalogServiceStub(new java.net.URL(WebCatalogServiceSoap_address), this);
                _stub.setPortName(getWebCatalogServiceSoapWSDDServiceName());
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
        if ("WebCatalogServiceSoap".equals(inputPortName)) {
            return getWebCatalogServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "WebCatalogService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "WebCatalogServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebCatalogServiceSoap".equals(portName)) {
            setWebCatalogServiceSoapEndpointAddress(address);
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
