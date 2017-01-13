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
import com.lishijia.my.mygift.adapters.StyleWednesdayAdapter;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.entities.StyleWednesday;
import com.lishijia.my.mygift.utils.JSONLoader;

/**
 * Created by my on 2016/12/30.
 */

public class StyleWednesdayFragment extends Fragment {

    private JSONLoader jsonLoader;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.style_wednesday_frag, container, false);
        listView = (ListView)view.findViewById(R.id.style_wednesday_list);
        jsonLoader = new JSONLoader();
        jsonLoader.loadJson(NetUrl.STYLE_WEDNESDAY_LIST, new JSONLoader.OnJSONLoaderListener() {
            @Override
            public void onJsonLoad(String json) {
                Gson gson = new Gson();
                StyleWednesday styleWednesday = gson.fromJson(json, StyleWednesday.class);
                StyleWednesdayAdapter mAdapter = new StyleWednesdayAdapter(getContext(), styleWednesday);
                listView.setAdapter(mAdapter);
            }
        });

        return view;
    }
}
