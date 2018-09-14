package com.dommy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public static final String CREATE_USERDATA="create table userData(" +
            "id integer primary key autoincrement,"
            +"name text,"
            +"password text)";

    private Context mContext;

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
        mContext=context;
    }

    public  void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERDATA);
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        //onCreate(db);
    }

}