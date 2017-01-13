package com.lishijia.my.mygift.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.fragments.GameFragment;
import com.lishijia.my.mygift.fragments.GiftFragment;
import com.lishijia.my.mygift.fragments.HotFragment;
import com.lishijia.my.mygift.fragments.StyleFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment fragGift, fragGame, fragHot, fragStyle, fragDefalut;
    private RadioGroup radioGroup;
    private FragmentManager mManager;
    private SlidingPaneLayout slidingPaneLayout;
    private boolean isFirstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判定是否初次加载
        if(isFirstStart){
            //初次加载,跳转到开场页面.主页面在后台进行初始化
            isFirstStart = false;
            startActivity(new Intent(this, SplashActivity.class));
        }
        initFragments();
        initView();
    }

    private void initView(){

        radioGroup = (RadioGroup)findViewById(R.id.rg_bottom);
        //为单选按钮添加点击切换Fragment事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.btn_gift:
                        switchFragment(fragGift);
                        break;
                    case R.id.btn_game:
                        switchFragment(fragGame);
                        break;
                    case R.id.btn_hot:
                        switchFragment(fragHot);
                        break;
                    case R.id.btn_style:
                        switchFragment(fragStyle);
                        break;
                }
            }
        });

        slidingPaneLayout = (SlidingPaneLayout)findViewById(R.id.sliding_pane);
        final View view = findViewById(R.id.activity_main);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                view.setScaleX(1 - slideOffset * 0.4f);
                view.setScaleY(1 - slideOffset * 0.4f);
            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
    }

    private void initFragments() {
        fragGift = new GiftFragment();
        fragGame = new GameFragment();
        fragHot = new HotFragment();
        fragStyle = new StyleFragment();
        mManager = getSupportFragmentManager();
        mManager.beginTransaction()
                .add(R.id.frame_content,fragGift)
                .commit();
        fragDefalut = fragGift;
    }

    public void switchFragment(Fragment newFrag){
        if(newFrag != fragDefalut){
            //判断新的Fragment是否已添加
            if(newFrag.isAdded()){
                //已经添加，隐藏原来的Fragment，显示新的Fragment
                mManager.beginTransaction()
                        .hide(fragDefalut)
                        .show(newFrag)
                        .commit();
            }else{
                //没有添加，隐藏原来的Fragment，添加并且显示新的Fragment
                mManager.beginTransaction()
                        .hide(fragDefalut)
                        .add(R.id.frame_content,newFrag)
                        .commit();
            }
            //设置新的Fragment为当前的默认Frag
            fragDefalut = newFrag;
        }
    }
}
