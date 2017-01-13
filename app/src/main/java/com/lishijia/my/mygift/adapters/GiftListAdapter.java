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
import com.lishijia.my.mygift.entities.Gift;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.ImageLoader;

import java.util.List;


/**
 * Created by my on 2016/12/27.
 */

public class GiftListAdapter extends BaseAdapter{

    private Context context;
    private List<Gift.ListBean> mList;
    private LayoutInflater myInflater;

    public GiftListAdapter(Context context, List<Gift.ListBean> mList) {
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
        if (null == view) {
            view = myInflater.inflate(R.layout.gift_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textGameName = (TextView)view.findViewById(R.id.text_gname);
            viewHolder.textGiftName = (TextView)view.findViewById(R.id.text_giftname);
            viewHolder.textNumber = (TextView)view.findViewById(R.id.text_number);
            viewHolder.textAddTime = (TextView)view.findViewById(R.id.text_addtime);
            viewHolder.imageHead = (ImageView)view.findViewById(R.id.image_head);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Gift.ListBean bean = mList.get(position);
        viewHolder.textGameName.setText(bean.getGname());
        viewHolder.textGiftName.setText(bean.getGiftname());
        viewHolder.textNumber.setText(String.valueOf(bean.getNumber()));
        viewHolder.textAddTime.setText(bean.getAddtime());

        viewHolder.imageHead.setImageResource(R.mipmap.def_loading);
        String imageURL = NetUrl.BEFORE_URL + bean.getIconurl();
        viewHolder.imageHead.setTag(imageURL);

        ImageLoader.loadImage(imageURL,new ImageLoader.OnImageLoaderListener() {


            @Override
            public void onImageLoad(Bitmap bitmap, String url) {
                if(url.equals(viewHolder.imageHead.getTag())){
                    viewHolder.imageHead.setImageBitmap(bitmap);
                }
            }
        });
        return view;
    }

    static class ViewHolder{
        TextView textGameName;
        TextView textGiftName;
        TextView textNumber;
        TextView textAddTime;
        ImageView imageHead;
    }
}
