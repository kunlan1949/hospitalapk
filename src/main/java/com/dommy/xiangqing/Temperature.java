package com.dommy.xiangqing;

import android.app.LauncherActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.dommy.Data.Handle;
import com.dommy.Data.MyOpenHelper;
import com.dommy.Http.AddTemp;
import com.dommy.Http.App;
import com.dommy.Http.HttpUtil;
import com.dommy.Mannager;
import com.dommy.Person.Add;
import com.dommy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Response;

public class Temperature extends AppCompatActivity implements View.OnClickListener {

    private LineChartView lineChart;
    public static final String TAG = "App";
    //数据库对象
    private DbHelper dbHelper;
    String url="http://192.168.137.1:8081/examples/temp.json";              //获取服务器文件所用的链接


    SQLiteDatabase db;
    List<AddTemp> addTempList;  //定义addTempList数组对象，获取来自网络的数据



   private List<PointValue> point=new ArrayList<PointValue>();
   private List<AxisValue> arrayList=new ArrayList<AxisValue>();



    float[] data_point = new float[7];
   String[] date = {"9/1", "9/2","9/3", "9/4","9/5", "9/6", "9/7"};//图表的数据点
   private List<PointValue> mPointValues = new ArrayList<PointValue>();
   private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_temperature);


       lineChart = (LineChartView) findViewById(R.id.line_temp);
       final IntentFilter filter = new IntentFilter(Mannager.action5);   //接受消息
       registerReceiver(broadcastReceiver2, filter);     //接收器

           sendRequestWithOkHttp();               //发动一次请求

           insertModelToDb(addTempList);   //插入数据进数据库
//建立温度折线图*****************************************************************************************************************************************
       dbHelper = new DbHelper(this);
       readDataFromDatabase();
//           getAxisXLables();//获取x轴的标注
//           getAxisPoints();//获取坐标点
//           initLineChart();//初始化

       Button button=findViewById(R.id.fresh);
       button.setOnClickListener(this);


       ImageButton imagebutton=findViewById(R.id.temp_back_Manager);            //图片按钮不用管
       imagebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Temperature.this,Mannager.class);
               startActivity(intent);
               finish();
           }
       });
   }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fresh) {
            sendRequestWithOkHttp();
            insertModelToDb(addTempList);
        }
    }

    BroadcastReceiver broadcastReceiver2=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {             //接收器
           sendRequestWithOkHttp();                                        //执行的方法
        }
    };


   /*---------------------------------------------------------------------折线图部分------------------------------------------------------------------------------------------------------------------------*/
//       private void getAxisXLables () {
//       for (int i=0;i<date.length;i++){
//           mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
//       }
//       }
//
//       private void getAxisPoints () {
//           for (int i=0;i<date.length;i++){
//               mPointValues.add(new PointValue(i,data[i]));
//           }
//   }
    private void readDataFromDatabase(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询表中数据
        Cursor cursor = db.query("temperature",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                data_point[0] = Float.valueOf(cursor.getString(cursor.getColumnIndex("Mon"))); //Mon
                data_point[1] = Float.valueOf(cursor.getString(cursor.getColumnIndex("Tue"))); //Tue
                data_point[2] = Float.valueOf(cursor.getString(cursor.getColumnIndex("Wed"))); //Tue
                data_point[3] = Float.valueOf(cursor.getString(cursor.getColumnIndex("Thr"))); //Tue
                data_point[4] = Float.valueOf(cursor.getString(cursor.getColumnIndex("Fri"))); //Tue
                data_point[5] = Float.valueOf(cursor.getString(cursor.getColumnIndex("Sat"))); //Tue
                data_point[6] = Float.valueOf(cursor.getString(cursor.getColumnIndex("Sun"))); //Tue


                Log.d("Temperature","Mon = "+data_point[0]+"\nTue = "+data_point[1]+"\nWed = "+data_point[2]+"\nThr = "+data_point[3]+"\nFri = "
                        +data_point[4] +"\nSat = "+data_point[5]+"\nSun = "+data_point[6]);
            }while(cursor.moveToNext());
        }
        cursor.close();
        for(int i=0 ; i<7 ; i++){
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
            mPointValues.add(new PointValue(i,data_point[i]));
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
       axisY.setName("");//y轴标注
       axisY.setTextSize(10);//设置字体大小
       data.setAxisYLeft(axisY);  //Y轴设置在左边
       //data.setAxisYRight(axisY);  //y轴设置在右边


       //设置行为属性，支持缩放、滑动以及平移
       lineChart.setInteractive(true);
       lineChart.setZoomType(ZoomType.HORIZONTAL);
       lineChart.setMaxZoom((float) 2);//最大方法比例
       lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
       lineChart.setLineChartData(data);
       lineChart.setVisibility(View.VISIBLE);
       /**注：下面的7，10只是代表一个数字去类比而已
        * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
        */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }



    /*----------------------------------------------------------接受网络数据并用gson解析json数据部分--------------------------------------------------------------------------------------------------------------*/



    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        addTempList = gson.fromJson(jsonData, new TypeToken<List<AddTemp>>() {}.getType());                             //使gson解析的数据和AddTemp定义的对象同步(列表数据同步)

        for(Iterator iterator = addTempList.iterator(); iterator.hasNext();){                                       //遍历addTempList列表进行选择
            AddTemp addTemp = (AddTemp) iterator.next();
            Log.e(TAG,addTemp.getMon()+addTemp.getTue()+addTemp.getWed()+addTemp.getThr()+addTemp.getFri()+addTemp.getSat()+addTemp.getSun());             //获取数据
        }
        for (AddTemp addTemp : addTempList) {                            //打印数据
            //控制台输出结果，便于查看
            Log.d("Temperature", "Mon=" + addTemp.getMon());
            Log.d("Temperature", "Tue=" + addTemp.getTue());
            Log.d("Temperature", "Wed=" + addTemp.getWed());
            Log.d("Temperature", "Thr=" + addTemp.getThr());
            Log.d("Temperature", "Fri=" + addTemp.getFri());
            Log.d("Temperature", "Sat=" + addTemp.getSat());
            Log.d("Temperature", "Sun=" + addTemp.getSun());

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
                      //  showResponse(responseData.toString());
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
    /*private void showResponse(final String response) {
        //在子线程中更新UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                textView.setText(response);
            }
        });
    }*/
    public void insertModelToDb(List<AddTemp> addTempList){
        try{

            DbHelper dbHelper = new DbHelper(this);
            db = dbHelper.getWritableDatabase();
            //开始事务
            db.beginTransaction();
            Log.d(TAG,addTempList.size()+"");
            String sql1 = "insert into temperature(id,Mon,Tue,Wed,Thr,Fri,Sat,Sun) values (?,?,?,?,?,?,?,?)";               //插入数据的键值对应关系
            for(AddTemp A :addTempList){
                db.execSQL(sql1,new Object[]{null,A.getMon(),A.getTue(),A.getWed(),A.getThr(),A.getFri(),A.getSat(),A.getSun()});          /**括号里的内容意思为（   键值对应关系       ，    定义的字符串数组【从addtemplist中获取数据 ===> A.get() 】  ）*/
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


}
