package com.lishijia.my.mygift.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by my on 2016/12/27.
 */

public class ImageLoader {


    public interface OnImageLoaderListener{

        void onImageLoad(Bitmap bitmap , String url);
    }

    //启动加载任务
    public static void loadImage(String url , OnImageLoaderListener imageListener){
        new ImageLoadTask(imageListener).execute(url);
    }

    //启动压缩图片加载任务
    public static void loadImage(String url , int width , int height ,OnImageLoaderListener imageListener){
        new ImageLoadTask(imageListener , width , height).execute(url);
    }

    static class ImageInfo{
        Bitmap bitmap;
        String url;

        public ImageInfo(Bitmap bitmap, String url) {
            this.bitmap = bitmap;
            this.url = url;
        }
    }

    static class ImageLoadTask extends AsyncTask<String, Void, ImageInfo> {

        private OnImageLoaderListener imageListener;
        private int mDestWidth;  //目标宽度
        private int mDestHeight; //目标高度

        public ImageLoadTask(OnImageLoaderListener imageListener) {
            this.imageListener = imageListener;
        }

        public ImageLoadTask(OnImageLoaderListener imageListener , int mDestWidth , int mDestHeight){
            this.imageListener = imageListener;
            this.mDestWidth = mDestWidth;
            this.mDestHeight = mDestHeight;
        }

        @Override
        protected ImageInfo doInBackground(String... params) {
            //先从一级缓存读取图片数据
            Bitmap bitmap = null;
            bitmap = ImageUtils.readFromMemoryCache(params[0]);
            if (null == bitmap){
                //一级缓存中没有,则从二级缓存中寻找
                bitmap = ImageUtils.readFromDiskCache(params[0]);
                if(null == bitmap){
                    //二级缓存中也没有,则从网络上加载,并存到一二级缓存
                    try {
                        URL url = new URL(params[0]);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        InputStream input = conn.getInputStream();
                        //判断是否压缩
                        if(mDestHeight == 0 || mDestWidth ==0){
                            bitmap = BitmapFactory.decodeStream(input);
                        }else{
                            byte[] buf = ImageUtils.stream2ByteArray(input);
                            bitmap = ImageUtils.zipImage(buf , mDestWidth , mDestHeight);
                        }
                        input.close();
                        //图片不为空,则保存到缓存中
                        if (null != bitmap){
                            ImageUtils.saveToMemoryCache(params[0], bitmap);
                            ImageUtils.saveToDiskCache(params[0], bitmap);
                        }
                        return new ImageInfo(bitmap ,params[0] );
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    //二级缓存中找到数据,显示 并保存到一级缓存
                    ImageUtils.saveToMemoryCache(params[0], bitmap);
                    return new ImageInfo(bitmap ,params[0]);
                }
            }else {
                //一级缓存中找到相应数据,直接显示
                return new ImageInfo(bitmap ,params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ImageInfo info) {
            if(null != info){
                imageListener.onImageLoad(info.bitmap, info.url);
            }
        }
    }
}
