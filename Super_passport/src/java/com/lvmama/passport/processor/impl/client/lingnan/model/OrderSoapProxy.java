package com.lvmama.passport.processor.impl.client.lingnan.model;

public class OrderSoapProxy implements OrderSoap {
  private String _endpoint = null;
  private com.lvmama.passport.processor.impl.client.lingnan.model.OrderSoap orderSoap = null;
  
  public OrderSoapProxy() {
    _initOrderSoapProxy();
  }
  
  public OrderSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initOrderSoapProxy();
  }
  
  private void _initOrderSoapProxy() {
    try {
      orderSoap = (new OrderLocator()).getOrderSoap();
      if (orderSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)orderSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)orderSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (orderSoap != null)
      ((javax.xml.rpc.Stub)orderSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderSoap getOrderSoap() {
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap;
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo addNew(OrderRequestInfo orderRequestInfo) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.addNew(orderRequestInfo);
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo add(OrderRequestInfo orderRequestInfo) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.add(orderRequestInfo);
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo pay(java.math.BigDecimal orderId) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.pay(orderId);
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo payAndSms(java.math.BigDecimal orderId) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.payAndSms(orderId);
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo cancel(java.lang.String orderId, java.lang.String leaveNote) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.cancel(orderId, leaveNote);
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo cancelApply(java.math.BigDecimal orderId, org.apache.axis.types.UnsignedByte isCancelApply, java.lang.String logTxt) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.cancelApply(orderId, isCancelApply, logTxt);
  }
  
  public boolean canReserve(OrderRequestInfo orderRequestInfo) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.canReserve(orderRequestInfo);
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo getInfo(java.lang.String orderId) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.getInfo(orderId);
  }
  
  public com.lvmama.passport.processor.impl.client.lingnan.model.CheckAccountInfo[] checkAccount(java.util.Calendar beginDate, java.util.Calendar endDate) throws java.rmi.RemoteException{
    if (orderSoap == null)
      _initOrderSoapProxy();
    return orderSoap.checkAccount(beginDate, endDate);
  }
  
  
}