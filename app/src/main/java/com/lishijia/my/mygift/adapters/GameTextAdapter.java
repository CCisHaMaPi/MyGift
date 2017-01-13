package com.lishijia.my.mygift.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.entities.GameOpenTest;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.ImageLoader;

import java.util.List;

/**
 * Created by my on 2016/12/30.
 */

public class GameTextAdapter extends BaseAdapter {

    private Context context;
    private List<GameOpenTest.InfoBean> mList;
    private LayoutInflater myInflater;

    public GameTextAdapter(Context context, List<GameOpenTest.InfoBean> mList) {
        this.context = context;
        this.mList = mList;

        myInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;
        if(null == view){
            view = myInflater.inflate(R.layout.game_text_item, parent , false);
            viewHolder = new ViewHolder();
            viewHolder.text_gname = (TextView)view.findViewById(R.id.text_gname);
            viewHolder.text_starttime = (TextView)view.findViewById(R.id.text_starttime);
            viewHolder.text_operators = (TextView)view.findViewById(R.id.text_operators);
            viewHolder.image_head = (ImageView)view.findViewById(R.id.image_head);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        final GameOpenTest.InfoBean bean = mList.get(position);
        viewHolder.text_gname.setText(bean.getGname());
        viewHolder.text_starttime.setText(bean.getAddtime()+" "+bean.getLinkurl());
        viewHolder.text_operators.setText("运营商:"+bean.getOperators());

        viewHolder.image_head.setImageResource(R.mipmap.def_loading);
        String imageURL = NetUrl.BEFORE_URL + bean.getIconurl();
        viewHolder.image_head.setTag(imageURL);
        ImageLoader.loadImage(imageURL, new ImageLoader.OnImageLoaderListener() {


            @Override
            public void onImageLoad(Bitmap bitmap, String url) {
                if(url.equals(viewHolder.image_head.getTag())){
                    viewHolder.image_head.setImageBitmap(bitmap);
                }
            }
        });
        return view;
    }

    static class ViewHolder{
        ImageView image_head;
        TextView text_gname;
        TextView text_starttime;
        TextView text_operators;
    }
}
