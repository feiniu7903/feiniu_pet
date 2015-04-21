package com.lvmama.passport.hengdianmc.client;

public class AgentInterfaceSoapProxy implements AgentInterfaceSoap {
  private String _endpoint = null;
  private com.lvmama.passport.hengdianmc.client.AgentInterfaceSoap agentInterfaceSoap = null;
  
  public AgentInterfaceSoapProxy() {
    _initAgentInterfaceSoapProxy();
  }
  
  public AgentInterfaceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initAgentInterfaceSoapProxy();
  }
  
  private void _initAgentInterfaceSoapProxy() {
    try {
      agentInterfaceSoap = (new AgentInterfaceLocator()).getAgentInterfaceSoap();
      if (agentInterfaceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)agentInterfaceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)agentInterfaceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (agentInterfaceSoap != null)
      ((javax.xml.rpc.Stub)agentInterfaceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.lvmama.passport.hengdianmc.client.AgentInterfaceSoap getAgentInterfaceSoap() {
    if (agentInterfaceSoap == null)
      _initAgentInterfaceSoapProxy();
    return agentInterfaceSoap;
  }
  
  public com.lvmama.passport.hengdianmc.client.OrderRep orderReq(OrderInfo orderInfo) throws java.rmi.RemoteException{
    if (agentInterfaceSoap == null)
      _initAgentInterfaceSoapProxy();
    return agentInterfaceSoap.orderReq(orderInfo);
  }
  
  
}