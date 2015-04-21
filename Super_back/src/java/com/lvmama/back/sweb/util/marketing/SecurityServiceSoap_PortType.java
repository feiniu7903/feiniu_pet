package com.lvmama.back.sweb.util.marketing;
/**
 * SecurityServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface SecurityServiceSoap_PortType extends java.rmi.Remote {
    public Privilege[] getGlobalPrivileges(java.lang.String sessionID) throws java.rmi.RemoteException;
    public ACL getGlobalPrivilegeACL(java.lang.String privilegeName, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void updateGlobalPrivilegeACL(java.lang.String privilegeName, ACL acl, UpdateACLParams updateACLParams, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void forgetAccounts(Account[] account, int cleanuplevel, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void renameAccounts(Account[] from, Account[] to, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void joinGroups(Account[] group, Account[] member, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void leaveGroups(Account[] group, Account[] member, java.lang.String sessionID) throws java.rmi.RemoteException;
    public Account[] getGroups(Account[] member, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException;
    public Account[] getMembers(Account[] group, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException;
    public boolean isMember(Account[] group, Account[] member, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException;
    public int[] getPermissions(ACL[] acls, Account account, java.lang.String sessionID) throws java.rmi.RemoteException;
    public boolean[] getPrivilegesStatus(java.lang.String[] privileges, java.lang.String sessionID) throws java.rmi.RemoteException;
    public Account[] getAccounts(Account[] account, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String getAccountTenantID(Account account, java.lang.String sessionID) throws java.rmi.RemoteException;
}
