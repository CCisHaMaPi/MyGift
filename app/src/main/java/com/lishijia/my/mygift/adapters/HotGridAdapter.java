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
import com.lishijia.my.mygift.entities.HotPush;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.ImageLoader;

import java.util.List;

/**
 * Created by my on 2016/12/27.
 */

public class HotGridAdapter extends BaseAdapter {

    private Context context;
    private List<HotPush.InfoBean.Push2Bean> hotList;
    private LayoutInflater myInflater;

    public HotGridAdapter(Context context, HotPush hotPush) {
        this.context = context;
        this.hotList = hotPush.getInfo().getPush2();

        myInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return hotList == null ? 0 : hotList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotList.get(position);
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
            view = myInflater.inflate(R.layout.hot_item_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textGname = (TextView)view.findViewById(R.id.grid_tv_name);
            viewHolder.imageHead = (ImageView)view.findViewById(R.id.grid_iv_head);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        HotPush.InfoBean.Push2Bean hotPush = hotList.get(position);
        viewHolder.textGname.setText(hotPush.getName());

        String imageURL = NetUrl.BEFORE_URL + hotPush.getLogo();
        viewHolder.imageHead.setTag(imageURL);
        ImageLoader.loadImage(imageURL, new ImageLoader.OnImageLoaderListener() {

            @Override
            public void onImageLoad(Bitmap bitmap, String url) {
                if(url.equals(viewHolder.imageHead.getTag())){
                    viewHolder.imageHead.setImageBitmap(bitmap);
                }
            }
        });
        return view;
    }
    class ViewHolder{
        TextView textGname;
        ImageView imageHead;
    }
}
