package com.android.simpleGlide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.hellotest.R;
import com.squareup.okhttp.Response;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MySampleGlide {
    private Context mCtx;
    private String TAG = "MySampleGlide";
    private static MySampleGlide mInstance = null;
    private Handler mUiHandler = new Handler();
    private ThreadPoolExecutor mExecutorService ;


    private LruCache<String, Bitmap> mMemoryCache;

    private MySampleGlide(Context ctx){
        mExecutorService =  new ThreadPoolExecutor(5,5,
                0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        mCtx = ctx;

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    public static MySampleGlide getInstance(Context ctx){
        if(null == mInstance){
            synchronized (MySampleGlide.class){
                if(null == mInstance){
                    mInstance = new MySampleGlide(ctx);
                }
            }
        }

        return mInstance;
    }

    private int mMaxW = 0;
    private int mMaxH = 0;
    public MySampleGlide reSize(int maxW, int maxH){
        mMaxW = maxW;
        mMaxH = maxH;
        return mInstance;
    }

    public MySampleGlide reSize(int maxW){
        mMaxW = maxW;
        return mInstance;
    }

    public void loadInto(String uri, ImageView imageview){
        Log.d(TAG, "new-uri" + uri);
        Log.d(TAG, "old-uri" + imageview.getTag());
        imageview.setTag(uri);

        if(null != mMemoryCache.get(uri)){
            Log.d(TAG, "cache-uri" + uri);
            imageview.setImageBitmap(mMemoryCache.get(uri));
        }else{
            imageview.setImageResource(R.drawable.camera_mode);
            mExecutorService.execute(new TaskRun(uri,mMaxW,mMaxH,imageview));
        }
        mMaxW = 0;
        mMaxH = 0;
    }

    private class TaskRun implements Runnable{
        private String mUri;
        private ImageView mImageView;

        private int mWid;
        private int mHei;
        public TaskRun(String uri,int wid, int hei, ImageView imageview){
            mUri = uri;
            mWid = wid;
            mHei = hei;
            mImageView = imageview;
        }

        protected void handleResponse(final Response response){
            Log.d(TAG,"finish download mUri---"+mUri);
            byte[] fileBytes = null;
            try
            {
                fileBytes = response.body().bytes();

                BitmapFactory.Options ops = new BitmapFactory.Options();
                ops.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(fileBytes,0,fileBytes.length,ops);

                ops.inSampleSize = calculateInSampleSize(ops, mWid);
                ops.inJustDecodeBounds = false;

                final Bitmap bm = BitmapFactory.decodeByteArray(fileBytes,0,fileBytes.length,ops);
                mMemoryCache.put(mUri,bm);
                if(null != mImageView.getTag() && !mUri.equals(mImageView.getTag())){
                    //old request, return;
                    return;
                }
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bm);
                    }
                });
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        protected int calculateInSampleSize(BitmapFactory.Options options,
                                                int reqWidth) {
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (width > reqWidth && reqWidth > 0) {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
            Log.d(TAG,"options.outWidth :"+width+" /  reqWidth:" + reqWidth + " /inSampleSize"+inSampleSize);
            return inSampleSize;
        }

        @Override
        public void run() {
            if(null != mImageView.getTag() && !mUri.equals(mImageView.getTag())){
                Log.d(TAG,"retrun old---"+mUri);
                return;
            }
            Log.d(TAG, "start download uri"+mUri);

            handleResponse(MySimpleOkHttp.getInstance().requestSync(mUri));
        }
    }
}
