package com.lvmama.back.sweb.util.marketing;
/**
 * SAWSessionServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface SAWSessionServiceSoap_PortType extends java.rmi.Remote {
    public java.lang.String logon(java.lang.String name, java.lang.String password) throws java.rmi.RemoteException;
    public AuthResult logonex(java.lang.String name, java.lang.String password, SAWSessionParameters sessionparams) throws java.rmi.RemoteException;
    public void logoff(java.lang.String sessionID) throws java.rmi.RemoteException;
    public void keepAlive(java.lang.String[] sessionID) throws java.rmi.RemoteException;
    public java.lang.String getCurUser(java.lang.String sessionID) throws java.rmi.RemoteException;
    public SessionEnvironment getSessionEnvironment(java.lang.String sessionID) throws java.rmi.RemoteException;

    /**
     * Returns values of BIEE variables associated with the current
     * session
     *            Here is the list of predefined variables :
     *            NQ_SESSION.USER,
     *            NQ_SESSION.USERGUID,
     *            NQ_SESSION.GROUP,
     *            NQ_SESSION.GROUPGUIDS,
     *            NQ_SESSION.WEBGROUPS,
     *            NQ_SESSION.REALM,
     *            NQ_SESSION.REALMGUID,
     *            NQ_SESSION.TOKENS,
     *            NQ_SESSION.REQUESTKEY,
     *            NQ_SESSION.PORTALPATH,
     *            NQ_SESSION.DISPLAYNAME,
     *            NQ_SESSION.SKIN,
     *            NQ_SESSION.STYLE,
     *            NQ_SESSION.EMAIL,
     *            NQ_SESSION.CURRENCYTAG,
     *            NQ_SESSION.ACTUATEUSERID,
     *            NQ_SESSION.TIMEZONE,
     *            NQ_SESSION.DATA_TZ,
     *            NQ_SESSION.DATA_DISPLAY_TZ,
     *            NQ_SESSION.PROXYLEVEL,
     *            NQ_SESSION.USERLOCALE,
     *            NQ_SESSION.USERLANG,
     *            NQ_SESSION.PREFERRED_CURRENCY
     */
    public java.lang.String[] getSessionVariables(GetSessionVariables parameters) throws java.rmi.RemoteException;

    /**
     * Access BIPS variables using expression syntax. BIS variables
     * can be retrieved using this method with the expression like biServer.variables['NQ_SESSION.USER']
     */
    public java.lang.String[] getBIPSVariables(GetBIPSVariables parameters) throws java.rmi.RemoteException;
    public java.lang.String impersonate(java.lang.String name, java.lang.String password, java.lang.String impersonateID) throws java.rmi.RemoteException;
    public AuthResult impersonateex(java.lang.String name, java.lang.String password, java.lang.String impersonateID, SAWSessionParameters sessionparams) throws java.rmi.RemoteException;
}
