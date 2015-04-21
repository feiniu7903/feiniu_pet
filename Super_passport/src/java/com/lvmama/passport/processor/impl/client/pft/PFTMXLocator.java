/**
 * PFTMXLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.pft;

import com.lvmama.passport.utils.WebServiceConstant;

public class PFTMXLocator extends org.apache.axis.client.Service implements PFTMX {
    
	
	private String scenceFlag;

	private java.lang.String PFTMXPort_address;
	
	public PFTMXLocator(String scenceFlag) {
		this.scenceFlag = scenceFlag;
		PFTMXPort_address = WebServiceConstant.getProperties("pft"+this.scenceFlag+".url");
    }

    public PFTMXLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PFTMXLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PFTMXPort
    //private java.lang.String PFTMXPort_address = WebServiceConstant.getProperties("pft"+this.scenceFlag+".url");
    

    public java.lang.String getPFTMXPortAddress() {
        return PFTMXPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PFTMXPortWSDDServiceName = "PFTMXPort";

    public java.lang.String getPFTMXPortWSDDServiceName() {
        return PFTMXPortWSDDServiceName;
    }

    public void setPFTMXPortWSDDServiceName(java.lang.String name) {
        PFTMXPortWSDDServiceName = name;
    }

    public PFTMXPort getPFTMXPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PFTMXPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPFTMXPort(endpoint);
    }

    public PFTMXPort getPFTMXPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            PFTMXBindingStub _stub = new PFTMXBindingStub(portAddress, this);
            _stub.setPortName(getPFTMXPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPFTMXPortEndpointAddress(java.lang.String address) {
        PFTMXPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (PFTMXPort.class.isAssignableFrom(serviceEndpointInterface)) {
                PFTMXBindingStub _stub = new PFTMXBindingStub(new java.net.URL(PFTMXPort_address), this);
                _stub.setPortName(getPFTMXPortWSDDServiceName());
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
        if ("PFTMXPort".equals(inputPortName)) {
            return getPFTMXPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:PFTMX", "PFTMX");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:PFTMX", "PFTMXPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PFTMXPort".equals(portName)) {
            setPFTMXPortEndpointAddress(address);
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
