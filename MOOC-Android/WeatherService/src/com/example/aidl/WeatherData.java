package com.example.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class is a Plain Old Java Object (POJO) used for data
 * transport within the WeatherService app.  This POJO implements the
 * Parcelable interface to enable IPC between the WeatherActivity and
 * the WeatherServiceSync and WeatherServiceAsync. It represents the
 * response Json obtained from the Open Weather Map API, e.g., a call
 * to http://api.openweathermap.org/data/2.5/weather?q=Nashville,TN
 * might return the following Json data:
 * 
 * { "coord":{ "lon":-86.78, "lat":36.17 }, "sys":{ "message":0.0138,
 * "country":"United States of America", "sunrise":1431427373,
 * "sunset":1431477841 }, "weather":[ { "id":802, "main":"Clouds",
 * "description":"scattered clouds", "icon":"03d" } ],
 * "base":"stations", "main":{ "temp":289.847, "temp_min":289.847,
 * "temp_max":289.847, "pressure":1010.71, "sea_level":1035.76,
 * "grnd_level":1010.71, "humidity":76 }, "wind":{ "speed":2.42,
 * "deg":310.002 }, "clouds":{ "all":36 }, "dt":1431435983,
 * "id":4644585, "name":"Nashville", "cod":200 }
 *
 * The meaning of these Json fields is documented at 
 * http://openweathermap.org/weather-data#current.
 *
 * Parcelable defines an interface for marshaling/de-marshaling
 * https://en.wikipedia.org/wiki/Marshalling_(computer_science)
 * to/from a format that Android uses to allow data transport between
 * processes on a device.  Discussion of the details of Parcelable is
 * outside the scope of this assignment, but you can read more at
 * https://developer.android.com/reference/android/os/Parcelable.html.
 */
public class WeatherData implements Parcelable {
    /*
     * These data members are the local variables that will store the
     * WeatherData's state
     */
    private String mName;
    public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public double getmSpeed() {
		return mSpeed;
	}

	public void setmSpeed(double mSpeed) {
		this.mSpeed = mSpeed;
	}

	public double getmDeg() {
		return mDeg;
	}

	public void setmDeg(double mDeg) {
		this.mDeg = mDeg;
	}

	public double getmTemp() {
		return mTemp;
	}

	public void setmTemp(double mTemp) {
		this.mTemp = mTemp;
	}

	public long getmHumidity() {
		return mHumidity;
	}

	public void setmHumidity(long mHumidity) {
		this.mHumidity = mHumidity;
	}

	public long getmSunrise() {
		return mSunrise;
	}

	public void setmSunrise(long mSunrise) {
		this.mSunrise = mSunrise;
	}

	public long getmSunset() {
		return mSunset;
	}

	public void setmSunset(long mSunset) {
		this.mSunset = mSunset;
	}

	private double mSpeed;
    private double mDeg;
    private double mTemp;
    private long mHumidity;
    private long mSunrise;
    private long mSunset;
    private double mPressure;
    private long mDate;
    private String mCountry;
    private String mIconID;

    public WeatherData(String mName, double mSpeed, double mDeg, double mTemp,
			long mHumidity, long mSunrise, long mSunset, double mPressure,
			long mDate, String mCountry, String mIconID) {
		super();
		this.mName = mName;
		this.mSpeed = mSpeed;
		this.mDeg = mDeg;
		this.mTemp = mTemp;
		this.mHumidity = mHumidity;
		this.mSunrise = mSunrise;
		this.mSunset = mSunset;
		this.mPressure = mPressure;
		this.mDate = mDate;
		this.mCountry = mCountry;
		this.mIconID = mIconID;
	}

	public double getmPressure() {
		return mPressure;
	}

	public void setmPressure(double mPressure) {
		this.mPressure = mPressure;
	}

	public long getmDate() {
		return mDate;
	}

	public void setmDate(long mDate) {
		this.mDate = mDate;
	}

	public String getmCountry() {
		return mCountry;
	}

	public void setmCountry(String mCountry) {
		this.mCountry = mCountry;
	}

	public String getmIconID() {
		return mIconID;
	}

	public void setmIconID(String mIconID) {
		this.mIconID = mIconID;
	}

	/**
     * Constructor
     * 
     * @param name
     * @param speed
     * @param deg
     * @param temp
     * @param humidity
     * @param sunrise
     * @param sunset
     */

    /**
     * Provides a printable representation of this object.
     */
    @Override
    public String toString() {
        return "WeatherData [name=" + mName 
            + ", speed=" + mSpeed
            + ", deg=" + mDeg 
            + ", temp=" + mTemp 
            + ", humidity=" + mHumidity 
            + ", sunrise=" + mSunrise 
            + ", sunset=" + mSunset + "]";
    }

    /*
     * BELOW THIS is related to Parcelable Interface.
     */

    /**
     * A bitmask indicating the set of special object types marshaled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write this instance out to byte contiguous memory.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeDouble(mSpeed);
        dest.writeDouble(mDeg);
        dest.writeDouble(mTemp);
        dest.writeLong(mHumidity);
        dest.writeLong(mSunrise);
        dest.writeLong(mSunset);
        dest.writeDouble(mPressure);
        dest.writeLong(mDate);
        dest.writeString(mCountry);
        dest.writeString(mIconID);

    }

    /**
     * Private constructor provided for the CREATOR interface, which
     * is used to de-marshal an WeatherData from the Parcel of data.
     * <p>
     * The order of reading in variables HAS TO MATCH the order in
     * writeToParcel(Parcel, int)
     *
     * @param in
     */
    private WeatherData(Parcel in) {
        mName = in.readString();
        mSpeed = in.readDouble();
        mDeg = in.readDouble();
        mTemp = in.readDouble();
        mHumidity = in.readLong();
        mSunrise = in.readLong();
        mSunset = in.readLong();
        mPressure = in.readDouble();
        mDate = in.readLong();
        mCountry = in.readString();
        mIconID = in.readString();
    }

    public WeatherData() {
		// TODO Auto-generated constructor stub
	}

	/**
     * public Parcelable.Creator for WeatherData, which is an
     * interface that must be implemented and provided as a public
     * CREATOR field that generates instances of your Parcelable class
     * from a Parcel.
     */
    public static final Parcelable.Creator<WeatherData> CREATOR =
        new Parcelable.Creator<WeatherData>() {
            public WeatherData createFromParcel(Parcel in) {
                return new WeatherData(in);
            }

            public WeatherData[] newArray(int size) {
                return new WeatherData[size];
            }
        };
}
