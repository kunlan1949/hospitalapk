package com.dommy.Http;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private Long id;
    private String bednum_item;
    private String name_item;
    private String speed_item;
    private String save_item;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBednum_item() {
        return bednum_item;
    }
    public void setBednum_item(String bednum_item) {
        this.bednum_item = bednum_item;
    }
    public String getName_item() {
        return name_item;
    }
    public void setName_item(String name_item) {
        this.name_item = name_item;
    }
    public String getSpeed_item() {
        return speed_item;
    }
    public void setSpeed_item(String speed_item) {
        this.speed_item = speed_item;
    }
    public String getSave_item() {
        return save_item;
    }
    public void setSave_item(String save_item) {
        this.save_item = save_item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.bednum_item);
        dest.writeString(this.name_item);
        dest.writeString(this.speed_item);
        dest.writeString(this.save_item);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.bednum_item = in.readString();
        this.name_item = in.readString();
        this.speed_item = in.readString();
        this.save_item = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
