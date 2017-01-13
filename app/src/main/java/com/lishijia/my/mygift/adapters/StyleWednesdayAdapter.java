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
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.entities.StyleWednesday;
import com.lishijia.my.mygift.utils.ImageLoader;

import java.util.List;

/**
 * Created by my on 2017/1/3.
 */

public class StyleWednesdayAdapter extends BaseAdapter {

    private Context context;
    private List<StyleWednesday.ListBean> mList;
    private LayoutInflater mInflater;

    public StyleWednesdayAdapter(Context context , StyleWednesday wednesday) {
        this.context = context;
        this.mList = wednesday.getList();

        mInflater = LayoutInflater.from(context);
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
            view = mInflater.inflate(R.layout.style_wednesday_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.style_wednesday_background);
            viewHolder.textTitle = (TextView)view.findViewById(R.id.style_wednesday_title);
            viewHolder.textAddtime = (TextView)view.findViewById(R.id.wednesday_text_time);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        final StyleWednesday.ListBean bean = mList.get(position);
        viewHolder.textTitle.setText(bean.getName());
        viewHolder.textAddtime.setText(bean.getAddtime());

        String imageUrl = NetUrl.BEFORE_URL+ bean.getIconurl();
        viewHolder.imageView.setTag(imageUrl);

        ImageLoader.loadImage(imageUrl, new ImageLoader.OnImageLoaderListener() {
            @Override
            public void onImageLoad(Bitmap bitmap, String url) {
                if(url.equals(viewHolder.imageView.getTag())){
                    viewHolder.imageView.setImageBitmap(bitmap);
                }
            }
        });
        return view;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textTitle;
        TextView textAddtime;
    }
}
