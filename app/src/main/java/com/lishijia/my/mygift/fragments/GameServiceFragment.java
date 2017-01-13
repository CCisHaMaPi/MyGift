package com.lishijia.my.mygift.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.adapters.GameServiceAdapter;
import com.lishijia.my.mygift.entities.GameOpenService;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.JSONLoader;

/**
 * Created by my on 2016/12/29.
 */

public class GameServiceFragment extends Fragment {

    private ExpandableListView listView;
    private GameServiceAdapter mAdapter;
    private JSONLoader mJsonLoader;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_service_frag,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        listView = (ExpandableListView)view.findViewById(R.id.game_service_list);
        final Gson gson = new Gson();
        mJsonLoader = new JSONLoader();
        mJsonLoader.loadJson(NetUrl.GAME_SERVICE_LIST, new JSONLoader.OnJSONLoaderListener() {
            @Override
            public void onJsonLoad(String json) {
                GameOpenService service = gson.fromJson(json , GameOpenService.class);
                mAdapter = new GameServiceAdapter(getContext() , service);
                listView.setAdapter(mAdapter);
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    listView.expandGroup(i);
                }
            }
        });
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }
}
