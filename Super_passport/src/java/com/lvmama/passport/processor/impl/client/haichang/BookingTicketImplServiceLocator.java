/**
 * BookingTicketImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.haichang;

import com.lvmama.passport.utils.WebServiceConstant;

public class BookingTicketImplServiceLocator extends org.apache.axis.client.Service implements com.lvmama.passport.processor.impl.client.haichang.BookingTicketImplService {
	private static final long serialVersionUID = 141728722945020865L;

	public BookingTicketImplServiceLocator() {
    	BookingTicketImplPort_address = WebServiceConstant.getProperties("haichangchina.url")+"/BookTicket";
    }


    public BookingTicketImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BookingTicketImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BookingTicketImplPort
    private java.lang.String BookingTicketImplPort_address;

    public java.lang.String getBookingTicketImplPortAddress() {
        return BookingTicketImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BookingTicketImplPortWSDDServiceName = "BookingTicketImplPort";

    public java.lang.String getBookingTicketImplPortWSDDServiceName() {
        return BookingTicketImplPortWSDDServiceName;
    }

    public void setBookingTicketImplPortWSDDServiceName(java.lang.String name) {
        BookingTicketImplPortWSDDServiceName = name;
    }

    public com.lvmama.passport.processor.impl.client.haichang.BookingTicket getBookingTicketImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BookingTicketImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBookingTicketImplPort(endpoint);
    }

    public com.lvmama.passport.processor.impl.client.haichang.BookingTicket getBookingTicketImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.lvmama.passport.processor.impl.client.haichang.BookingTicketImplServiceSoapBindingStub _stub = new com.lvmama.passport.processor.impl.client.haichang.BookingTicketImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getBookingTicketImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBookingTicketImplPortEndpointAddress(java.lang.String address) {
        BookingTicketImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.lvmama.passport.processor.impl.client.haichang.BookingTicket.class.isAssignableFrom(serviceEndpointInterface)) {
                com.lvmama.passport.processor.impl.client.haichang.BookingTicketImplServiceSoapBindingStub _stub = new com.lvmama.passport.processor.impl.client.haichang.BookingTicketImplServiceSoapBindingStub(new java.net.URL(BookingTicketImplPort_address), this);
                _stub.setPortName(getBookingTicketImplPortWSDDServiceName());
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
        if ("BookingTicketImplPort".equals(inputPortName)) {
            return getBookingTicketImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.ticket.com/", "BookingTicketImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.ticket.com/", "BookingTicketImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BookingTicketImplPort".equals(portName)) {
            setBookingTicketImplPortEndpointAddress(address);
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
