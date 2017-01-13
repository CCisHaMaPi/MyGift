package com.lishijia.my.mygift.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by my on 2016/12/27.
 */

public class JSONLoader {


    public interface OnJSONLoaderListener {

        void onJsonLoad(String json);
    }

    private OnJSONLoaderListener jSONListener;

    public void loadJson(String url , OnJSONLoaderListener jsonListener){
        this.jSONListener = jsonListener;
        new JSONLoadTaskd().execute(url);
    }

    class JSONLoadTaskd extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                InputStream input = conn.getInputStream();
                byte[] buf = new byte[1024];
                int length = 0;
                StringBuilder sb = new StringBuilder();

                while((length = input.read(buf))!=-1){
                    String result = new String(buf, 0, length);
                    sb.append(result);
                }
                input.close();
                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(null != jSONListener){
                jSONListener.onJsonLoad(result);
            }
        }
    }
}
