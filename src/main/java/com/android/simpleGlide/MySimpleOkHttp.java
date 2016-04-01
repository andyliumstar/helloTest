package com.android.simpleGlide;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Created by andyliu on 2016/3/24.
 */
public class MySimpleOkHttp {
    private static MySimpleOkHttp mInstance = null;

    private OkHttpClient mOkHttpClient = null;

    private MySimpleOkHttp(){
        mOkHttpClient = new OkHttpClient();
    }

    public static MySimpleOkHttp getInstance(){
        if(null == mInstance){
            synchronized (MySimpleOkHttp.class){
                if(null == mInstance){
                    mInstance = new MySimpleOkHttp();
                }
            }
        }

        return mInstance;
    }

    public Response requestSync(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        return requestSync(request);
    }

    public Response requestSync(Request request){

        try {
            return mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void requestAsync(String url,Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        requestAsync(request,callback);
    }

    public void requestAsync(Request request,Callback callback){
        try {
            mOkHttpClient.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
