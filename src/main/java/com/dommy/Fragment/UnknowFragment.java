package com.dommy.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.dommy.R;
import com.dommy.View.VerticalProgressBar;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnknowFragment extends Fragment implements View.OnClickListener {
    @Nullable

    private NiceSpinner niceSpinner;
    private NiceSpinner niceSpinner1;
    private NiceSpinner niceSpinner2;
    private View view;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addHotelNameView;

    private VerticalProgressBar vpProgressBar;
    List<String> spinnerData = new LinkedList<>(Arrays.asList("消息","1号"));
    List<String> spinnerData2 = new LinkedList<>(Arrays.asList("监控中心", "药液流速", "药液剩余"));
    List<String> spinnerData3 = new LinkedList<>(Arrays.asList("我", "切换登录", "退出"));
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.unknow_fragment,container,false);
        initView();
        addHotelNameView = (LinearLayout)view.findViewById(R.id.ll_addView);
        //view.findViewById(R.id.btn_getData).setOnClickListener((View.OnClickListener) this);

        //默认添加一个Item
        addViewItem(null);

        niceSpinner = view.findViewById(R.id.nice_spinner);
        niceSpinner.attachDataSource(spinnerData);
        niceSpinner.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner.setTextColor(Color.WHITE);
        niceSpinner.setTextSize(13);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        niceSpinner1 = view.findViewById(R.id.nice_spinner2);
        niceSpinner1.attachDataSource(spinnerData2);
        niceSpinner1.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner1.setTextColor(Color.WHITE);
        niceSpinner1.setTextSize(13);

        niceSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        niceSpinner2 = view.findViewById(R.id.nice_spinner3);
        niceSpinner2.attachDataSource(spinnerData3);
        niceSpinner2.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner2.setTextColor(Color.WHITE);
        niceSpinner2.setTextSize(13);

        niceSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_ills://点击添加按钮就动态添加Item
                addViewItem(v);
                break;
           /* case R.id.btn_getData://打印数据
                printData();
                break;*/
        }
    }

    /**
     * Item排序
     */
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
            View hotelEvaluateView = View.inflate(getActivity(), R.layout.add_ills, null);
            Button btn_add = (Button) hotelEvaluateView.findViewById(R.id.new_ills);
            btn_add.setText("+新增");
            btn_add.setTag("add");
            btn_add.setOnClickListener(this);
            addHotelNameView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else if (((String) view.getTag()).equals("add")) {//如果有一个以上的Item,点击为添加的Item则添加
            View hotelEvaluateView = View.inflate(getActivity(), R.layout.add_ills, null);
            addHotelNameView.addView(hotelEvaluateView);
            sortHotelViewItem();
        }
        //else {
        //  sortHotelViewItem();
        //}
    }

    //获取所有动态添加的Item，找到控件的id，获取数据
  /*  private void printData() {
        for (int i = 0; i < addHotelNameView.getChildCount(); i++) {
            View childAt = addHotelNameView.getChildAt(i);
            EditText hotelName = (EditText) childAt.findViewById(R.id.person_number);
            Log.e(TAG, "酒店名称：" + hotelName.getText().toString());
        }
    }
*/
    private void initView() {
        vpProgressBar = (VerticalProgressBar) view.findViewById(R.id.vp_progress);
        run();
    }

    /**测试progressbar*/
    private void run() {
        new Thread(){
            public void run() {
                try {
                    for (int i= 0;i<=100;i++) {
                        Thread.sleep(50);//休息50毫秒
                        vpProgressBar.setProgress(i);//更新进度条进度
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
