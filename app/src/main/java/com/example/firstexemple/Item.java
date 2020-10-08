package com.example.firstexemple;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    public static final String TYPE_UNKNOWN = "unknown";
    public static final  String TYPE_INCOMES = "incomes";
    public static final  String TYPE_EXPENSES = "expenses";

    public int id;
    public String name;
    public String price;
    public String type;

    public Item(String title, String price, String type) {
        this.name = title;
        this.price = price;
        this.type = type;
    }


    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        type = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(type);
    }
}
