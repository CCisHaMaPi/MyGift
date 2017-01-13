package com.lishijia.my.mygift.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lishijia.my.mygift.R;
import com.lishijia.my.mygift.entities.GiftInfo;
import com.lishijia.my.mygift.entities.NetUrl;
import com.lishijia.my.mygift.utils.ImageLoader;
import com.lishijia.my.mygift.utils.JSONLoader;


/**
 * Created by my on 2016/12/27.
 */

public class GiftInfoActivity extends AppCompatActivity {

    private TextView textName;
    private TextView textOverTime;
    private TextView textNumber;
    private TextView textExplains;
    private TextView textDescs;
    private ImageView imageHead;
    private JSONLoader myJSONLoader;
    private GiftInfo.InfoBean infoBean;
    private Button btnGetGift ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_info_activity);
        initView();
        Intent intent = getIntent();
        String urlPath = intent.getStringExtra("pathUrl");

        myJSONLoader.loadJson(urlPath, new JSONLoader.OnJSONLoaderListener() {

            @Override
            public void onJsonLoad(String json) {
                Gson gson = new Gson();
                GiftInfo info = gson.fromJson(json, GiftInfo.class);

                infoBean = info.getInfo();

                textName.setText(infoBean.getGname()+" - "+infoBean.getGiftname());
                textOverTime.setText("有效期 : "+infoBean.getOvertime());
                textNumber.setText("剩余 : "+String.valueOf(infoBean.getNumber()));
                textExplains.setText(infoBean.getExplains());
                textDescs.setText(infoBean.getDescs());
                ImageLoader.loadImage(NetUrl.BEFORE_URL+infoBean.getIconurl(), new ImageLoader.OnImageLoaderListener() {

                    @Override
                    public void onImageLoad(Bitmap bitmap, String url) {
                        imageHead.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public void onClickGetGift(View v){
        btnGetGift = (Button)findViewById(R.id.btn_getgift);
        btnGetGift.setBackgroundResource(R.color.grey);
        btnGetGift.setText(R.string.after_getgift);
        btnGetGift.setClickable(false);
        textNumber.setText("剩余 : "+String.valueOf(infoBean.getNumber()-1));
        Toast.makeText(this, "领取成功", Toast.LENGTH_SHORT).show();
    }

    private void initView(){
        myJSONLoader = new JSONLoader();
        textName = (TextView)findViewById(R.id.text_name);
        textOverTime = (TextView)findViewById(R.id.text_overtime);
        textNumber = (TextView)findViewById(R.id.text_last_number);
        textExplains = (TextView)findViewById(R.id.text_explains);
        textDescs = (TextView)findViewById(R.id.text_descs);
        imageHead = (ImageView)findViewById(R.id.image_background);
        btnGetGift = (Button)findViewById(R.id.btn_getgift);
    }
}
