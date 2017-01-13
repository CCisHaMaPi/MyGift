package com.lishijia.my.mygift.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.entities.GameOpenService;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by my on 2016/12/29.
 */

public class GameServiceAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater myInflater;
    private Map<String , List<GameOpenService.InfoBean>> mData;

    public GameServiceAdapter(Context context, GameOpenService service) {
        this.context = context;
        myInflater = LayoutInflater.from(context);

        if(service != null){
            mData = new LinkedHashMap<>();
            for(GameOpenService.InfoBean bean: service.getInfo()){
                String addtime = bean.getAddtime();
                //判断是否已存在该分组,不存在该分组,则新建分组
                if(!mData.containsKey(addtime)){
                    mData.put(addtime,new ArrayList<GameOpenService.InfoBean>());
                }
                //加入对应分组集合
                mData.get(addtime).add(bean);
            }
        }
    }

    public GameServiceAdapter() {
        super();
    }

    @Override
    public int getGroupCount() {
        return mData.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = (String) mData.keySet().toArray()[groupPosition];
        List<GameOpenService.InfoBean> list = mData.get(key);
        return list == null ? 0 :list.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group = (String) mData.keySet().toArray()[groupPosition];
        TextView textView = new TextView(context);
        textView.setText(group);
        textView.setTextColor(Color.GREEN);
        textView.setTextSize(20);
        return textView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;
        if(null == view){
            view = myInflater.inflate(R.layout.game_service_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text_gname = (TextView)view.findViewById(R.id.text_gname);
            viewHolder.text_starttime = (TextView)view.findViewById(R.id.text_starttime);
            viewHolder.text_area = (TextView)view.findViewById(R.id.text_area);
            viewHolder.text_operators = (TextView)view.findViewById(R.id.text_operators);
            viewHolder.imageHead = (ImageView) view.findViewById(R.id.image_head);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        String key = (String) mData.keySet().toArray()[groupPosition];
        GameOpenService.InfoBean bean = (GameOpenService.InfoBean) mData.get(key).get(childPosition);
        viewHolder.text_gname.setText(bean.getGname());
        viewHolder.text_starttime.setText(bean.getLinkurl());
        viewHolder.text_area.setText(bean.getArea());
        viewHolder.text_operators.setText("运营商:"+bean.getOperators());

        viewHolder.imageHead.setImageResource(R.mipmap.def_loading);
        String imageURL = NetUrl.BEFORE_URL + bean.getIconurl();
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


    static class ViewHolder{
        TextView text_gname;
        TextView text_starttime;
        TextView text_area;
        TextView text_operators;
        ImageView imageHead;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
