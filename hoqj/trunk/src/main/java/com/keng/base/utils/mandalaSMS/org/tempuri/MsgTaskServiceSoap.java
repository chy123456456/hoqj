/**
 * MsgTaskServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.keng.base.utils.mandalaSMS.org.tempuri;

public interface MsgTaskServiceSoap extends java.rmi.Remote {
    public com.keng.base.utils.mandalaSMS.org.tempuri.WebApiResult submitMsgCustomer(java.lang.String scode, java.lang.String bizid, java.lang.String senddt, java.lang.String sysid, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException;
    public com.keng.base.utils.mandalaSMS.org.tempuri.WebApiResult submitMsgEmp(java.lang.String empid, java.lang.String bizid, java.lang.String senddt, java.lang.String sysid, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException;
    public com.keng.base.utils.mandalaSMS.org.tempuri.WebApiResult submitMsgSimple(java.lang.String cellphone, java.lang.String bizid, java.lang.String senddt, java.lang.String sysid, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException;
}
