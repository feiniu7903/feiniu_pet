/**
 * AllianzPolicyLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.back.web.utils.insurance.alianz;

public class AllianzPolicyLocator extends org.apache.axis.client.Service implements AllianzPolicy {

    public AllianzPolicyLocator() {
    }


    public AllianzPolicyLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AllianzPolicyLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AllianzPolicySoap12
    private java.lang.String AllianzPolicySoap12_address = "http://sales.allianz.com.cn/allianzpolicy.asmx";

    public java.lang.String getAllianzPolicySoap12Address() {
        return AllianzPolicySoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AllianzPolicySoap12WSDDServiceName = "AllianzPolicySoap12";

    public java.lang.String getAllianzPolicySoap12WSDDServiceName() {
        return AllianzPolicySoap12WSDDServiceName;
    }

    public void setAllianzPolicySoap12WSDDServiceName(java.lang.String name) {
        AllianzPolicySoap12WSDDServiceName = name;
    }

    public AllianzPolicySoap_PortType getAllianzPolicySoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AllianzPolicySoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAllianzPolicySoap12(endpoint);
    }

    public AllianzPolicySoap_PortType getAllianzPolicySoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AllianzPolicySoap12Stub _stub = new AllianzPolicySoap12Stub(portAddress, this);
            _stub.setPortName(getAllianzPolicySoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAllianzPolicySoap12EndpointAddress(java.lang.String address) {
        AllianzPolicySoap12_address = address;
    }


    // Use to get a proxy class for AllianzPolicySoap
    private java.lang.String AllianzPolicySoap_address = "http://sales.allianz.com.cn/allianzpolicy.asmx";

    public java.lang.String getAllianzPolicySoapAddress() {
        return AllianzPolicySoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AllianzPolicySoapWSDDServiceName = "AllianzPolicySoap";

    public java.lang.String getAllianzPolicySoapWSDDServiceName() {
        return AllianzPolicySoapWSDDServiceName;
    }

    public void setAllianzPolicySoapWSDDServiceName(java.lang.String name) {
        AllianzPolicySoapWSDDServiceName = name;
    }

    public AllianzPolicySoap_PortType getAllianzPolicySoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AllianzPolicySoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAllianzPolicySoap(endpoint);
    }

    public AllianzPolicySoap_PortType getAllianzPolicySoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AllianzPolicySoap_BindingStub _stub = new AllianzPolicySoap_BindingStub(portAddress, this);
            _stub.setPortName(getAllianzPolicySoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAllianzPolicySoapEndpointAddress(java.lang.String address) {
        AllianzPolicySoap_address = address;
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
            if (AllianzPolicySoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                AllianzPolicySoap12Stub _stub = new AllianzPolicySoap12Stub(new java.net.URL(AllianzPolicySoap12_address), this);
                _stub.setPortName(getAllianzPolicySoap12WSDDServiceName());
                return _stub;
            }
            if (AllianzPolicySoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                AllianzPolicySoap_BindingStub _stub = new AllianzPolicySoap_BindingStub(new java.net.URL(AllianzPolicySoap_address), this);
                _stub.setPortName(getAllianzPolicySoapWSDDServiceName());
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
        if ("AllianzPolicySoap12".equals(inputPortName)) {
            return getAllianzPolicySoap12();
        }
        else if ("AllianzPolicySoap".equals(inputPortName)) {
            return getAllianzPolicySoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sales.allianz.com.cn/", "AllianzPolicy");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sales.allianz.com.cn/", "AllianzPolicySoap12"));
            ports.add(new javax.xml.namespace.QName("http://sales.allianz.com.cn/", "AllianzPolicySoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AllianzPolicySoap12".equals(portName)) {
            setAllianzPolicySoap12EndpointAddress(address);
        }
        else 
if ("AllianzPolicySoap".equals(portName)) {
            setAllianzPolicySoapEndpointAddress(address);
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
