package com.dommy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class yaoHelper extends SQLiteOpenHelper {
    public yaoHelper(Context context) {
        super(context,"yao.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table yao(id integer primary key autoincrement,bednum_item text,name_item text,speed_item text,save_item text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        System.out.print("数据库升级了!!!");

    }
}
