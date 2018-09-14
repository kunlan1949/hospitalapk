package com.dommy.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class yaoDao {
    private yaoHelper helper;
    public yaoDao(Context context){
        //创建Dao时，创建Helper
        helper=new yaoHelper(context);
    }
    public List queryAll(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.query("yao",null,null,null,null,null,null);
        List list=new ArrayList<>();
        while(c.moveToNext()){
            //可以根据列名获取索引
            long id=c.getLong(c.getColumnIndex("id"));
            String bednum_item=c.getString(1);
            String name_item=c.getString(2);
            String speed_item=c.getString(3);
            String save_item=c.getString(4);
            list.add(new Yao(id,bednum_item,name_item,speed_item,save_item));
        }
        c.close();
        db.close();
        return list;
    }
}
