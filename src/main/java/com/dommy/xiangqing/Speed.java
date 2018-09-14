package com.dommy.xiangqing;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dommy.Data.DbHelper;
import com.dommy.Http.AddTemp;
import com.dommy.Http.HttpUtil;
import com.dommy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 动态加载数据的折线图
 */
public class Speed extends AppCompatActivity {


    private LineChartView chart;
    public static final String TAG = "App";


    private TextView textView;
    SQLiteDatabase db;
    List<AddTemp> addTempList;  //定义addTempList数组对象，获取来自网络的数据

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private Axis axisX;
    private Axis axisY;

    private ProgressDialog progressDialog;
    private boolean isBiss;
    //数据库对象
    private DbHelper dbHelper;
    String url="http://192.168.137.1:8081/examples/temp.json";              //获取服务器文件所用的链接
    //定义X轴和点的数据             //改
    private float[] data_point = new float[6];
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        sendRequestWithOkHttp();               //发动一次请求

        insertModelToDb(addTempList);   //插入数据进数据库
        progressDialog = new ProgressDialog(this);

        chart = (LineChartView) findViewById(R.id.line_liusu);
//        generateData(7);//原
        readDataFromDatabase();

        final ImageButton imageButton=findViewById(R.id.speed_back_Manager);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                startActivity(intent);
                finish();
            }
        });
    }

    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        addTempList = gson.fromJson(jsonData, new TypeToken<List<AddTemp>>() {}.getType());                             //使gson解析的数据和AddTemp定义的对象同步(列表数据同步)

        for(Iterator iterator = addTempList.iterator(); iterator.hasNext();){                                       //遍历addTempList列表进行选择
            AddTemp addTemp = (AddTemp) iterator.next();
            Log.e(TAG,addTemp.getOne()+addTemp.getSecond()+addTemp.getThird()+addTemp.getFourth()+addTemp.getFifth()+addTemp.getSixth());             //获取数据
        }
        for (AddTemp addTemp : addTempList) {                            //打印数据
            //控制台输出结果，便于查看
            Log.d("Temperature", "one=" + addTemp.getOne());
            Log.d("Temperature", "second=" + addTemp.getSecond());
            Log.d("Temperature", "third=" + addTemp.getThird());
            Log.d("Temperature", "fourth=" + addTemp.getFourth());
            Log.d("Temperature", "fifth=" + addTemp.getFifth());
            Log.d("Temperature", "sixth=" + addTemp.getSixth());
        }
    }
    public void sendRequestWithOkHttp() {                                                           //发送请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        //接受数据并定义数组对象存储，这一部分是列表操作里的，不用管
                        // |                                                      ||
                        //|                                                       ||
                        //|                                                       ||
                        String responseData=response.body().string();
                        parseJSONWithGSON(responseData);
                        //显示UI界面，调用的showResponse方法
                        insertModelToDb(addTempList);
                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
        return;
    }

    public void insertModelToDb(List<AddTemp> addTempList){
        try{

            DbHelper dbHelper = new DbHelper(this);
            db = dbHelper.getWritableDatabase();
            //开始事务
            db.beginTransaction();
            Log.d(TAG,addTempList.size()+"");
            String sql1 = "insert into speeddata(id,one,second,third,fourth,fifth,sixth) values (?,?,?,?,?,?,?)";               //插入数据的键值对应关系
            for(AddTemp A :addTempList){
                db.execSQL(sql1,new Object[]{null,A.getOne(),A.getSecond(),A.getThird(),A.getFourth(),A.getFifth(),A.getSixth()});          /**括号里的内容意思为（   键值对应关系       ，    定义的字符串数组【从addtemplist中获取数据 ===> A.get() 】  ）*/
            }                                                                                                                              //每一个键都对应了数组排列的顺序，对应的是值的传入
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //提交
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }
    }

    /*----------------------------------------------------------------------------------------------------------------------------------*/
    private void readDataFromDatabase(){
        dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询表中数据
        Cursor cursor = db.query("speeddata",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                data_point[0] = Float.valueOf(cursor.getString(cursor.getColumnIndex("one"))); //one
                data_point[1] = Float.valueOf(cursor.getString(cursor.getColumnIndex("second"))); //second
                data_point[2] = Float.valueOf(cursor.getString(cursor.getColumnIndex("third"))); //third
                data_point[3] = Float.valueOf(cursor.getString(cursor.getColumnIndex("fourth"))); //fourth
                data_point[4] = Float.valueOf(cursor.getString(cursor.getColumnIndex("fifth"))); //fifth
                data_point[5] = Float.valueOf(cursor.getString(cursor.getColumnIndex("sixth"))); //sixth

                Log.d("Speed","one = "+data_point[0]+"\nsecond = "+data_point[1]+"\nthird = "+data_point[2]+"\nfourth = "+data_point[3]+"\nfifth = "
                        +data_point[4] +"\nsixth = "+data_point[5]);
            }while(cursor.moveToNext());
        }
        cursor.close();
        for(int addtime=0,i=0 ; i< data_point.length ; i++,addtime++){
            String timeX_get = getNewTime().substring(getNewTime().indexOf("日")+1,getNewTime().length());
            String[] timeX_arr_str= timeX_get.split(":");
            //手动设置时间延迟为十分钟
            int[] timeX_arr_int  = new int[3];
            timeX_arr_int[0]= Integer.parseInt(timeX_arr_str[0]);
            timeX_arr_int[1]= Integer.parseInt(timeX_arr_str[1]);
            timeX_arr_int[2]= Integer.parseInt(timeX_arr_str[2]);
            int h= addtime*10+timeX_arr_int[1];
                timeX_arr_int[0] += h/60;
                timeX_arr_int[1] = h%60;
            timeX_arr_str[0]= String.valueOf(timeX_arr_int[0]);
                if(timeX_arr_int[1]<10){
                    timeX_arr_str[1]= "0"+String.valueOf(timeX_arr_int[1]);
                }else{
                    timeX_arr_str[1]= String.valueOf(timeX_arr_int[1]);
                }

            mAxisXValues.add(new AxisValue(i).setLabel(""+timeX_arr_str[0]+":"+timeX_arr_str[1]+":"+timeX_arr_str[2]));
            mPointValues.add(new PointValue(i,data_point[i]));
            Log.d("Speed","\ntime = "+""+timeX_arr_str[0]+":"+timeX_arr_str[1]+":"+timeX_arr_str[2]+"\ndata = "+data_point[i]);
//            //延迟十分钟
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000*60*10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        }
        initLineChart();
    }

    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("每分钟脉搏跳动次数(次/分)");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL);
        chart.setMaxZoom((float) 2);//最大方法比例
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chart.setLineChartData(data);
        chart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(chart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        chart.setCurrentViewport(v);
    }




        public String getNewTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        Log.d("AquLightFragment:","Date获取当前日期时间"+simpleDateFormat.format(date));
        return simpleDateFormat.format(date);
    }
}
