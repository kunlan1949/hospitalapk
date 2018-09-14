package com.dommy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dommy.Data.DbDao;
import com.dommy.Data.Handle;
import com.dommy.Data.HandleDao;
import com.dommy.Data.MyOpenHelper;
import com.dommy.Http.App;
import com.dommy.Http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class IllsHouse extends AppCompatActivity {
    public static final String TAG = "App";
//    String url="http://192.168.137.1:8081/examples/get.json";
String url="http://www.pythonhj.top/ad_patient_data";
  //  TextView responseText;
    SQLiteDatabase db;
    List<App> appList;

    private HandleDao dao;
    private List list;
    private MyAdapter2 myAdapter2;
    private ListView listView;
    private MyOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        sendRequestWithOkHttp();
        dao=new HandleDao(this);
        insertModelToDb(appList);
        list=dao.queryAll();
        myAdapter2=new MyAdapter2();
        listView=findViewById(R.id.list_show);
        listView.setAdapter(myAdapter2);
        myAdapter2.notifyDataSetChanged(); // 刷新界面
     //   responseText = (TextView) findViewById(R.id.get_request);


        Button button1=findViewById(R.id.btn_1_C);
        Button button2=findViewById(R.id.btn_2_C);
        Button button3=findViewById(R.id.btn_3_C);
        Button button4=findViewById(R.id.btn_4_C);

        button1.setOnClickListener(l);
        button2.setOnClickListener(l);
        button3.setOnClickListener(l);
        button4.setOnClickListener(l);


        TextView textView=findViewById(R.id.sql);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });
        ImageButton sqlif=findViewById(R.id.refresh);
        sqlif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertModelToDb(appList);
                list=dao.queryAll();
                myAdapter2=new MyAdapter2();
                listView=findViewById(R.id.list_show);
                listView.setAdapter(myAdapter2);
                myAdapter2.notifyDataSetChanged(); // 刷新界面
            }
        });

    }
    View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_1_C:
                    Intent intent=new Intent(IllsHouse.this,Searching.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_2_C:
                    Intent intent2=new Intent(IllsHouse.this,Setting.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.btn_3_C:
                    Intent intent3=new Intent(IllsHouse.this,IllsHouse.class);
                    startActivity(intent3);
                    break;
                case R.id.btn_4_C:
                    Intent intent4=new Intent(IllsHouse.this,Mannager.class);
                    startActivity(intent4);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData=response.body().string();
                        parseJSONWithGSON(responseData);
                        //显示UI界面，调用的showResponse方法
                  //      showResponse(responseData.toString());
                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
    }
 /*   private void showResponse(final String response) {
        //在子线程中更新UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                responseText.setText(response);
            }
        });
    }*/




    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {}.getType());

        for(Iterator iterator = appList.iterator(); iterator.hasNext();){
            App app = (App) iterator.next();
            Log.e(TAG,app.getBednum()+app.getName()+app.getGender()+app.getAge()+app.getContact()+app.getDoctor()+app.getIDcard()+app.getIll());
        }
        for (App app : appList) {
            //控制台输出结果，便于查看
            Log.d("bednum", "name=" + app.getBednum());
            Log.d("age", "age=" + app.getName());
            Log.d("sex", "sex=" + app.getGender());
            Log.d("address", "address=" + app.getAge());
            Log.d("family", "family=" + app.getContact());
            Log.d("doctor", "doctor=" + app.getDoctor());
            Log.d("oldills", "oldills=" + app.getIDcard());
            Log.d("ills", "ills=" + app.getIll());
        }
    }


    public class MyAdapter2 extends BaseAdapter {

        public int getCount() {                   // 获取条目总数
            return list.size();
        }

        public Object getItem(int position) { // 根据位置获取对象
            return list.get(position);
        }

        public long getItemId(int position) { // 根据位置获取id
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 重用convertView
            View item = convertView != null ? convertView : View.inflate(
                    getApplicationContext(), R.layout.item, null);
            // 获取该视图中的TextView
            TextView id =item.findViewById(R.id.num);
            TextView bednum=item.findViewById(R.id.bednum);
            TextView name =item.findViewById(R.id.name);
            TextView gender=item.findViewById(R.id.gender);
            TextView age=item.findViewById(R.id.age);
            TextView doctor =item.findViewById(R.id.doctor);
            TextView contact=item.findViewById(R.id.contact);
            TextView idcard=item.findViewById(R.id.idcard);
            TextView ill=item.findViewById(R.id.ill);

            // 根据当前位置获取Account对象


            final Handle handle = (Handle) list.get(position);
            // 把Account对象中的数据放到TextView中
            id.setText(handle.getId()+"");
            name.setText(handle.getName());
            doctor.setText(handle.getDoctor());
            bednum.setText(handle.getBednum());
            gender.setText(handle.getGender());
            idcard.setText(handle.getIDcard());
            ill.setText(handle.getIll());
            contact.setText(handle.getContact());
            age.setText(handle.getAge());
            //Button delete=findViewById(R.id.delete);

            //删除图片的点击事件触发的方法
          /*  delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //删除数据之前首先弹出一个对话框
                    android.content.DialogInterface.OnClickListener listener = new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(handle);          // 从集合中删除
                            dao.delete(handle.getId()); // 从数据库中删除
                            notifyDataSetChanged();// 刷新界面
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(IllsHouse.this); // 创建对话框
                    builder.setTitle("确定要删除吗?");                    // 设置标题
                    // 设置确定按钮的文本以及监听器
                    builder.setPositiveButton("确定", listener);
                    builder.setNegativeButton("取消", null);         // 设置取消按钮
                    builder.show(); // 显示对话框
                }
            }); */
            return item;
        }
    }


    /*-------------------------------------------------------------------------------------------------------------------------------------------------------*/
    public void insertModelToDb(List<App> appList){
        try{

            MyOpenHelper dbHelper = new MyOpenHelper(this);
            db = dbHelper.getWritableDatabase();
            //开始事务
            db.beginTransaction();
            Log.d(TAG,appList.size()+"");
            String sql1 = "insert into ill(_id,bednum,name,gender,age,contact,doctor,IDcard,ill) values (?,?,?,?,?,?,?,?,?)";
            for(App f :appList){
                db.execSQL(sql1,new Object[]{null,f.getBednum(),f.getName(),f.getGender(),f.getAge(),f.getContact(),f.getDoctor(),f.getIDcard(),f.getIll()});
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //提交
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
