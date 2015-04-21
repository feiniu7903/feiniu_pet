package com.lvmama.back.sweb.util.marketing;
/**
 * ReplicationServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface ReplicationServiceSoap_PortType extends java.rmi.Remote {
    public void export(java.lang.String filename, CatalogItemsFilter filter, ExportFlags flag, boolean exportSecurity, java.lang.String sessionID) throws java.rmi.RemoteException;
    public ImportError[] _import(java.lang.String filename, ImportFlags flag, java.util.Calendar lastPurgedLog, boolean updateReplicationLog, boolean returnErrors, CatalogItemsFilter filter, PathMapEntry[] pathMap, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void markForReplication(java.lang.String item, boolean replicate, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void purgeLog(java.lang.String[] items, java.util.Calendar timestamp, java.lang.String sessionID) throws java.rmi.RemoteException;
}
