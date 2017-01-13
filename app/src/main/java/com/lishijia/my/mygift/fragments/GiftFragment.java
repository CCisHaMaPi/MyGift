package com.lishijia.my.mygift.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.activities.GiftInfoActivity;
import com.lishijia.my.mygift.adapters.GiftListAdapter;
import com.lishijia.my.mygift.entities.Gift;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.ImageLoader;
import com.lishijia.my.mygift.utils.JSONLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/12/27.
 */

public class GiftFragment extends Fragment {


    private int page = 1;
    private List<Gift.ListBean> mList;
    private List<View> mViewList;
    private GiftListAdapter giftListAdapter;
    private AdBeanAdapter giftAdAdapter;
    private JSONLoader jsonLoader;
    private ListView listView;
    private ViewPager viewPager;
    private LinearLayout mDotLayout;
    private List<ImageView> mDots;
    private Handler handler;
    private int mCurrentDot = 0;//圆点位置
    private int mCurrentPage = 0;//广告页面位置

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gift_frag,container,false);
        initView(view);
        return view;
    }

    //初始化各个控件
    private void initView(View view){
        listView = (ListView)view.findViewById(R.id.list_gift);

        mList = new ArrayList<>();
        mViewList = new ArrayList<>();

        giftListAdapter = new GiftListAdapter(getContext(), mList);
        giftAdAdapter = new AdBeanAdapter();

        final View header = LayoutInflater.from(getContext()).inflate(R.layout.gift_ad_pager,null);
        viewPager = (ViewPager)header.findViewById(R.id.gift_ad_viewpager);
        //初始化广告底部圆点视图
        mDotLayout = (LinearLayout)header.findViewById(R.id.gift_ad_dots);
        //添加底部按钮加载更多
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.gift_load_foot,null);
        final Button btnLoadMore = (Button)footer.findViewById(R.id.btn_load_more);
        listView.addFooterView(footer);
        listView.addHeaderView(header);
        listView.setAdapter(giftListAdapter);
        viewPager.setAdapter(giftAdAdapter);

        final Gson gson = new Gson();
        jsonLoader = new JSONLoader();
        jsonLoader.loadJson(NetUrl.GIFT_LIST_BEAN + page, new JSONLoader.OnJSONLoaderListener() {
            @Override
            public void onJsonLoad(String json) {
                final Gift mGift = gson.fromJson(json, Gift.class);
                mList.addAll(mGift.getList());
                mViewList = initPagerView(mGift.getAd());
                giftListAdapter.notifyDataSetChanged();
                giftAdAdapter.notifyDataSetChanged();
                //初始化圆点指示器
                initDots(mGift.getAd().size());
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mCurrentPage == mGift.getAd().size()-1){
                            mCurrentPage = 0;
                        }else {
                            mCurrentPage++;
                        }
                        viewPager.setCurrentItem(mCurrentPage);
                        header.postDelayed(this,4000);
                    }
                },4000);
            }
        });
        //底部按钮点击事件
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoadMore.setText(R.string.loading_more);
                btnLoadMore.setClickable(false);
                page++;
                jsonLoader.loadJson(NetUrl.GIFT_LIST_BEAN + page, new JSONLoader.OnJSONLoaderListener() {

                    @Override
                    public void onJsonLoad(String json) {
                        Gift mGift = gson.fromJson(json, Gift.class);
                        mList.addAll(mGift.getList());
                        giftListAdapter.notifyDataSetChanged();
                        btnLoadMore.setClickable(true);
                        btnLoadMore.setText(R.string.load_more);
                    }
                });
            }
        });

        //点击项目进入对应礼包详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position < listView.getCount()-1){
                    Gift.ListBean bean = (Gift.ListBean)giftListAdapter.getItem(position-1);
                    String infoId = bean.getId();
                    String giftInfoUrl = NetUrl.GIFT_LIST_BEAN_INFO+infoId;
                    Intent intent = new Intent(getContext(), GiftInfoActivity.class);
                    intent.putExtra("pathUrl", giftInfoUrl);
                    startActivity(intent);
                }
            }
        });

        //广告页面切换监听,实现圆点跟着变换
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //原来的位置设置为未选中
                mDots.get(mCurrentDot).setEnabled(true);
                //将新的圆点设置为已选中
                mDots.get(position).setEnabled(false);
                mCurrentDot = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<View> initPagerView(List<Gift.AdBean> adList){
        List<View> list = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        //遍历AdBean集合中对象.
        for(Gift.AdBean ad : adList){
            //加载视图
            View view = inflater.inflate(R.layout.gift_ad_item,null);
            //加载控件
            final ImageView image = (ImageView) view.findViewById(R.id.gift_ad_image);
            //解析图片地址并载入控件
            ImageLoader.loadImage(
                    NetUrl.BEFORE_URL + ad.getIconurl(),
                    new ImageLoader.OnImageLoaderListener() {
                @Override
                public void onImageLoad(Bitmap bitmap , String url) {
                    if(null != bitmap){
                        image.setImageBitmap(bitmap);
                    }
                }
            });
            list.add(view);
        }
        return list;
    }

    //初始化圆点集合
    private void initDots(int count){
        mDots = new ArrayList<>();
        //布局参数
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,0,5,0);
        for (int i = 0; i < count; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.dot_ad_selector);
            mDots.add(imageView);
            mDotLayout.addView(imageView, lp);
        }
        //默认选中第一个点
        mDots.get(0).setEnabled(false);
    }

    //广告轮播适配器
    class AdBeanAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mViewList == null ? 0: mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }
}
