/**
 * BookingTicket.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.haichang;

public interface BookingTicket extends java.rmi.Remote {
    public com.lvmama.passport.processor.impl.client.haichang.ResultBookingTicket bookingTicket(java.lang.String agentOrderID, java.lang.String agentID, java.lang.String agentPassword, java.lang.String companyID, java.lang.String visitorName, java.lang.String visitorPhoneNumber, java.lang.String identificationCardNumber, java.lang.String city, java.lang.String profession, java.lang.String mail, java.lang.String QQ, java.lang.String blog, java.lang.String weChat, java.lang.String blogAddress, java.lang.String sendAddress, java.lang.String ticketType, java.lang.String ticketNumber, java.lang.String ticketPrice, java.lang.String timeStart, java.lang.String timeEnd, java.lang.String flagPayOffline, java.lang.String flagPayOnline, java.lang.String visitorIP) throws java.rmi.RemoteException;
}
