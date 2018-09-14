package com.dommy.Logo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dommy.IllsHouse;
import com.dommy.Mannager;
import com.dommy.R;

public class Logo extends AppCompatActivity {

    public static final String action = "hahahahahahhahahahaha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.xiaoguo);
        TextView textView=findViewById(R.id.ttt);
        textView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
// TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
// TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
// TODO Auto-generated method stub
                Intent intent=new Intent(Logo.this, IllsHouse.class);
                startActivity(intent);
                Intent intent2 = new Intent(action);
                sendBroadcast(intent2);
                finish();
            }
        });

    }
}
