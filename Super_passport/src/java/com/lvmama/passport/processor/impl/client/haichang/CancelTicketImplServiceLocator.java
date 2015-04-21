/**
 * CancelTicketImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.haichang;

import com.lvmama.passport.utils.WebServiceConstant;

public class CancelTicketImplServiceLocator extends org.apache.axis.client.Service implements com.lvmama.passport.processor.impl.client.haichang.CancelTicketImplService {

    public CancelTicketImplServiceLocator() {
    	CancelTicketImplPort_address=WebServiceConstant.getProperties("haichangchina.url")+"/CancelTicket";
    }


    public CancelTicketImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CancelTicketImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CancelTicketImplPort
    private java.lang.String CancelTicketImplPort_address;

    public java.lang.String getCancelTicketImplPortAddress() {
        return CancelTicketImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CancelTicketImplPortWSDDServiceName = "CancelTicketImplPort";

    public java.lang.String getCancelTicketImplPortWSDDServiceName() {
        return CancelTicketImplPortWSDDServiceName;
    }

    public void setCancelTicketImplPortWSDDServiceName(java.lang.String name) {
        CancelTicketImplPortWSDDServiceName = name;
    }

    public com.lvmama.passport.processor.impl.client.haichang.CancelTicket getCancelTicketImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CancelTicketImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCancelTicketImplPort(endpoint);
    }

    public com.lvmama.passport.processor.impl.client.haichang.CancelTicket getCancelTicketImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.lvmama.passport.processor.impl.client.haichang.CancelTicketImplServiceSoapBindingStub _stub = new com.lvmama.passport.processor.impl.client.haichang.CancelTicketImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCancelTicketImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCancelTicketImplPortEndpointAddress(java.lang.String address) {
        CancelTicketImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.lvmama.passport.processor.impl.client.haichang.CancelTicket.class.isAssignableFrom(serviceEndpointInterface)) {
                com.lvmama.passport.processor.impl.client.haichang.CancelTicketImplServiceSoapBindingStub _stub = new com.lvmama.passport.processor.impl.client.haichang.CancelTicketImplServiceSoapBindingStub(new java.net.URL(CancelTicketImplPort_address), this);
                _stub.setPortName(getCancelTicketImplPortWSDDServiceName());
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
        if ("CancelTicketImplPort".equals(inputPortName)) {
            return getCancelTicketImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.ticket.com/", "CancelTicketImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.ticket.com/", "CancelTicketImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CancelTicketImplPort".equals(portName)) {
            setCancelTicketImplPortEndpointAddress(address);
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
