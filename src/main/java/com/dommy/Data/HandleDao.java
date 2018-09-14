package com.dommy.Data;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HandleDao {
    private MyOpenHelper helper;
    public HandleDao(Context context){
        //创建Dao时，创建Helper
        helper=new MyOpenHelper(context);
    }
    public void insert(Handle handle){
        //获取数据库对象
        SQLiteDatabase db=helper.getWritableDatabase();
        //用来装载要插入的数据的Map<列名，列的值>
        ContentValues values=new ContentValues();
        values.put("bednum",handle.getBednum());
        values.put("name",handle.getName());
        values.put("gender",handle.getGender());
        values.put("age",handle.getAge());
        values.put("contact",handle.getContact());
        values.put("doctor",handle.getDoctor());
        values.put("IDcard",handle.getIDcard());
        values.put("ill",handle.getIll());
        //向account表插入数据values,
        long id=db.insert("ill",null,values);
        handle.setId(id);//得到id
        db.close();//关闭数据库
    }
    //根据id删除数据
   public int delete(long id){
        SQLiteDatabase db=helper.getWritableDatabase();
        //按条件删除指定表中的数据，返回受影响的行数
        int count=db.delete("ill","_id=?",new String[]{id+""});
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

    //读取数据库数据
    public void readData(List<String> list){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor = db.query("ill", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(name);
        }
        cursor.close();
    }
    //根据传入的条件查询
    public void readData(List<String> list,String s){
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql = "select * from name where age like"+"'%"+s+"%'";
        list.clear();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            String bookAuthor = cursor.getString(cursor.getColumnIndex("author"));
            list.add(bookAuthor);
        }
        cursor.close();
    }


    //查询所有数据倒序排列
  public List queryAll(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.query("ill",null,null,null,null,null,null);
        List list=new ArrayList<>();
        while(c.moveToNext()){
            //可以根据列名获取索引
            long id=c.getLong(c.getColumnIndex("_id"));
            String bednum=c.getString(1);
            String name=c.getString(2);
            String gender=c.getString(3);
            String age=c.getString(4);
            String contact=c.getString(5);
            String doctor=c.getString(6);
            String IDcard=c.getString(7);
            String ill=c.getString(8);
            list.add(new Handle(id,bednum,name,gender,age,contact,doctor,IDcard,ill));
        }
        c.close();
        db.close();
        return list;
    }
}
