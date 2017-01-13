package com.lishijia.my.mygift.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.adapters.StyleWeeklyAdapter;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.entities.StyleWeekly;
import com.lishijia.my.mygift.utils.JSONLoader;

/**
 * Created by my on 2016/12/30.
 */

public class StyleWeeklyFragment extends Fragment{

    private ListView listView;
    private JSONLoader jsonLoader;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.style_weekly_frag, container , false);
        listView = (ListView)view.findViewById(R.id.style_weekly_list);
        jsonLoader = new JSONLoader();
        jsonLoader.loadJson(NetUrl.STYLE_WEEKLY_LIST, new JSONLoader.OnJSONLoaderListener() {
            @Override
            public void onJsonLoad(String json) {
                Gson gson = new Gson();
                StyleWeekly weekly = gson.fromJson(json, StyleWeekly.class);
                StyleWeeklyAdapter mAdapter = new StyleWeeklyAdapter(getContext() , weekly.getList());
                listView.setAdapter(mAdapter);
            }
        });
        return view;
    }
}
