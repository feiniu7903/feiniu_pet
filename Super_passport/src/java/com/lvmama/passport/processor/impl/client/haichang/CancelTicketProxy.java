package com.lvmama.passport.processor.impl.client.haichang;

public class CancelTicketProxy implements CancelTicket {
  private String _endpoint = null;
  private CancelTicket cancelTicket = null;
  
  public CancelTicketProxy() {
    _initCancelTicketProxy();
  }
  
  public CancelTicketProxy(String endpoint) {
    _endpoint = endpoint;
    _initCancelTicketProxy();
  }
  
  private void _initCancelTicketProxy() {
    try {
      cancelTicket = (new CancelTicketImplServiceLocator()).getCancelTicketImplPort();
      if (cancelTicket != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cancelTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cancelTicket)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cancelTicket != null)
      ((javax.xml.rpc.Stub)cancelTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CancelTicket getCancelTicket() {
    if (cancelTicket == null)
      _initCancelTicketProxy();
    return cancelTicket;
  }
  
  public ResultCancelTicket cancelTicket(java.lang.String agentOrderID, java.lang.String agentID, java.lang.String agentPassword, java.lang.String companyID, java.lang.String visitorName, java.lang.String visitorPhoneNumber, java.lang.String payFlag) throws java.rmi.RemoteException{
    if (cancelTicket == null)
      _initCancelTicketProxy();
    return cancelTicket.cancelTicket(agentOrderID, agentID, agentPassword, companyID, visitorName, visitorPhoneNumber, payFlag);
  }
  
  
}