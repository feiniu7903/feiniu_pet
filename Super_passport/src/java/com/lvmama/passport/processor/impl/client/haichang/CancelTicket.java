/**
 * CancelTicket.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.haichang;

public interface CancelTicket extends java.rmi.Remote {
    public com.lvmama.passport.processor.impl.client.haichang.ResultCancelTicket cancelTicket(java.lang.String agentOrderID, java.lang.String agentID, java.lang.String agentPassword, java.lang.String companyID, java.lang.String visitorName, java.lang.String visitorPhoneNumber, java.lang.String payFlag) throws java.rmi.RemoteException;
}
