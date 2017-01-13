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

public class HotListAdapter extends BaseAdapter {


    private Context context;
    private List<HotPush.InfoBean.Push1Bean> hotList;
    private LayoutInflater myInflater;

    public HotListAdapter(Context context,HotPush hotPush){
        this.context = context;
        this.hotList = hotPush.getInfo().getPush1();

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
            view = myInflater.inflate(R.layout.hot_item_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textGname = (TextView)view.findViewById(R.id.text_gname);
            viewHolder.textType = (TextView)view.findViewById(R.id.text_type_content);
            viewHolder.textSize = (TextView)view.findViewById(R.id.text_size_content);
            viewHolder.textPeople = (TextView)view.findViewById(R.id.text_people_content);
            viewHolder.imageHead = (ImageView)view.findViewById(R.id.image_head);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        HotPush.InfoBean.Push1Bean hotPush = hotList.get(position);
        viewHolder.textGname.setText(hotPush.getName());
        viewHolder.textType.setText(hotPush.getTypename());
        viewHolder.textSize.setText(hotPush.getSize());
        viewHolder.textPeople.setText(String.valueOf(hotPush.getClicks()));

        String imageURL = NetUrl.BEFORE_URL + hotPush.getLogo();
        viewHolder.imageHead.setTag(imageURL);

        new ImageLoader().loadImage(imageURL, new ImageLoader.OnImageLoaderListener() {

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
        TextView textType;
        TextView textSize;
        TextView textPeople;
        ImageView imageHead;
    }
}
