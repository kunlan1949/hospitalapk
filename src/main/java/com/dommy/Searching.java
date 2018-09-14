package com.dommy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dommy.Data.Handle;
import com.dommy.Data.HandleDao;
import com.dommy.Data.MyOpenHelper;
import com.dommy.Person.Add;

import java.util.ArrayList;
import java.util.List;

public class Searching extends AppCompatActivity {
    private HandleDao dao;
    private List list;
    private MyAdapter myAdapter;
    private ListView listView;
    private MyOpenHelper dbHelper;

   // List<Handle> handleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
       // dbHelper = new MyOpenHelper(this);
        dao=new HandleDao(this);


       /* SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c=db.query("ills",null,null,null,null,null,null);


       handleList=new ArrayList<>();
        while(c.moveToNext()){
            //可以根据列名获取索引
            long id=c.getLong(c.getColumnIndex("_id"));
            String name=c.getString(c.getColumnIndex("name"));
            String illhouse=c.getString(c.getColumnIndex("illhouse"));
            String doctor=c.getString(c.getColumnIndex("doctor"));
            String illbed=c.getString(c.getColumnIndex("illbed"));


            //Handle h=new Handle(id,name,doctor,illhouse,illbed);
           // handleList.add(h);
        }*/

        IntentFilter filter = new IntentFilter(Add.action4);
        registerReceiver(broadcastReceiver, filter);


        initView();
        dao=new HandleDao(this);
        list=dao.queryAll();
        myAdapter=new MyAdapter();
        myAdapter.notifyDataSetChanged(); // 刷新界面
        listView=findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        Button button1=findViewById(R.id.btn_1_A);
        Button button2=findViewById(R.id.btn_2_A);
        Button button3=findViewById(R.id.btn_3_A);
        Button button4=findViewById(R.id.btn_4_A);

        button1.setOnClickListener(l);
        button2.setOnClickListener(l);
        button3.setOnClickListener(l);
        button4.setOnClickListener(l);
        ImageButton imageButton=findViewById(R.id.addills);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Searching.this, Add.class);
                startActivity(intent);

            }
        });

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listView.setAdapter(myAdapter);
        }
    };


    private void initView() {
        listView=(ListView) findViewById(R.id.listView);
        // 添加监听器, 监听条目点击事件

        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    //activity_mian.xml 对应ImageView的点击事件触发的方法
 /*   public void add(View v) {
        String name = illname.getText().toString().trim();
        String doctor = illdoctor.getText().toString().trim();
        //三目运算 balance.equals(“”) 则等于0
        //如果balance 不是空字符串 则进行类型转换
        Handle a = new Handle(name,doctor);
        dao.insert(a);                      // 插入数据库
        list.add(a);                        // 插入集合
        myAdapter.notifyDataSetChanged(); // 刷新界面
        // 选中最后一个
        listView.setSelection(listView.getCount() - 1);
    }*/

    public class MyAdapter extends BaseAdapter {

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
                    TextView name =item.findViewById(R.id.name);
                    TextView doctor =item.findViewById(R.id.doctor);
                    TextView gender=item.findViewById(R.id.gender);
                    TextView idcard=item.findViewById(R.id.idcard);
                    TextView ill=item.findViewById(R.id.ill);
                    TextView contact=item.findViewById(R.id.contact);
                    TextView age=item.findViewById(R.id.age);
                    TextView bednum=item.findViewById(R.id.bednum);
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
                //    Button delete=Item.findViewById(R.id.delete);

            //   ImageView upIV = (ImageView) Item.findViewById(R.id.upIV);
            // ImageView downIV = (ImageView) Item.findViewById(R.id.downIV);
            //ImageView deleteIV = (ImageView) Item.findViewById(R.id.deleteIV);



       /*    //向上箭头的点击事件触发的方法
            upIV.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    a.setBalance(a.getBalance() + 1); // 修改值
                    notifyDataSetChanged(); // 刷新界面
                    dao.update(a); // 更新数据库
                }
            });
            //向下箭头的点击事件触发的方法
            downIV.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    a.setBalance(a.getBalance() - 1);
                    notifyDataSetChanged();
                    dao.update(a);
                }
            });
            */
            //删除图片的点击事件触发的方法
           /* delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //删除数据之前首先弹出一个对话框
                    android.content.DialogInterface.OnClickListener listener =
                            new android.content.DialogInterface.
                                    OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    list.remove(handle);          // 从集合中删除
                                    dao.delete(handle.getId()); // 从数据库中删除
                                    notifyDataSetChanged();// 刷新界面
                                }
                            };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Searching.this); // 创建对话框
                    builder.setTitle("确定要删除吗?");                    // 设置标题
                    // 设置确定按钮的文本以及监听器
                    builder.setPositiveButton("确定", listener);
                    builder.setNegativeButton("取消", null);         // 设置取消按钮
                    builder.show(); // 显示对话框
                }
            });*/
            return item;
        }
    }
    //ListView的Item点击事件
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 获取点击位置上的数据
            Handle a= (Handle) parent.getItemAtPosition(position);

            Bundle bundle=new Bundle();
            bundle.putIntegerArrayList("id", (ArrayList<Integer>) list);
            Intent intent=new Intent();
            intent.getBundleExtra("id");

            Toast.makeText(getApplicationContext(), a.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_1_A:
                    Intent intent=new Intent(Searching.this,Searching.class);
                    startActivity(intent);

                    break;
                case R.id.btn_2_A:
                    Intent intent2=new Intent(Searching.this,Setting.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.btn_3_A:
                    Intent intent3=new Intent(Searching.this,IllsHouse.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.btn_4_A:
                    Intent intent4=new Intent(Searching.this,Mannager.class);
                    startActivity(intent4);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
