package com.dommy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    private  static final String dbName = "temp.db";
    public DbHelper(Context context){
        super(context,dbName,null,1);
        this.context = context;
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "create table if not exists temperature(id integer primary key AUTOINCREMENT,Mon text,Tue text,Wed text,Thr text,Fri text,Sat text,Sun text)";
        db.execSQL(sql1);
        String speed_spll = "create table if not exists speeddata(id integer primary key AUTOINCREMENT,one text,second text,third text,fourth text,fifth text,sixth text)";
        db.execSQL(speed_spll);
    }

    //删除数据库
    public void deleteDb(){
        context.deleteDatabase(dbName);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

