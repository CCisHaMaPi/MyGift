package com.lishijia.my.mygift.utils;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by my on 2017/1/3.
 */

public class ImageUtils {

    //内存缓存大小
    public static final int MEMEORY_CACHE_SIZE = 1024 * 1024 * 4;
    //磁盘缓存大小
    public static final int DISK_CACHE_SIZE = MEMEORY_CACHE_SIZE * 4;
    //内存缓存
    private static LruCache<String , Bitmap> memoryCache;
    //磁盘缓存
    private static DiskLruCache diskCache;

    /**
     *初始化缓存方法
     */
    public static void initCache(Context context){
        if (null == memoryCache){
            memoryCache = new LruCache<>(MEMEORY_CACHE_SIZE);
        }
        if (null == diskCache){
            try {
                diskCache = DiskLruCache.open(
                        getCacheFile(context),
                        getVersionCode(context),
                        1,
                        DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *获得缓存目录
     */
    public static File getCacheFile(Context context){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return context.getExternalCacheDir();
        }
        return context.getCacheDir();
    }

    public static int getVersionCode(Context context){
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String md5(String url){
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(url.getBytes());// 加密
            byte[] digest1 = digest.digest();// 获取
            StringBuilder builder = new StringBuilder();
            //字节数组转换为16进制字符
            for (int i = 0; i < digest1.length; i++){
                builder.append(Integer.toHexString(Math.abs(digest1[i])));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片保存在一级缓存
     * @param url
     * @param bitmap
     */
    public static void saveToMemoryCache(String url, Bitmap bitmap){
        memoryCache.put(md5(url), bitmap);
    }

    /**
     * 从一级缓存中读取图片
     * @param url
     * @return
     */
    public static Bitmap readFromMemoryCache(String url){
        return memoryCache.get(md5(url));
    }

    /**
     * 图片保存到磁盘缓存
     * @param url
     * @param bitmap
     */
    public static void saveToDiskCache(String url, Bitmap bitmap){
        try {
            DiskLruCache.Editor editor = diskCache.edit(md5(url));
            if (null != editor){
                OutputStream outputStream = editor.newOutputStream(0);
                boolean compress =
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                if (compress){
                    editor.commit();
                }else {
                    editor.abort();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readFromDiskCache(String url){
        try {
            DiskLruCache.Snapshot snapshot = diskCache.get(md5(url));
            if (null != snapshot){
                InputStream inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap zipImage(byte[] buf , int destWidth , int destHeight){

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(buf, 0, buf.length, options);

        int width = options.outWidth;
        int height = options.outHeight;

        int sampleSize = 1;
        while((width / sampleSize >= destWidth) || (height / sampleSize >=destHeight)){
            sampleSize = sampleSize << 1;
        }

        options.inJustDecodeBounds = false;

        options.inSampleSize = sampleSize;

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length, options);
        Log.i("!" , "zipImage: "+ width +"*" +height +"---->" +bitmap.getWidth() +"*" +bitmap.getHeight());
        return bitmap;
    }


    /**
     *  输入流转换为字节数组
     */
    public static byte[] stream2ByteArray(InputStream inputStream){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        try{
            while ((len = inputStream.read(buf)) != -1){
                outputStream.write(buf,0,len);
            }
            outputStream.flush();;
            return  outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
