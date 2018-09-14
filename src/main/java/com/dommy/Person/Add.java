package com.dommy.Person;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dommy.Data.Handle;
import com.dommy.Data.HandleDao;
import com.dommy.Data.MyOpenHelper;
import com.dommy.R;
import com.dommy.Searching;

import java.util.List;

public class Add extends AppCompatActivity implements TextWatcher {

    private Button button;
    public static final String action4="hhhhhhhhh";
    private MyOpenHelper dbHelper;


    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;
    private EditText editText8;
    private EditText editText9;
    private EditText editText10;
    private EditText editText11;


    private String name;
    private String age;
    private String address;
    private String family;
    private String sex;
    private String phone;
    private String doctor;
    private String ills;
    private String illhouse;
    private String illbed;
    private String oldills;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper = new MyOpenHelper(this);


        final ImageButton imageButton=findViewById(R.id.back_search);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Add.this, Searching.class);
                startActivity(intent);
                finish();
            }
        });
        button=findViewById(R.id.btn_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
                Intent intent=new Intent();
                intent.setAction(action4);
                sendBroadcast(intent);
            }
        });

    }



       public void Add() {
           editText = findViewById(R.id.ill_name);
           editText2 = findViewById(R.id.ill_age);
           editText3 = findViewById(R.id.ill_address);
           editText4 = findViewById(R.id.ill_sex);
           editText5 = findViewById(R.id.ill_family);
           editText6 = findViewById(R.id.ill_phone);
           editText7 = findViewById(R.id.ill_doctor);
           editText8 = findViewById(R.id.ill_ills);
           editText9 = findViewById(R.id.ill_illhouse);
           editText10 = findViewById(R.id.ill_illbed);
           editText11 = findViewById(R.id.ill_oldills);


           name = editText.getText().toString();
           age = editText2.getText().toString();
           address = editText3.getText().toString();
           sex = editText4.getText().toString();
           family = editText5.getText().toString();
           phone = editText6.getText().toString();
           doctor = editText7.getText().toString();
           ills = editText8.getText().toString();
           illhouse = editText9.getText().toString();
           illbed = editText10.getText().toString();
           oldills = editText11.getText().toString();


 /*       if (name!=null && age!=null && address!=null && sex!=null && family!=null && phone!=null && doctor!=null && ills!=null && illhouse!=null && illbed!=null){
            //saveFile(local);
            //HandleDao handleDao=new HandleDao(this);
            //Handle handle=new Handle();
            //handleDao.insert(handle);*/

           if (name!=null) {
               register(name, age, address, sex, family, phone, doctor, ills, illhouse, illbed, oldills);
               Toast.makeText(this, "保存成功!", Toast.LENGTH_SHORT).show();
           }
           else {
               Toast.makeText(this,"请填写所有必填项!",Toast.LENGTH_SHORT).show();
           }


       }

    //向数据库插入数据
   public boolean register(String name, String age,String address,String sex,String family,String phone,String doctor,String ills,String illhouse,String illbed,String oldills){
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        //String sql = "insert into userData(name,password) value(?,?)";
        //Object obj[]={username,password};
        //db.execSQL(sql,obj);
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("age",age);
        values.put("address",address);
        values.put("sex",sex);
        values.put("family",family);
        values.put("phone",phone);
        values.put("doctor",doctor);
        values.put("ills",ills);
        values.put("illhouse",illhouse);
        values.put("illbed",illbed);
        values.put("oldills",oldills);
        db.insert("ill",null,values);
        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      //  button.setEnabled(false);//在这里重复设置，以保证清除任意EditText中的内容，按钮重新变回不可点击状态
        //button.setBackgroundResource(R.drawable.shape_before);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        boolean Sign1 =name.length()>0;  //判断当前的edittext是否有内容
        boolean Sign2 =age.length()>0;

        if(Sign1 && Sign2) {

            button.setEnabled(true);

            button.setBackgroundResource(R.drawable.shape_after);
        }
        else
        {
            button.setBackgroundResource(R.drawable.shape_before);
            Toast.makeText(Add.this,"请填写所有必填项!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    /*    if(!(editText.getText().toString().equals("") || editText2.getText().toString().equals("") || editText3.getText().toString().equals("")))
        {
            //if (editText.getText().toString()!=null && editText2.getText().toString()!=null && editText3.getText().toString()!=null && editText4.getText().toString()!=null && editText5.getText().toString()!=null && editText6.getText().toString()!=null && editText7.getText().toString()!=null && editText8.getText().toString()!=null && editText9.getText().toString()!=null && editText10.getText().toString()!=null && editText11.getText().toString()!=null) {
            button.setBackgroundResource(R.drawable.shape_after);
            button.setEnabled(true);
        }*/
    }
}

