package com.dommy;

/**                           _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
*/

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dommy.Data.Data;
import com.dommy.Data.DataBase;
import com.dommy.Logo.Logo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends Activity implements ViewPager.OnPageChangeListener  {
    private EditText edit_Username;// 定义一个输入用户名的编辑框组件
    private EditText edit_Password;// 定义一个输入密码的编辑框组件
    private DataBase dataBase;
    private CheckBox box;
    File file = null;
    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private long exit=0;

    public static final String action3 = "hahahahahahahahaha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteStudioService.instance().start(this);



        dataBase = new DataBase(this, "UserStore.db", null, 1);
        TextView login = findViewById(R.id.to_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        box = (CheckBox) findViewById(R.id.checkbox);
        edit_Username = (EditText) findViewById(R.id.name);         //获取用于输入用户名的编辑框组件
        edit_Password = (EditText) findViewById(R.id.password);     //获取用于输入密码的编辑框组件
        Button button2 = findViewById(R.id.login_in);           //获取用于登录的按钮控件

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked(v);
               // Intent intent=new Intent(action3);
                //sendBroadcast(intent);
            }
        });


        initViews();

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();

        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();


    }

    @SuppressLint("ShowToast")
    public void login(View v) throws IOException {
        String name = edit_Username.getText().toString();
        String pwd = edit_Password.getText().toString();
        FileOutputStream fos = null;
        // 判断是否有勾选记住密码
        if (box.isChecked()) {
            try {
                /*
                 * getFilesDir()路径其实就是data/data/项目包/files 安卓中，每一个程序只能在自己的包下进行读写。
                 * 例如，本例子中，其实路径就是 data/data/com.examle.mylogin/files/info.txt
                 * 这里补充一点，如果文件要写在sd卡上，那么路径为storage/sdcard/info.txt,注意，写在sd卡是要添加读写权限的。
                 * 当然咯，路径不用自己写，可以用api获取，Environment.getExternalStorageDirectory()
                 * android.permission.READ_EXTERNAL_STORAGE，android.permission.WRITE_EXTERNAL_STORAGE
                 */
                file = new File(getFilesDir(), "info.txt");
                fos = new FileOutputStream(file);
                // 将name和pwd转化为字节数组写入。##是为了方便待会分割
                fos.write((name + "##" + pwd).getBytes());
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

            } finally {
                if (fos != null) {
                    fos.close();
                }
            }

        } else {
            // 如果用户没有勾选记住密码，就判断file是否存在，存在就删除
            if (file.exists()) {
                file.delete();
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 加载账户密码的方法
    public void load() throws IOException {
        FileInputStream fiStream = null;
        BufferedReader br = null;
        file = new File(getFilesDir(), "info.txt");
        if (file.exists()) {
            try {
                fiStream = new FileInputStream(file);
                /* 将字节流转化为字符流，转化是因为我们知道info.txt
                 * 只有一行数据，为了使用readLine()方法，所以我们这里
                 * 转化为字符流，其实用字节流也是可以做的。但比较麻烦
                 */
                br = new BufferedReader(new InputStreamReader(fiStream));
                //读取info.txt
                String str = br.readLine();
                //分割info.txt里面的内容。这就是为什么写入的时候要加入##的原因
                String arr[] = str.split("##");
                edit_Username.setText(arr[0]);
                edit_Password.setText(arr[1]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        } else {

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
//		viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);

        tv_desc = (TextView) findViewById(R.id.tv_desc);

    }

    /**
     * 初始化要显示的数据
     */
    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述
        contentDescs = new String[]{
                "",
                "",
                "",
                "",
                ""
        };
        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.ic_point);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//			newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

//		for (int i = 0; i < ll_point_container.getChildCount(); i++) {
//			View childAt = ll_point_container.getChildAt(position);
//			childAt.setEnabled(position == i);
//		}
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }



    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //点击登录按钮
    public void loginClicked(View view) {
        edit_Username = (EditText) findViewById(R.id.name);
        edit_Password = (EditText) findViewById(R.id.password);
        String userName = edit_Username.getText().toString();
        String passWord = edit_Password.getText().toString();
        if (login(userName, passWord)) {
            Toast.makeText(MainActivity.this, "登陆成功!" + "你好" + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Logo.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(MainActivity.this, "登陆失败,请检查用户名或密码!", Toast.LENGTH_SHORT).show();
        }
        //判断是否保存密码
        if(box.isChecked())
        {
            //保存用户密码
            boolean result = Data.saveUserInfo(MainActivity.this, edit_Username.getText().toString(), edit_Password.getText().toString());
            if (result) {
                Toast.makeText(MainActivity.this, "保存数据成功", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    //验证登录
    public boolean login(String username,String password) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        String sql = "select * from userData where name=? and password=? ";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exit(){
        if((System.currentTimeMillis()-exit)>2000){
            Toast.makeText(MainActivity.this,"再按一次退出程序!",Toast.LENGTH_SHORT).show();
            exit=System.currentTimeMillis();
        }else {
            finish();
            System.exit(0);
        }
    }
}
