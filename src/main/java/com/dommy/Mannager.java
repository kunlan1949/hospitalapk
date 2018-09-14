package com.dommy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.dommy.Data.Handle;
import com.dommy.Data.MyOpenHelper;
import com.dommy.Data.Yao;
import com.dommy.Data.yaoDao;
import com.dommy.Data.yaoHelper;
import com.dommy.Http.App;
import com.dommy.Http.HttpUtil;
import com.dommy.Http.Item;
import com.dommy.View.VerticalProgressBar;
import com.dommy.xiangqing.Speed;
import com.dommy.xiangqing.Temperature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.angmarch.views.NiceSpinner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class Mannager extends AppCompatActivity  {

    public static final String action5="hhhhhhhhhh";
    List<Item> itemlist;
    String url="http://192.168.137.1:8081/examples/yaowu.json";
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addHotelNameView;

    private yaoDao yaoDao;
  //  private VerticalProgressBar vpProgressBar;
    private List list;
    private NiceSpinner niceSpinner;
    private NiceSpinner niceSpinner1;
    private MyAdapter3 myAdapter3;

    private int a;
    private int b;
    private  int amount=0;
    private boolean rel=false;
    private ListView listView;
    List<String> spinnerData = new LinkedList<>   (Arrays.asList("消息","1号","2号"));
    List<String> spinnerData2 = new LinkedList<>  ( Arrays.asList("监控中心", "药液流速", "患者体温"));
    SQLiteDatabase db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sendRequestWithOkHttp();

   //     addHotelNameView = (LinearLayout) findViewById(R.id.add_View);
//        findViewById(R.id.btn_getData).setOnClickListener(this);
        yaoDao=new yaoDao(this);
        list=yaoDao.queryAll();
        myAdapter3=new MyAdapter3();
        insertModelToDb(itemlist);
        Cursor c = db.rawQuery("select * from yao", null);
        amount=c.getCount();
        if(!rel) {
            if (amount != 0) {
                Toast.makeText(Mannager.this, "数据接受成功!", Toast.LENGTH_SHORT).show();
                rel=true;
            } else {
                Toast.makeText(Mannager.this, "数据接受失败!请检查网络连接!", Toast.LENGTH_SHORT).show();
                rel=false;
            }
        }
        listView=findViewById(R.id.list_item);
        listView.setAdapter(myAdapter3);
        myAdapter3.notifyDataSetChanged(); // 刷新界面

        //默认添加一个Item
      //  addViewItem(null);

        niceSpinner =findViewById(R.id.nice_spinner);
        niceSpinner.attachDataSource(spinnerData);
        niceSpinner.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner.setTextColor(Color.WHITE);
        niceSpinner.setBackgroundColor(Color.parseColor("#98AED7"));
        niceSpinner.setTextSize(13);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        insertModelToDb(itemlist);
                        myAdapter3.notifyDataSetChanged(); // 刷新界面
                        Toast.makeText(Mannager.this,"1！",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(Mannager.this,"1！",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        niceSpinner1 =findViewById(R.id.nice_spinner2);
        niceSpinner1.attachDataSource(spinnerData2);
        niceSpinner1.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner1.setBackgroundColor(Color.parseColor("#98AED7"));
        niceSpinner1.setTextColor(Color.WHITE);
        niceSpinner1.setTextSize(13);

        niceSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position){
                    case 0:
                        Intent intent1= new Intent(Mannager.this,Mannager.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case 1:
                        Intent intent2=new Intent(Mannager.this,Speed.class);
                        startActivity(intent2);
                        break;
                    case 2:

                        Intent intent=new Intent();
                        intent.setAction(action5);
                        sendBroadcast(intent);

                        Intent intent3=new Intent(Mannager.this, Temperature.class);
                        startActivity(intent3);

                        break;
                        default:
                            break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        Button button1=findViewById(R.id.btn_1_D);
        Button button2=findViewById(R.id.btn_2_D);
        Button button3=findViewById(R.id.btn_3_D);
        Button button4=findViewById(R.id.btn_4_D);

        button1.setOnClickListener(l);
        button2.setOnClickListener(l);
        button3.setOnClickListener(l);
        button4.setOnClickListener(l);

    }
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
                        // showResponse(responseData.toString());
                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        itemlist = gson.fromJson(jsonData, new TypeToken<List<Item>>() {}.getType());


        for(Iterator iterator = itemlist.iterator(); iterator.hasNext();){
            Item item = (Item) iterator.next();
            Log.e(TAG,item.getBednum_item()+item.getName_item()+item.getSpeed_item()+item.getSave_item());
        }
        for (Item item : itemlist) {
            //控制台输出结果，便于查看
            Log.d("bednum", "name=" + item.getBednum_item());
            Log.d("age", "age=" + item.getName_item());
            Log.d("speed", "sex=" + item.getSpeed_item());
            Log.d("save", "address=" + item.getSave_item());
        }
    }
    public class MyAdapter3 extends BaseAdapter {

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
                    getApplicationContext(), R.layout.item2, null);
            // 获取该视图中的TextView
            //TextView id =item.findViewById(R.id.id);
            TextView bednum_item=item.findViewById(R.id.bednum_item);
            TextView name_item =item.findViewById(R.id.name_item);
            TextView speed_item=item.findViewById(R.id.speed_item);
            TextView save_item=item.findViewById(R.id.save_item);

            // 根据当前位置获取Account对象


           final Yao yao = (Yao) list.get(position);
            // 把Account对象中的数据放到TextView中
          //  id.setText(handle.getId()+"");

            String str = yao.getSave_item();
            try {
                a= Integer.parseInt(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            name_item.setText(yao.getName_item());

            bednum_item.setText(yao.getBednum_item());
            String stt=yao.getSpeed_item();
            try {
                b= Integer.parseInt(stt);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if(b>=31){
                speed_item.setTextColor(Color.RED);
            }else if(b==0) {
                speed_item.setTextColor(Color.BLUE);
            }
            speed_item.setText(yao.getSpeed_item());
            if(a<=50){
                save_item.setTextColor(Color.RED);
            }else if(a==0)
            {
                save_item.setTextColor(Color.BLUE);
            }
            save_item.setText(yao.getSave_item());
           /* if(yao.getSave_item()<=20){
                save_item.setTextColor(Color.RED);
            }*/

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
    public void insertModelToDb(List<Item> itemlist){
        try{

            yaoHelper yaohelper = new yaoHelper(this);
            db = yaohelper.getWritableDatabase();
            //开始事务
            db.beginTransaction();
            Log.d(TAG,itemlist.size()+"");
            String sql = "insert into yao(id,bednum_item,name_item,speed_item,save_item) values (?,?,?,?,?)";
            for(Item I :itemlist){
                db.execSQL(sql,new Object[]{null,I.getBednum_item(),I.getName_item(),I.getSpeed_item(), I.getSave_item()});
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //提交
            db.setTransactionSuccessful();
            db.endTransaction();
          //  db.close();

        }
    }

    View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_1_D:
                    Intent intent=new Intent(Mannager.this,Searching.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_2_D:
                    Intent intent2=new Intent(Mannager.this,Setting.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.btn_3_D:
                    Intent intent3=new Intent(Mannager.this,IllsHouse.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.btn_4_D:
                    Intent intent4=new Intent(Mannager.this,Mannager.class);
                    startActivity(intent4);
                    break;
                default:
                    break;
            }
        }
    };


/*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_ills://点击添加按钮就动态添加Item
                addViewItem(v);
                break;
            /*case R.id.btn_getData://打印数据
                printData();
                break;
        }
    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //deleteDatabase(this);

    }
 /*  public boolean deleteDatabase(Context context) {
        return context.deleteDatabase("yao.db");
    }*/
    /**
     * Item排序
     */
    /*

    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addHotelNameView.getChildCount(); i++) {
            final View childAt = addHotelNameView.getChildAt(i);
            final Button btn_remove = (Button) childAt.findViewById(R.id.new_ills);
            btn_remove.setText("删除");
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    addHotelNameView.removeView(childAt);
                }
            });
            //如果是最后一个ViewItem，就设置为添加
            if (i == (addHotelNameView.getChildCount() - 1)) {
                Button btn_add = (Button) childAt.findViewById(R.id.new_ills);
                btn_add.setText("+新增");
                btn_add.setTag("add");
                btn_add.setOnClickListener(this);
            }
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (addHotelNameView.getChildCount() == 0) {//如果一个都没有，就添加一个
            View hotelEvaluateView = View.inflate(this, R.layout.add_ills, null);
            Button btn_add = (Button) hotelEvaluateView.findViewById(R.id.new_ills);
            btn_add.setText("+新增");
            btn_add.setTag("add");
            btn_add.setOnClickListener(this);
            addHotelNameView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else if (((String) view.getTag()).equals("add")) {//如果有一个以上的Item,点击为添加的Item则添加
            View hotelEvaluateView = View.inflate(this, R.layout.add_ills, null);
            addHotelNameView.addView(hotelEvaluateView);
            sortHotelViewItem();
        }
        //else {
        //  sortHotelViewItem();
        //}
    }

*/






    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
