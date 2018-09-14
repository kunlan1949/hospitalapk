package com.dommy.Help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dommy.MainActivity;
import com.dommy.R;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Button button=findViewById(R.id.help_to_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)

            return true;//不执行父类点击事件
        Toast.makeText(Help.this,"请点击屏幕内按钮返回!",Toast.LENGTH_SHORT).show();
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}
