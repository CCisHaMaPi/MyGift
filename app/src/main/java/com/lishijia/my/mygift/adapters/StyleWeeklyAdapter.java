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
import com.lishijia.my.mygift.entities.StyleWeekly;
import com.lishijia.my.mygift.utils.CircleImageView;
import com.lishijia.my.mygift.utils.ImageLoader;

import java.util.List;


/**
 * Created by my on 2016/12/30.
 */

public class StyleWeeklyAdapter extends BaseAdapter {

    private Context context;
    private List<StyleWeekly.ListBean> mList;
    private LayoutInflater mInflater;

    public StyleWeeklyAdapter(Context context, List<StyleWeekly.ListBean> mList) {
        this.context = context;
        this.mList = mList;

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
            view = mInflater.inflate(R.layout.style_weekly_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.style_weekly_background);
            viewHolder.imageAuthor = (CircleImageView)view.findViewById(R.id.style_weekly_author);
            viewHolder.textTitle = (TextView)view.findViewById(R.id.style_weekly_title);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        final StyleWeekly.ListBean bean = mList.get(position);
        viewHolder.textTitle.setText(bean.getName());

        String authorUrl = NetUrl.BEFORE_URL + bean.getAuthorimg();
        String imageUrl = NetUrl.BEFORE_URL + bean.getIconurl();
        viewHolder.imageAuthor.setTag(authorUrl);
        viewHolder.imageView.setTag(imageUrl);

        ImageLoader.loadImage(imageUrl, new ImageLoader.OnImageLoaderListener() {
            @Override
            public void onImageLoad(Bitmap bitmap, String url) {
                if(url.equals(viewHolder.imageView.getTag())){
                    viewHolder.imageView.setImageBitmap(bitmap);
                }
            }
        });

        ImageLoader.loadImage(authorUrl, new ImageLoader.OnImageLoaderListener() {
            @Override
            public void onImageLoad(Bitmap bitmap, String url) {
                if(url.equals(viewHolder.imageAuthor.getTag())){
                    viewHolder.imageAuthor.setImageBitmap(bitmap);
                }
            }
        });

        return view;
    }

    static class ViewHolder{
        ImageView imageView;
        CircleImageView imageAuthor;
        TextView textTitle;
    }
}
