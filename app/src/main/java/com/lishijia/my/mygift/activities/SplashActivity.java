package com.lishijia.my.mygift.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.lishijia.my.mygift.R;

/**
 * Created by my on 2017/1/7.
 */

public class SplashActivity extends AppCompatActivity {

    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        image = (ImageView) findViewById(R.id.image_wc);
        //创建透明度补间动画
        final Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        //延时启动动画
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.startAnimation(animation);
            }
        },2000);

        //设置动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                image.setImageResource(R.mipmap.wc);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //监听动画,动画结束时,结束开场页面,回到栈底的MainActivity
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
