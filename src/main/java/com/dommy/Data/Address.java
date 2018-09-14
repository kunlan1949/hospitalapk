package com.dommy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Address extends SQLiteOpenHelper{
    public static final String CREATE_USERDATA="create table Address(" +
            "id integer primary key autoincrement,"
            +"address text," +
            "time text," +
            "name text)";

    private Context CT;

    public Address(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
        CT=context;
    }

    public  void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERDATA);
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        //onCreate(db);
    }
}
