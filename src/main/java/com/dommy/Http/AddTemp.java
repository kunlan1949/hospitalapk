package com.dommy.Http;


import android.os.Parcel;
import android.os.Parcelable;

import com.dommy.Data.DbHelper;

import java.util.Set;

public class AddTemp implements Parcelable {


    private String Mon;

    private String Tue;

    private String Wed;

    private String Thr;

    private String Fri;

    private String Sat;

    private String Sun;

    private String one;

    private String second;

    private String third;

    private String fourth;

    private String fifth;

    private String sixth;



    public String getMon() {
        return Mon;
    }

    public void setMon(String Mon) {
        this.Mon = Mon;
    }

    public String getTue() {
        return Tue;
    }

    public void setTue(String Tue) {
        this.Tue = Tue;
    }

    public String getWed() {
        return Wed;
    }

    public void setWed(String Wed) {
        this.Wed = Wed;
    }

    public String getThr() {
        return Thr;
    }

    public void setThr(String Thr) {
        this.Thr = Thr;
    }

    public String getFri() {
        return Fri;
    }

    public void setFri(String Fri) {
        this.Fri = Fri;
    }

    public String getSat() {
        return Sat;
    }

    public void setSat(String Sat) {
        this.Sat = Sat;
    }

    public String getSun() {
        return Sun;
    }

    public void setSun(String Sun) {
        this.Sun = Sun;
    }

    public String getOne(){
        return one;
    }
    public void setOne(String one) {
        this.one = one;
    }
    public String getSecond(){
        return second;
    }
    public void setSecond(String second) {
        this.second = second;
    }
    public String getThird(){
        return third;
    }
    public void setThird(String third) {
        this.third = third;
    }
    public String getFourth(){
        return fourth;
    }
    public void setFourth(String fourth) {
        this.fourth= fourth;
    }
    public String getFifth(){
        return fifth;
    }
    public void setFifth(String fifth) {
        this.fifth = fifth;
    }
    public String getSixth(){
        return sixth;
    }
    public void setSixth(String sixth) {
        this.sixth = sixth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Mon);
        dest.writeString(this.Tue);
        dest.writeString(this.Wed);
        dest.writeString(this.Thr);
        dest.writeString(this.Fri);
        dest.writeString(this.Sat);
        dest.writeString(this.Sun);
        dest.writeString(this.one);
        dest.writeString(this.second);
        dest.writeString(this.third);
        dest.writeString(this.fourth);
        dest.writeString(this.fifth);
        dest.writeString(this.sixth);
    }

    public AddTemp() {
    }

    protected AddTemp(Parcel in) {
        this.Mon = in.readString();
        this.Tue = in.readString();
        this.Wed = in.readString();
        this.Thr = in.readString();
        this.Fri = in.readString();
        this.Sat = in.readString();
        this.Sun = in.readString();
        this.one = in.readString();
        this.second = in.readString();
        this.third = in.readString();
        this.fourth = in.readString();
        this.fifth = in.readString();
        this.sixth = in.readString();
    }

    public static final Parcelable.Creator<AddTemp> CREATOR = new Parcelable.Creator<AddTemp>() {
        @Override
        public AddTemp createFromParcel(Parcel source) {
            return new AddTemp(source);
        }

        @Override
        public AddTemp[] newArray(int size) {
            return new AddTemp[size];
        }
    };
}