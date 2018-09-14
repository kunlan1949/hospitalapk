package com.dommy.Http;

import android.os.Parcel;
import android.os.Parcelable;

public class people implements Parcelable {
    private Long id;
    private String bednum;
    private String name;
    private String gender;
    private String age;
    private String contact;
    private String doctor;
    private String IDcard;
    private String ill;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBednum() {
        return bednum;
    }
    public void setBednum(String bednum) {
        this.bednum = bednum;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
    public String getIDcard() {
        return IDcard;
    }
    public void setIDcard(String IDcard) {
        this.IDcard= IDcard;
    }
    public String getIll() {
        return ill;
    }
    public void setIll(String ill) {
        this.ill = ill;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.bednum);
        dest.writeString(this.name);
        dest.writeString(this.gender);
        dest.writeString(this.age);
        dest.writeString(this.contact);
        dest.writeString(this.doctor);
        dest.writeString(this.IDcard);
        dest.writeString(this.ill);
    }

    public people() {
    }

    protected people(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.bednum = in.readString();
        this.name = in.readString();
        this.gender = in.readString();
        this.age = in.readString();
        this.contact = in.readString();
        this.doctor = in.readString();
        this.IDcard = in.readString();
        this.ill = in.readString();
    }

    public static final Parcelable.Creator<people> CREATOR = new Parcelable.Creator<people>() {
        @Override
        public people createFromParcel(Parcel source) {
            return new people(source);
        }

        @Override
        public people[] newArray(int size) {
            return new people[size];
        }
    };
}
