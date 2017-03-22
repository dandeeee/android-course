package com.dandeeee.advanceddbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    public int personId;
    public String name;
    public String address;
    public String phone;
    public String email;
    public String date;
    public String time;
    public float rating;
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public Person() {
		super();
	}

	public int describeContents() {
		return 10;
	}

	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeString(this.name);
		parcel.writeString(this.address);
		parcel.writeString(this.phone);
		parcel.writeString(this.email);
		parcel.writeString(this.date);
		parcel.writeString(this.time);
		parcel.writeFloat(this.rating);
		parcel.writeInt(this.personId);
	}
	
	public static Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		public Person createFromParcel(Parcel parcel) {
			Person person = new Person();
			
			person.name = parcel.readString();
			person.address = parcel.readString();
			person.phone = parcel.readString();
			person.email = parcel.readString();
			person.date = parcel.readString();
			person.time = parcel.readString();
			person.rating = parcel.readFloat();
			person.personId = parcel.readInt();
			
			return person;
		}

		public Person[] newArray(int arg0) {
			return null;
		}
		
	};
}
