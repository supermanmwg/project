/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\work\\git\\project\\android_app\\Weather\\src\\com\\weather\\mwg\\aidl\\WeatherRequest.aidl
 */
package com.weather.mwg.aidl;
/**
 * Interface defining the method implemented within
 * WeatherServiceAsync that provides asynchronous access to the
 * Weather Service web service.
 */
public interface WeatherRequest extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.weather.mwg.aidl.WeatherRequest
{
private static final java.lang.String DESCRIPTOR = "com.weather.mwg.aidl.WeatherRequest";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.weather.mwg.aidl.WeatherRequest interface,
 * generating a proxy if needed.
 */
public static com.weather.mwg.aidl.WeatherRequest asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.weather.mwg.aidl.WeatherRequest))) {
return ((com.weather.mwg.aidl.WeatherRequest)iin);
}
return new com.weather.mwg.aidl.WeatherRequest.Stub.Proxy(obj);
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
case TRANSACTION_getCurrentWeather:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
long _arg2;
_arg2 = data.readLong();
java.lang.String _arg3;
_arg3 = data.readString();
com.weather.mwg.aidl.WeatherResults _arg4;
_arg4 = com.weather.mwg.aidl.WeatherResults.Stub.asInterface(data.readStrongBinder());
this.getCurrentWeather(_arg0, _arg1, _arg2, _arg3, _arg4);
return true;
}
case TRANSACTION_getLocation:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.weather.mwg.aidl.WeatherResults _arg1;
_arg1 = com.weather.mwg.aidl.WeatherResults.Stub.asInterface(data.readStrongBinder());
this.getLocation(_arg0, _arg1);
return true;
}
case TRANSACTION_getPM2_5:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.weather.mwg.aidl.WeatherResults _arg1;
_arg1 = com.weather.mwg.aidl.WeatherResults.Stub.asInterface(data.readStrongBinder());
this.getPM2_5(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.weather.mwg.aidl.WeatherRequest
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
// for weather info

@Override public void getCurrentWeather(java.lang.String Weather, java.lang.String metric, long cnt, java.lang.String lang, com.weather.mwg.aidl.WeatherResults results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(Weather);
_data.writeString(metric);
_data.writeLong(cnt);
_data.writeString(lang);
_data.writeStrongBinder((((results!=null))?(results.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_getCurrentWeather, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
// for location                                  

@Override public void getLocation(java.lang.String location, com.weather.mwg.aidl.WeatherResults results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(location);
_data.writeStrongBinder((((results!=null))?(results.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_getLocation, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
//for pm2.5 info 

@Override public void getPM2_5(java.lang.String cityName, com.weather.mwg.aidl.WeatherResults results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(cityName);
_data.writeStrongBinder((((results!=null))?(results.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_getPM2_5, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_getCurrentWeather = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getLocation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getPM2_5 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
// for weather info

public void getCurrentWeather(java.lang.String Weather, java.lang.String metric, long cnt, java.lang.String lang, com.weather.mwg.aidl.WeatherResults results) throws android.os.RemoteException;
// for location                                  

public void getLocation(java.lang.String location, com.weather.mwg.aidl.WeatherResults results) throws android.os.RemoteException;
//for pm2.5 info 

public void getPM2_5(java.lang.String cityName, com.weather.mwg.aidl.WeatherResults results) throws android.os.RemoteException;
}
