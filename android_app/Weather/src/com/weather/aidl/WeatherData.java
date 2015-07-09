package com.weather.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData implements Parcelable{

	private double mSpeed;
    private double mDeg;
    private double mTemp;
    private double mTempMax;
    private double mTempMin;
    private long mHumidity;
    private double mPressure;
    private long mDate;
    private String mCountry;
    private String mName;
    private String mIconID;
    private String mDescription;

	

	public WeatherData(double mSpeed, double mDeg, double mTemp,
			double mTempMax, double mTempMin, long mHumidity, double mPressure,
			long mDate, String mCountry, String mName, String mIconID,
			String mDescription) {
		super();
		this.mSpeed = mSpeed;
		this.mDeg = mDeg;
		this.mTemp = mTemp;
		this.mTempMax = mTempMax;
		this.mTempMin = mTempMin;
		this.mHumidity = mHumidity;
		this.mPressure = mPressure;
		this.mDate = mDate;
		this.mCountry = mCountry;
		this.mName = mName;
		this.mIconID = mIconID;
		this.mDescription = mDescription;
	}
	
	

	public double getmTempMax() {
		return mTempMax;
	}



	public void setmTempMax(double mTempMax) {
		this.mTempMax = mTempMax;
	}



	public double getmTempMin() {
		return mTempMin;
	}



	public void setmTempMin(double mTempMin) {
		this.mTempMin = mTempMin;
	}



	public String getmDescription() {
		return mDescription;
	}



	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
	}



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
        dest.writeDouble(mTempMax);
        dest.writeDouble(mTempMin);
        dest.writeLong(mHumidity);
        dest.writeDouble(mPressure);
        dest.writeLong(mDate);
        dest.writeString(mCountry);
        dest.writeString(mIconID);
        dest.writeString(mDescription);

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
        mTempMax = in.readDouble();
        mTempMin = in.readDouble();
        mHumidity = in.readLong();
        mPressure = in.readDouble();
        mDate = in.readLong();
        mCountry = in.readString();
        mIconID = in.readString();
        mDescription = in.readString();
    }

    public WeatherData() {
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
