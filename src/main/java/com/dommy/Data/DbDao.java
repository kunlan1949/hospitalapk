package com.dommy.Data;



import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbDao {
   /* private DbHelper helper;
    public DbDao(Context context){
        //创建Dao时，创建Helper
        helper=new DbHelper(context);
    }
    public void insert(Handle handle){
        //获取数据库对象
        SQLiteDatabase db=helper.getWritableDatabase();
        //用来装载要插入的数据的Map<列名，列的值>
        ContentValues values=new ContentValues();
        values.put("name",handle.getName());
        values.put("age",handle.getAge());
        values.put("address",handle.getAddress());
        values.put("sex",handle.getSex());
        values.put("family",handle.getFamily());
        values.put("phone",handle.getPhone());
        values.put("doctor",handle.getDoctor());
        values.put("ills",handle.getIlls());
        values.put("illhouse",handle.getIllhouse());
        values.put("illbed",handle.getIllbed());
        values.put("oldills",handle.getOldills());
        //向account表插入数据values,
        long id=db.insert("ill",null,values);
        handle.setId(id);//得到id
        db.close();//关闭数据库
    }
    //根据id删除数据
    public int delete(long id){
        SQLiteDatabase db=helper.getWritableDatabase();
        //按条件删除指定表中的数据，返回受影响的行数
        int count=db.delete("illsdata","_id=?",new String[]{id+""});
        db.close();
        return count;
    }
    //更新数据
  /*  public int update(Account account){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",handle.getName());
        values.put("balance",account.getBalance());
        int count=db.update("account",values,"_id=?",new String[]{account.getId()+""});
        db.close();
        return count;
    }*/


/*
    //查询所有数据倒序排列
    public List queryAll(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.query("illsdata",null,null,null,null,null,null);
        List list=new ArrayList<>();
        while(c.moveToNext()){
            //可以根据列名获取索引
            long id=c.getLong(c.getColumnIndex("id"));
            String name=c.getString(1);
            String illhouse=c.getString(10);
            String doctor=c.getString(7);
            String illbed=c.getString(11);
            list.add(new Handle(id,name,doctor,illhouse,illbed));
        }
        c.close();
        db.close();
        return list;
    }*/
}
