package com.keng.base.utils.mandalaSMS.org.tempuri;

public class MsgTaskServiceSoapProxy implements com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoap {
  private String _endpoint = null;
  private com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoap msgTaskServiceSoap = null;
  
  public MsgTaskServiceSoapProxy() {
    _initMsgTaskServiceSoapProxy();
  }
  
  public MsgTaskServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initMsgTaskServiceSoapProxy();
  }
  
  private void _initMsgTaskServiceSoapProxy() {
    try {
      msgTaskServiceSoap = (new com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceLocator()).getMsgTaskServiceSoap();
      if (msgTaskServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)msgTaskServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)msgTaskServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (msgTaskServiceSoap != null)
      ((javax.xml.rpc.Stub)msgTaskServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoap getMsgTaskServiceSoap() {
    if (msgTaskServiceSoap == null)
      _initMsgTaskServiceSoapProxy();
    return msgTaskServiceSoap;
  }
  
  public com.keng.base.utils.mandalaSMS.org.tempuri.WebApiResult submitMsgCustomer(java.lang.String scode, java.lang.String bizid, java.lang.String senddt, java.lang.String sysid, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException{
    if (msgTaskServiceSoap == null)
      _initMsgTaskServiceSoapProxy();
    return msgTaskServiceSoap.submitMsgCustomer(scode, bizid, senddt, sysid, keys, values);
  }
  
  public com.keng.base.utils.mandalaSMS.org.tempuri.WebApiResult submitMsgEmp(java.lang.String empid, java.lang.String bizid, java.lang.String senddt, java.lang.String sysid, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException{
    if (msgTaskServiceSoap == null)
      _initMsgTaskServiceSoapProxy();
    return msgTaskServiceSoap.submitMsgEmp(empid, bizid, senddt, sysid, keys, values);
  }
  
  public com.keng.base.utils.mandalaSMS.org.tempuri.WebApiResult submitMsgSimple(java.lang.String cellphone, java.lang.String bizid, java.lang.String senddt, java.lang.String sysid, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException{
    if (msgTaskServiceSoap == null)
      _initMsgTaskServiceSoapProxy();
    return msgTaskServiceSoap.submitMsgSimple(cellphone, bizid, senddt, sysid, keys, values);
  }
  
  
}