package com.lvmama.back.sweb.util.marketing;
/**
 * IBotServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface IBotServiceSoap_PortType extends java.rmi.Remote {
    public void executeIBotNow(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException;
    public int writeIBot(CatalogObject obj, java.lang.String path, boolean resolveLinks, boolean allowOverwrite, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void subscribe(java.lang.String path, java.lang.String customizationXml, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void unsubscribe(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void moveIBot(java.lang.String fromPath, java.lang.String toPath, boolean resolveLinks, boolean allowOverwrite, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void deleteIBot(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String sendMessage(java.lang.String[] recipient, java.lang.String[] group, java.lang.String subject, java.lang.String body, java.lang.String priority, java.lang.String sessionID) throws java.rmi.RemoteException;
}
