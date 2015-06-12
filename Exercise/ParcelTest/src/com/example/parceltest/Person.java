package com.example.parceltest;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	private int age;
	private String name;
	private String sex;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(age);
		dest.writeString(name);
		dest.writeString(sex);
	}
	
	public static final Parcelable.Creator<Person> CREATOR = 
			new Parcelable.Creator<Person>() {

				@Override
				public Person createFromParcel(Parcel source) {
					return new Person(source);
				}

				@Override
				public Person[] newArray(int size) {
					return new Person[size];
				}
			};
			
			
			public Person(Parcel source) {
				age = source.readInt();
				name = source.readString();
				sex = source.readString();
			}
			public Person() {
				age = 18;
				name = "haha";
				sex = "woman";
			}

}
