/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\work\\git\\project\\android_app\\Weather\\src\\com\\weather\\mwg\\aidl\\WeatherResults.aidl
 */
package com.weather.mwg.aidl;
/**
 * Interface defining the method that receives callbacks from the
 * WeatherServiceAsync.  
 */
public interface WeatherResults extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.weather.mwg.aidl.WeatherResults
{
private static final java.lang.String DESCRIPTOR = "com.weather.mwg.aidl.WeatherResults";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.weather.mwg.aidl.WeatherResults interface,
 * generating a proxy if needed.
 */
public static com.weather.mwg.aidl.WeatherResults asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.weather.mwg.aidl.WeatherResults))) {
return ((com.weather.mwg.aidl.WeatherResults)iin);
}
return new com.weather.mwg.aidl.WeatherResults.Stub.Proxy(obj);
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
case TRANSACTION_sendResult:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.weather.mwg.aidl.WeatherData> _arg0;
_arg0 = data.createTypedArrayList(com.weather.mwg.aidl.WeatherData.CREATOR);
this.sendResult(_arg0);
return true;
}
case TRANSACTION_sendLocationName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendLocationName(_arg0);
return true;
}
case TRANSACTION_sendPM2_5:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.sendPM2_5(_arg0);
return true;
}
case TRANSACTION_sendErrors:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.sendErrors(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.weather.mwg.aidl.WeatherResults
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
// For weather info

@Override public void sendResult(java.util.List<com.weather.mwg.aidl.WeatherData> results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(results);
mRemote.transact(Stub.TRANSACTION_sendResult, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
// For location

@Override public void sendLocationName(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_sendLocationName, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
// For PM2.5

@Override public void sendPM2_5(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_sendPM2_5, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
// For error info

@Override public void sendErrors(int error) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(error);
mRemote.transact(Stub.TRANSACTION_sendErrors, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_sendResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_sendLocationName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_sendPM2_5 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_sendErrors = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
// For weather info

public void sendResult(java.util.List<com.weather.mwg.aidl.WeatherData> results) throws android.os.RemoteException;
// For location

public void sendLocationName(java.lang.String name) throws android.os.RemoteException;
// For PM2.5

public void sendPM2_5(int value) throws android.os.RemoteException;
// For error info

public void sendErrors(int error) throws android.os.RemoteException;
}
