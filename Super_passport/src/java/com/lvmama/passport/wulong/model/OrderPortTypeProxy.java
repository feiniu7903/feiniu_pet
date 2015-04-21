package com.lvmama.passport.wulong.model;

public class OrderPortTypeProxy implements com.lvmama.passport.wulong.model.OrderPortType {
  private String _endpoint = null;
  private com.lvmama.passport.wulong.model.OrderPortType orderPortType = null;
  
  public OrderPortTypeProxy() {
    _initOrderPortTypeProxy();
  }
  
  public OrderPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initOrderPortTypeProxy();
  }
  
  private void _initOrderPortTypeProxy() {
    try {
      orderPortType = (new com.lvmama.passport.wulong.model.OrderLocator()).getorderHttpSoap11Endpoint();
      if (orderPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)orderPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)orderPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (orderPortType != null)
      ((javax.xml.rpc.Stub)orderPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.lvmama.passport.wulong.model.OrderPortType getOrderPortType() {
    if (orderPortType == null)
      _initOrderPortTypeProxy();
    return orderPortType;
  }
  
  public java.lang.String orderSubmit(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String content) throws java.rmi.RemoteException{
    if (orderPortType == null)
      _initOrderPortTypeProxy();
    return orderPortType.orderSubmit(pass, dis_code, checkcode, dispass, content);
  }
  
  public java.lang.String orderCancel(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String orderId, java.lang.String reason) throws java.rmi.RemoteException{
    if (orderPortType == null)
      _initOrderPortTypeProxy();
    return orderPortType.orderCancel(pass, dis_code, checkcode, dispass, orderId, reason);
  }
  
  public java.lang.String orderQuery(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String orderId) throws java.rmi.RemoteException{
    if (orderPortType == null)
      _initOrderPortTypeProxy();
    return orderPortType.orderQuery(pass, dis_code, checkcode, dispass, orderId);
  }
  
  public java.lang.String orderUpdate(java.lang.String pass, java.lang.String dis_code, java.lang.String checkcode, java.lang.String dispass, java.lang.String content) throws java.rmi.RemoteException{
    if (orderPortType == null)
      _initOrderPortTypeProxy();
    return orderPortType.orderUpdate(pass, dis_code, checkcode, dispass, content);
  }
  
  
}