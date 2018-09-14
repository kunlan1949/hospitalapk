package com.dommy;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dommy.Data.Handle;
import com.dommy.Util.SlideSwitch;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Setting extends AppCompatActivity {

    private NiceSpinner niceSpinner2;
    public static final int one = 1;
    public static final int second = 2;
    public static final int third = 3;
    public static final int four = 4;
    public static final int five = 5;
    private AudioManager audioManager=null; //音频
    private TextView textView;

    List<String> spinnerData3 = new LinkedList<>(Arrays.asList("我", "切换登录", "退出"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button button1 = findViewById(R.id.btn_1_B);
        Button button2 = findViewById(R.id.btn_2_B);
        Button button3 = findViewById(R.id.btn_3_B);
        Button button4 = findViewById(R.id.btn_4_B);
        SwichState();
        button1.setOnClickListener(l);
        button2.setOnClickListener(l);
        button3.setOnClickListener(l);
        button4.setOnClickListener(l);

        ImageView imageView1 = findViewById(R.id.setting);
        ImageView imageView2 = findViewById(R.id.setting2);
//        final Switch aSwitch1 = findViewById(R.id.setting3);
//        Switch aSwitch2 = findViewById(R.id.setting4);
//        Switch aSwitch3 = findViewById(R.id.setting5);

        imageView1.setOnClickListener(m);
        imageView2.setOnClickListener(m);

//        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    Toast.makeText(Setting.this, "声音已打开", Toast.LENGTH_SHORT).show();
//                    .put("speak", aSwitch1.isChecked());
//                }
//                else
//                {
//                    Toast.makeText(Setting.this, "声音已关闭", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    Toast.makeText(Setting.this, "振动已打开", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(Setting.this, "振动已关闭", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        aSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    Toast.makeText(Setting.this, "已设置", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(Setting.this, "已恢复", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        TextView textView1 = findViewById(R.id.talk);
        TextView textView2 = findViewById(R.id.talk2);
        TextView textView3 = findViewById(R.id.talk3);
        TextView textView4 = findViewById(R.id.talk4);
        TextView textView5 = findViewById(R.id.talk5);

        textView1.setOnClickListener(t);
        textView2.setOnClickListener(t);
        textView3.setOnClickListener(t);
        textView4.setOnClickListener(t);
        textView5.setOnClickListener(t);

        niceSpinner2 = findViewById(R.id.nice_spinner3);
        niceSpinner2.attachDataSource(spinnerData3);
        niceSpinner2.setBackgroundColor(Color.parseColor("#98AED7"));
        niceSpinner2.setTextColor(Color.WHITE);
        niceSpinner2.setTextSize(13);


        niceSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Intent intent = new Intent(Setting.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:

                        AlertDialog builder = new AlertDialog.Builder(Setting.this).create(); // 创建对话框
                        builder.setTitle("确定要退出吗?");                    // 设置标题
                        // 设置确定按钮的文本以及监听器
                        builder.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        builder.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (DialogInterface.OnClickListener) null);         // 设置取消按钮
                        builder.show(); // 显示对话框
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //  textView = findViewById(R.id.text_known);
    }

    private void SwichState() {

        final boolean falg = true;
        SharedPreferences preferences;

        Switch aSwitch1= (Switch) findViewById(R.id.setting3);

        // 从SharedPreferences获取数据:
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (preferences != null) {
            boolean name = preferences.getBoolean("flag", falg);
            aSwitch1.setChecked(name);
        }
        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //将数据保存至SharedPreferences:
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
            //        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, !b); //设置是否静音
//                    int max;
//                    int current;
//                    max = audioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
//                    current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
//                    Log.d("MUSIC", "max : " + max + " current : " + current);
                    Toast.makeText(Setting.this, "声音已打开", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("flag", true);
                    editor.commit();
                } else {
                    //将数据保存至SharedPreferences:
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
               //     audioManager.setStreamMute(AudioManager.STREAM_MUSIC, b); //设置是否静音
                    Toast.makeText(Setting.this, "声音已关闭", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("flag", false);
                    editor.commit();
                }
            }
        });

        final boolean falg2 = true;
        Switch aSwitch2= (Switch) findViewById(R.id.setting4);
        // 从SharedPreferences获取数据:
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (preferences != null) {
            boolean name = preferences.getBoolean("flag2", falg2);
            aSwitch2.setChecked(name);
        }
        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //将数据保存至SharedPreferences:
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    Toast.makeText(Setting.this, "振动已打开", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("flag2", true);
                    editor.commit();
                } else {
                    //将数据保存至SharedPreferences:
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    Toast.makeText(Setting.this, "振动已关闭", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("flag2", false);
                    editor.commit();
                }
            }
        });

        final boolean falg3 = true;

        Switch aSwitch3= (Switch) findViewById(R.id.setting5);

        // 从SharedPreferences获取数据:
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (preferences != null) {
            boolean name = preferences.getBoolean("flag3", falg3);
            aSwitch3.setChecked(name);
        }
        aSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //将数据保存至SharedPreferences:
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    Toast.makeText(Setting.this, "已设置", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("flag3", true);
                    editor.commit();
                } else {
                    //将数据保存至SharedPreferences:
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    Toast.makeText(Setting.this, "已恢复", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("flag3", false);
                    editor.commit();
                }
            }
        });


    }



            @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case one:
                    textView.setText("打开本应用的设置界面，可以清除全部数据，更改权限设置");

                    break;
                case second:
                    textView.setText("点击后将会打开网络权限设置界面，可以开启或关闭网络数据");

                    break;
                case third:
                    textView.setText("点击后将关闭本应用的声音");

                    break;
                case four:
                    textView.setText("点击后将关闭振动!");

                    break;
                case five:
                    textView.setText("点击后切换主题颜色");
                default:
                    break;
            }
        }
    };




    View.OnClickListener t=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.talk:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=one;
                            handler.sendMessage(message);
                        }
                    });

                    Toast.makeText(Setting.this,"1",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.talk2:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=second;
                            handler.sendMessage(message);
                        }
                    });
                    Toast.makeText(Setting.this,"2",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.talk3:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=third;
                            handler.sendMessage(message);
                        }
                    });
                    Toast.makeText(Setting.this,"3",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.talk4:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=four;
                            handler.sendMessage(message);
                        }
                    });
                    Toast.makeText(Setting.this,"4",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.talk5:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=five;
                            handler.sendMessage(message);
                        }
                    });
                    Toast.makeText(Setting.this,"5",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    View.OnClickListener m=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting:
                    Uri packageURI = Uri.parse("package:" + "com.dommy");
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    startActivity(intent);
                    break;
                case R.id.setting2:
                    Intent intent2= new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                    startActivity(intent2);
                    break;
//                case R.id.setting3:
//                    voice();
//                    Toast.makeText(Setting.this,"已关闭应用声音!",Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.setting4:
//                    Toast.makeText(Setting.this,"已关闭应用振动!",Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.setting5:
//                    Toast.makeText(Setting.this,"已更换主题!",Toast.LENGTH_SHORT).show();
//                    break;
                default:
                    break;
            }
        }
    };
    public void voice(){
        try {
            AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//设置声音模式
            audioManager.setMode(AudioManager.STREAM_MUSIC);
//关闭麦克风
            audioManager.setMicrophoneMute(false);
// 打开扬声器
            audioManager.setSpeakerphoneOn(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_1_B:
                    Intent intent=new Intent(Setting.this,Searching.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_2_B:
                    Intent intent2=new Intent(Setting.this,Setting.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_3_B:
                    Intent intent3=new Intent(Setting.this,IllsHouse.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.btn_4_B:
                    Intent intent4=new Intent(Setting.this,Mannager.class);
                    startActivity(intent4);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
