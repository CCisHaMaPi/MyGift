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
import com.lishijia.my.mygift.adapters.GameTextAdapter;
import com.lishijia.my.mygift.entities.GameOpenTest;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.JSONLoader;

/**
 * Created by my on 2016/12/29.
 */

public class GameTextFragment extends Fragment {

    private ListView listView;
    private GameTextAdapter mAdapter;
    private JSONLoader mJSONLoader;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_text_frag,container,false);
        listView = (ListView)view.findViewById(R.id.game_text_list);
        mJSONLoader = new JSONLoader();
        mJSONLoader.loadJson(NetUrl.GAME_TEXT_LIST, new JSONLoader.OnJSONLoaderListener() {
            @Override
            public void onJsonLoad(String json) {
                Gson gson = new Gson();
                GameOpenTest gameOpenTest = gson.fromJson(json, GameOpenTest.class);
                mAdapter = new GameTextAdapter(getContext(),gameOpenTest.getInfo());
                listView.setAdapter(mAdapter);
            }
        });
        return view;
    }
}
