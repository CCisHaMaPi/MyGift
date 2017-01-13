package com.lishijia.my.mygift.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.adapters.HotGridAdapter;
import com.lishijia.my.mygift.adapters.HotListAdapter;
import com.lishijia.my.mygift.entities.HotPush;
import com.lishijia.my.mygift.utils.JSONLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/12/27.
 */

public class HotFragment extends Fragment {

    private static final String URL_HOTPUSH = "http://www.1688wan.com//majax.action?method=hotpushForAndroid";
    private JSONLoader jsonLoader;
    private ListView listView;
    private GridView gridView;
    private HotPush hotPush = null;
    private HotListAdapter hotAdapter;
    private HotGridAdapter gridHotAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hot_frag,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        gridView = (GridView)view.findViewById(R.id.grid_view);

        listView = (ListView)view.findViewById(R.id.list_view);
    }

    private void initData(){
        jsonLoader = new JSONLoader();
        jsonLoader.loadJson(URL_HOTPUSH, new JSONLoader.OnJSONLoaderListener() {

            @Override
            public void onJsonLoad(String json) {
                Gson gson = new Gson();
                HotPush hotPush = gson.fromJson(json, HotPush.class);
                hotAdapter = new HotListAdapter(getContext(), hotPush);
                listView.setAdapter(hotAdapter);
                gridHotAdapter = new HotGridAdapter(getContext(), hotPush);
                gridView.setAdapter(gridHotAdapter);

            }
        });
    }
}
