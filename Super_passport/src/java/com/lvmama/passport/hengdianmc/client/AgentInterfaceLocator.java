/**
 * AgentInterfaceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.hengdianmc.client;

import com.lvmama.passport.utils.WebServiceConstant;

public class AgentInterfaceLocator extends org.apache.axis.client.Service implements AgentInterface {

    public AgentInterfaceLocator() {
    	AgentInterfaceSoap_address=WebServiceConstant.getProperties("hengdianMovieCity.url");
    }


    public AgentInterfaceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AgentInterfaceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AgentInterfaceSoap
    private java.lang.String AgentInterfaceSoap_address ;

    public java.lang.String getAgentInterfaceSoapAddress() {
        return AgentInterfaceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AgentInterfaceSoapWSDDServiceName = "AgentInterfaceSoap";

    public java.lang.String getAgentInterfaceSoapWSDDServiceName() {
        return AgentInterfaceSoapWSDDServiceName;
    }

    public void setAgentInterfaceSoapWSDDServiceName(java.lang.String name) {
        AgentInterfaceSoapWSDDServiceName = name;
    }

    public com.lvmama.passport.hengdianmc.client.AgentInterfaceSoap getAgentInterfaceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AgentInterfaceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAgentInterfaceSoap(endpoint);
    }

    public com.lvmama.passport.hengdianmc.client.AgentInterfaceSoap getAgentInterfaceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AgentInterfaceSoapStub _stub = new AgentInterfaceSoapStub(portAddress, this);
            _stub.setPortName(getAgentInterfaceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAgentInterfaceSoapEndpointAddress(java.lang.String address) {
        AgentInterfaceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (AgentInterfaceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                AgentInterfaceSoapStub _stub = new AgentInterfaceSoapStub(new java.net.URL(AgentInterfaceSoap_address), this);
                _stub.setPortName(getAgentInterfaceSoapWSDDServiceName());
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
        if ("AgentInterfaceSoap".equals(inputPortName)) {
            return getAgentInterfaceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "AgentInterface");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "AgentInterfaceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AgentInterfaceSoap".equals(portName)) {
            setAgentInterfaceSoapEndpointAddress(address);
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
