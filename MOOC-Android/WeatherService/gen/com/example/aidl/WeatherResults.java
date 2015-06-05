/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\work\\git\\POSA-14\\ex\\WeatherService\\src\\com\\example\\aidl\\WeatherResults.aidl
 */
package com.example.aidl;
/**
 * Interface defining the method that receives callbacks from the
 * WeatherServiceAsync.  This method should be implemented by the
 * WeatherActivity.
 */
public interface WeatherResults extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.aidl.WeatherResults
{
private static final java.lang.String DESCRIPTOR = "com.example.aidl.WeatherResults";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.aidl.WeatherResults interface,
 * generating a proxy if needed.
 */
public static com.example.aidl.WeatherResults asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.aidl.WeatherResults))) {
return ((com.example.aidl.WeatherResults)iin);
}
return new com.example.aidl.WeatherResults.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_sendResults:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.example.aidl.WeatherData> _arg0;
_arg0 = data.createTypedArrayList(com.example.aidl.WeatherData.CREATOR);
this.sendResults(_arg0);
return true;
}
case TRANSACTION_sendErrors:
{
data.enforceInterface(DESCRIPTOR);
this.sendErrors();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.aidl.WeatherResults
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * This one-way (non-blocking) method allows WeatherServiceAsync
     * to return the List of WeatherData results associated with a
     * one-way WeatherRequest.getCurrentWeather() call.
     */
@Override public void sendResults(java.util.List<com.example.aidl.WeatherData> results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(results);
mRemote.transact(Stub.TRANSACTION_sendResults, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void sendErrors() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_sendErrors, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_sendResults = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_sendErrors = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * This one-way (non-blocking) method allows WeatherServiceAsync
     * to return the List of WeatherData results associated with a
     * one-way WeatherRequest.getCurrentWeather() call.
     */
public void sendResults(java.util.List<com.example.aidl.WeatherData> results) throws android.os.RemoteException;
public void sendErrors() throws android.os.RemoteException;
}
