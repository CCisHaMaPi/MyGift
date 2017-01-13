package com.lishijia.my.mygift.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lishijia.my.mygift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/12/27.
 */

public class StyleFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> mlist;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.style_frag,container,false);
        initFragment();
        initView(view);
        return view;
    }

    private void initFragment(){
        mlist = new ArrayList<>();
        mlist.add(new StyleWednesdayFragment());
        mlist.add(new StyleWeeklyFragment());
    }

    private void initView(View v){
        viewPager = (ViewPager)v.findViewById(R.id.style_viewpager);
        tabLayout = (TabLayout)v.findViewById(R.id.style_table);
        viewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("暴打星期三");
        tabLayout.getTabAt(1).setText("新游周刊");
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public int getCount() {
            return mlist.size();
        }
    }
}
