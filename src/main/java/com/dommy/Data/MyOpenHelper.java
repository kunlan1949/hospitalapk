package com.dommy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context,"ill.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ill(_id integer primary key autoincrement,bednum text,name text,gender text,age int(10),contact text,doctor text,IDcard text,ill text)");
     //   db.execSQL("create table yao(id integer primary key autoincrement,bednum_item text,name_item text,speed_item text,save_item text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        System.out.print("数据库升级了!!!");

    }

}
