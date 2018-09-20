/**
 * MsgTaskServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.keng.base.utils.mandalaSMS.org.tempuri;

public class MsgTaskServiceLocator extends org.apache.axis.client.Service implements com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskService {

    public MsgTaskServiceLocator() {
    }


    public MsgTaskServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MsgTaskServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MsgTaskServiceSoap
    private java.lang.String MsgTaskServiceSoap_address = "http://192.168.1.104:7002/MsgTaskService.asmx";

    public java.lang.String getMsgTaskServiceSoapAddress() {
        return MsgTaskServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MsgTaskServiceSoapWSDDServiceName = "MsgTaskServiceSoap";

    public java.lang.String getMsgTaskServiceSoapWSDDServiceName() {
        return MsgTaskServiceSoapWSDDServiceName;
    }

    public void setMsgTaskServiceSoapWSDDServiceName(java.lang.String name) {
        MsgTaskServiceSoapWSDDServiceName = name;
    }

    public com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoap getMsgTaskServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MsgTaskServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMsgTaskServiceSoap(endpoint);
    }

    public com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoap getMsgTaskServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoapStub _stub = new com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoapStub(portAddress, this);
            _stub.setPortName(getMsgTaskServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMsgTaskServiceSoapEndpointAddress(java.lang.String address) {
        MsgTaskServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoapStub _stub = new com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoapStub(new java.net.URL(MsgTaskServiceSoap_address), this);
                _stub.setPortName(getMsgTaskServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MsgTaskServiceSoap".equals(inputPortName)) {
            return getMsgTaskServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "MsgTaskService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MsgTaskServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MsgTaskServiceSoap".equals(portName)) {
            setMsgTaskServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
